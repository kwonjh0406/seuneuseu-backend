package kwonjh0406.sns.user.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.UserProfileResponse;
import kwonjh0406.sns.user.dto.WelcomeProfileSetupDTO;
import kwonjh0406.sns.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/welcome-profile-setup")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@Valid WelcomeProfileSetupDTO welcomeProfileSetupDTO) {
        try {
            userService.setWelcomeProfile(welcomeProfileSetupDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .message(null)
                                    .data(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .message(e.getMessage())
                                    .data(null)
                                    .build()
                    );
        }
    }

    @GetMapping("/api/user/profile/{username}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String username) {
        UserProfileResponse userProfileResponse = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfileResponse);
    }

}