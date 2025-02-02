package kwonjh0406.sns.follow.controller;

import kwonjh0406.sns.follow.dto.FollowCheck;
import kwonjh0406.sns.follow.service.FollowService;
import kwonjh0406.sns.global.dto.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/users/{username}/follow")
    public ResponseEntity<ApiResponse<FollowCheck>> isFollow(@PathVariable String username) {
        FollowCheck followCheck = followService.isFollow(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<FollowCheck>builder()
                                .message(null)
                                .data(followCheck)
                                .build()
                );
    }

    @PostMapping("/users/{username}/follow")
    public ResponseEntity<ApiResponse<Void>> follow(@PathVariable String username) {
        System.out.println(username);
        followService.follow(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<Void>builder()
                                .message(null)
                                .data(null)
                                .build()
                );
    }
}
