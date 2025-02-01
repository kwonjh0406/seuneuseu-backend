package kwonjh0406.sns.user.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.dto.SearchUserResponse;
import kwonjh0406.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/search")
@RequiredArgsConstructor
public class UserSearchController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SearchUserResponse>>> searchUsers() {
        try {
            List<SearchUserResponse> searchUserResponseList = userService.searchUsers();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<List<SearchUserResponse>>builder()
                                    .message(null)
                                    .data(searchUserResponseList)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<List<SearchUserResponse>>builder()
                                    .message(e.getMessage())
                                    .data(null)
                                    .build()
                    );
        }
    }
}
