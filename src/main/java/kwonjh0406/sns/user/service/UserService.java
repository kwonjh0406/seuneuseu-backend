package kwonjh0406.sns.user.service;

import kwonjh0406.sns.global.ApiResponse;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.dto.UserProfileResponse;
import kwonjh0406.sns.user.dto.WelcomeProfileSetupDTO;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse> setWelcomeProfile(WelcomeProfileSetupDTO welcomeProfileSetupDTO) {

        ApiResponse apiResponse = new ApiResponse();

        String username = welcomeProfileSetupDTO.getUsername();
        String name = welcomeProfileSetupDTO.getName();

        if (userRepository.existsByUsername(username)) {
            apiResponse.setMessage("Username already exists");
            apiResponse.setSuccess(false);
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        if (authentication != null) {
            Object principal2 = authentication.getPrincipal();
            System.out.println("Principal: " + principal2.getClass());
        } else {
            System.out.println("Authentication is null");
        }

        if (principal instanceof CustomOAuth2User oAuth2User) { // 로그인 된 사용자가 OAuth2 사용자인 경우
            System.out.println("이거 동작 해야되는?");
            User user = oAuth2User.getUser();
            user.setUsername(username);
            user.setName(name);
            user.setFollowing(0L);
            user.setFollower(0L);
            user.setProfileImageUrl("https://www.gravatar.com/avatar/?d=mp");
            user.setIsNew(false);
            userRepository.save(user);
            apiResponse.setSuccess(true);
            return ResponseEntity.ok(apiResponse);
        }

        System.out.println("이거 동작하면 안됨");

        apiResponse.setSuccess(false);
        return ResponseEntity.ok(apiResponse);

        /**
         * 프로필 사진 존재하면 -> AWS S3에 저장 + URL로 DB 저장
         * 사용자 아이디 -> 유효성 검증 + 중복 체크  : O
         * 이름 -> 유효성 검증 :  O
         * 이 모든 단계 문제 없이 진행 시 isNew = False로 변경
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
}
