package kwonjh0406.sns.user.service;

import kwonjh0406.sns.aws.s3.service.S3Service;
import kwonjh0406.sns.user.exception.UsernameAlreadyExistsException;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.dto.*;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void setupProfile(WelcomeProfileSetupDTO welcomeProfileSetupDTO) throws IOException {

        String username = welcomeProfileSetupDTO.getUsername();
        String name = welcomeProfileSetupDTO.getName();
        String profileImageUrl;

        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("이미 사용 중인 사용자 아이디입니다.");
        }

        if (welcomeProfileSetupDTO.getProfileImage() != null) {
            profileImageUrl = s3Service.uploadImageToS3(welcomeProfileSetupDTO.getProfileImage());
        } else {
            profileImageUrl = "https://www.gravatar.com/avatar/?d=mp";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomOAuth2User oAuth2User) {
            User user = oAuth2User.getUser();
            user.setUsername(username);
            user.setName(name);
            user.setProfileImageUrl(profileImageUrl);
            user.setIsNew(false);
            userRepository.save(user);
        }
    }

    public UserProfileResponse getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return new UserProfileResponse(user);
    }

    public List<SearchUserResponse> searchUsers() {
        return userRepository.findAllUsers();
    }

    public ProfileEditResponse getProfileEdit() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomOAuth2User oAuth2User) {
            ProfileEditResponse profileEditResponse = userRepository.findProfileByUserId(oAuth2User.getUser().getId());
            return profileEditResponse;
        }
        return null;
    }

    public void editProfile(ProfileEditRequest profileEditRequest) throws IOException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            User user = oAuth2User.getUser();

            if (profileEditRequest.getProfileImage() != null) {
                s3Service.deleteImageFromS3(user.getProfileImageUrl());
                String url = s3Service.uploadImageToS3(profileEditRequest.getProfileImage());
                user.setProfileImageUrl(url);
            }
            if (profileEditRequest.getName() != null) {
                user.setName(profileEditRequest.getName());
            }
            if (profileEditRequest.getUsername() != null) {
                if (userRepository.existsByUsername(profileEditRequest.getUsername())) {
                    throw new IllegalIdentifierException("사용중인 아이디");
                }
                user.setUsername(profileEditRequest.getUsername());
            }
            if (profileEditRequest.getBio() != null) {
                user.setBio(profileEditRequest.getBio());
            }
            userRepository.save(user);
        }
    }
}
