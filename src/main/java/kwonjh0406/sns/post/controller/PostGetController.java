package kwonjh0406.sns.post.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.dto.EditPostRequest;
import kwonjh0406.sns.post.dto.PostContentDto;
import kwonjh0406.sns.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostGetController {

    private final PostService postService;

    @Autowired
    public PostGetController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/edit/{postId}")
    public ResponseEntity<ApiResponse<PostContentDto>> getContentByPostId(@PathVariable Long postId) {
        try {
            PostContentDto postContentDto = postService.getPostByPostId(postId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ApiResponse.<PostContentDto>builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .message("Successfully retrieved post content")
                                    .data(postContentDto)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<PostContentDto>builder()
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .message("Failed to retrieve post content")
                                    .data(null)
                                    .build()
                    );
        }
    }

    @PatchMapping("/edit/{postId}")
    public ResponseEntity<ApiResponse<Void>> editPost(@PathVariable Long postId,@RequestBody EditPostRequest editPostRequest) {
        try {
            postService.editPostByPostId(postId, editPostRequest.getContent());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .message("Successfully retrieved post content")
                                    .data(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .message("Failed to retrieve post content")
                                    .data(null)
                                    .build()
                    );
        }
    }
}
