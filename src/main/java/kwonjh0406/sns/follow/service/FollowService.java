package kwonjh0406.sns.follow.service;

import jakarta.transaction.Transactional;
import kwonjh0406.sns.follow.dto.FollowCheck;
import kwonjh0406.sns.follow.entity.Follow;
import kwonjh0406.sns.follow.repository.FollowRepository;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowCheck isFollow(String username) {
        FollowCheck followCheck = new FollowCheck();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            User follower = oAuth2User.getUser();
            User following = userRepository.findByUsername(username);
            followCheck.setFollow(followRepository.existsByFollowerAndFollowing(follower, following));
        }

        return followCheck;
    }

    @Transactional
    public void follow(String username) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            User follower = oAuth2User.getUser();
            User following = userRepository.findByUsername(username);
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();
            followRepository.save(follow);
            // 동시성 제어 해결 필요 현 코드는 초안
            follower.setFollowing(follower.getFollowing() + 1);
            following.setFollower(follower.getFollower() + 1);
            userRepository.save(following);
            userRepository.save(follower);
        }
    }
}
