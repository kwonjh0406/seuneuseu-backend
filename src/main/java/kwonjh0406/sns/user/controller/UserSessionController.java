package kwonjh0406.sns.user.controller;

import io.swagger.v3.oas.annotations.Operation;
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

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService userSessionService;
    private final UserService userService;

    @Operation(summary = "현재 로그인 사용자 아이디 조회", description = "현재 로그인된 사용자의 사용자 아이디 값을 가져옴, 로그인 된 사용자가 없는 경우 null 반환")
    @GetMapping("/me/username")
    public UsernameDto getCurrentUsername() {
        return userSessionService.getCurrentUsername();
    }

    @Operation(summary = "프로필 수정 페이지 정보 조회", description = "프로필 수정 페이지에서 보여줄 기존 프로필 정보를 받아옴. 프로필 사진, 사용자 아이디, 이름, 한 줄 소개")
    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<ProfileEditResponse>> getMyProfile() {
        ProfileEditResponse profileEditResponse = userService.getProfileEdit();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ProfileEditResponse>builder()
                                .message(null)
                                .data(profileEditResponse)
                                .build()
                );
    }

    @Operation(summary = "프로필 수정", description = "프로필 수정 후 저장, 변경된 값에 대해서만 값이 넘어온다. 변경되지 않은 값들은 null로 넘어온다.")
    @PatchMapping("/me/profile")
    public ResponseEntity<ApiResponse<Void>> editProfile(@Valid ProfileEditRequest profileEditRequest) throws IOException {
        userService.editProfile(profileEditRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<Void>builder()
                                .message(null)
                                .data(null)
                                .build()
                );
    }
}
