package kwonjh0406.sns.user.service;


import kwonjh0406.sns.aws.s3.service.S3Service;
import kwonjh0406.sns.global.exception.UsernameAlreadyExistsException;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.dto.SearchUserResponse;
import kwonjh0406.sns.user.dto.UserProfileResponse;
import kwonjh0406.sns.user.dto.WelcomeProfileSetupDTO;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.AllArgsConstructor;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    public void setWelcomeProfile(WelcomeProfileSetupDTO welcomeProfileSetupDTO) throws IOException {

        String username = welcomeProfileSetupDTO.getUsername();
        String name = welcomeProfileSetupDTO.getName();
        String profileImageUrl;

        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("이미 사용 중인 사용자 아이디입니다.");
        }

        if (welcomeProfileSetupDTO.getProfileImage() != null) {
            profileImageUrl = s3Service.uploadImageToS3(welcomeProfileSetupDTO.getProfileImage());
        } else {
            profileImageUrl = null;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) { // 로그인 된 사용자가 OAuth2 사용자인 경우
            User user = oAuth2User.getUser();
            user.setUsername(username);
            user.setName(name);
            user.setFollowing(0L);
            user.setFollower(0L);
            user.setProfileImageUrl(profileImageUrl);
            user.setIsNew(false);
            userRepository.save(user);
        }

        /*
          프로필 사진 존재하면 -> AWS S3에 저장 + URL 로 DB 저장
          사용자 아이디 -> 유효성 검증 + 중복 체크  : O
          이름 -> 유효성 검증 :  O
          이 모든 단계 문제 없이 진행 시 isNew = false 로 변경
         */
    }

    public UserProfileResponse getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setUsername(user.getUsername());
        userProfileResponse.setName(user.getName());
        userProfileResponse.setBio(user.getBio());
        userProfileResponse.setProfileImageUrl(user.getProfileImageUrl());
        userProfileResponse.setFollower(user.getFollower());
        userProfileResponse.setFollowing(user.getFollowing());
        userProfileResponse.setCreatedAt(user.getCreatedAt());
        return userProfileResponse;
    }

    public List<SearchUserResponse> searchUsers() {
        return userRepository.findAllUsers();
    }
}
