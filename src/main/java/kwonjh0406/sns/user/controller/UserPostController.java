package kwonjh0406.sns.user.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.dto.PostImageResponse;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.entity.PostImage;
import kwonjh0406.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserPostController {

    private final PostService postService;

    @GetMapping("/{username}/posts")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getUserPosts(@PathVariable String username) {
        try {
            List<PostResponse> postResponseList = postService.getPostsByUsername(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<List<PostResponse>>builder()
                                    .message(null)
                                    .data(postResponseList)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<List<PostResponse>>builder()
                                    .message(e.getMessage())
                                    .data(null)
                                    .build()
                    );
        }
    }

    @GetMapping("/{username}/posts/images")
    public ResponseEntity<ApiResponse<List<PostImageResponse>>> getUserPostImages(@PathVariable String username) {
        try {
            List<PostImageResponse> postImageResponseList = postService.getPostImagesByUsername(username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.<List<PostImageResponse>>builder()
                                    .message(null)
                                    .data(postImageResponseList)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<List<PostImageResponse>>builder()
                                    .message(e.getMessage())
                                    .data(null)
                                    .build()
                    );
        }
    }
}
