package kwonjh0406.sns.user.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.*;
import kwonjh0406.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
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

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String username) {
        UserProfileResponse userProfileResponse = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfileResponse);
    }


}