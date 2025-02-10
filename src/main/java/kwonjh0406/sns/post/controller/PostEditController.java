package kwonjh0406.sns.post.controller;

import jakarta.validation.Valid;
import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.dto.EditPostRequest;
import kwonjh0406.sns.post.dto.PostContentDto;
import kwonjh0406.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostEditController {

    private final PostService postService;

    @GetMapping("/edit/{postId}")
    public ResponseEntity<ApiResponse<PostContentDto>> getContentByPostId(@PathVariable Long postId) {
        try {
            PostContentDto postContentDto = postService.getPostEditByPostId(postId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ApiResponse.<PostContentDto>builder()
                                    .message("Successfully retrieved post content")
                                    .data(postContentDto)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<PostContentDto>builder()
                                    .message("Failed to retrieve post content")
                                    .data(null)
                                    .build()
                    );
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> editPost(@PathVariable Long postId, @RequestBody @Valid EditPostRequest editPostRequest) {
        try {
            System.out.println(editPostRequest.getContent());
            postService.editPostByPostId(postId, editPostRequest.getContent());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ApiResponse.<Void>builder()
                                    .data(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<Void>builder()
                                    .message("Failed to retrieve post content")
                                    .data(null)
                                    .build()
                    );
        }
    }
}
