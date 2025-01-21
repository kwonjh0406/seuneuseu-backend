package kwonjh0406.sns.user.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.*;
import kwonjh0406.sns.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/welcome-profile-setup")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@Valid WelcomeProfileSetupDTO welcomeProfileSetupDTO) {
        try {
            userService.setWelcomeProfile(welcomeProfileSetupDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<Void>builder()
                                    .message(null)
                                    .data(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(
                            ApiResponse.<Void>builder()
                                    .message(e.getMessage())
                                    .data(null)
                                    .build()
                    );
        }
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String username) {
        UserProfileResponse userProfileResponse = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfileResponse);
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<ProfileEditResponse>> getMyProfile() {
        try {
            ProfileEditResponse profileEditResponse = userService.getProfileEdit();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<ProfileEditResponse>builder()
                                    .message(null)
                                    .data(profileEditResponse)
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<ProfileEditResponse>builder()
                                    .message(null)
                                    .data(null)
                                    .build()
                    );
        }
    }

    @PatchMapping("/me/profile")
    public ResponseEntity<ApiResponse<Void>> profileEdit(@Valid ProfileEditRequest profileEditRequest) {
        try {
            userService.editProfile(profileEditRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<Void>builder()
                                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<Void>builder()
                                    .message(e.getMessage())
                                    .build()
                    );
        }
    }
}