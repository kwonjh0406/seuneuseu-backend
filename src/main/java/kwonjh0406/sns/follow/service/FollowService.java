package kwonjh0406.sns.follow.service;

import jakarta.transaction.Transactional;
import kwonjh0406.sns.follow.dto.FollowCheck;
import kwonjh0406.sns.follow.entity.Follow;
import kwonjh0406.sns.follow.repository.FollowRepository;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.post.dto.PageRequestDto;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.entity.PostImage;
import kwonjh0406.sns.post.repository.PostRepository;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

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

    @Transactional
    public void unFollow(String username) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            User follower = oAuth2User.getUser();
            User following = userRepository.findByUsername(username);
            followRepository.deleteByFollowerAndFollowing(follower,following);
            // 동시성 제어 해결 필요 현 코드는 초안
            follower.setFollowing(follower.getFollowing() - 1);
            following.setFollower(following.getFollower() - 1);
            userRepository.save(following);
            userRepository.save(follower);
        }
    }


    public List<PostResponse> getFollowingPosts() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            List<Follow> followingList = followRepository.findByFollower(oAuth2User.getUser());
            List<User> followingUserList = followingList.stream()
                    .map(Follow::getFollowing)
                    .collect(Collectors.toList());
            List<Post> posts = postRepository.findPostsByFollowingWithPage(followingUserList);
            return posts.stream()
                    .map(post -> new PostResponse(
                            post,
                            post.getPostImages().stream()
                                    .map(PostImage::getImageUrl)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
