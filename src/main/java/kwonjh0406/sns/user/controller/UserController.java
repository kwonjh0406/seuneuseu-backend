package kwonjh0406.sns.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.*;
import kwonjh0406.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "프로필 정보 등록", description = "가입 후 최초 1회 프로필 정보 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> setupProfile(@Valid WelcomeProfileSetupDTO welcomeProfileSetupDTO) throws IOException {
        userService.setupProfile(welcomeProfileSetupDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<Void>builder()
                                .message(null)
                                .data(null)
                                .build()
                );
    }

    @Operation(summary = "프로필 정보 조회", description = "사용자 아이디로 해당 사용자의 프로필 정보(이름, 사용자 아이디, 프로필 사진, 한 줄 소개)를 가져옴")
    @GetMapping("/{username}/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@PathVariable String username) {
        UserProfileResponse userProfileResponse = userService.getUserProfileByUsername(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<UserProfileResponse>builder()
                                .message(null)
                                .data(userProfileResponse)
                                .build()
                );
    }
}
