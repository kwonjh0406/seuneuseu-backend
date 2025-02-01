package kwonjh0406.sns.user.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.ProfileEditRequest;
import kwonjh0406.sns.user.dto.ProfileEditResponse;
import kwonjh0406.sns.user.dto.UsernameDto;
import kwonjh0406.sns.user.service.UserService;
import kwonjh0406.sns.user.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService userSessionService;
    private final UserService userService;

    @GetMapping("/me/username")
    public UsernameDto getCurrentUsername() {
        return userSessionService.getCurrentUsername();
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