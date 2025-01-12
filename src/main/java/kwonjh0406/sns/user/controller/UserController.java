package kwonjh0406.sns.user.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.ApiResponse;
import kwonjh0406.sns.user.dto.UserProfileResponse;
import kwonjh0406.sns.user.dto.WelcomeProfileSetupDTO;
import kwonjh0406.sns.user.service.UserService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<ApiResponse> updateProfile(@Valid WelcomeProfileSetupDTO welcomeProfileSetupDTO) {

        return userService.setWelcomeProfile(welcomeProfileSetupDTO);
    }

    @GetMapping("/api/user/profile/{username}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable String username) {
        UserProfileResponse userProfileResponse = userService.getUserProfileByUsername(username);
        return ResponseEntity.ok(userProfileResponse);
    }

}