package kwonjh0406.sns.post.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.dto.CreatePostRequest;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@ModelAttribute CreatePostRequest createPostRequest) {

        try {
            postService.createPost(createPostRequest);
            return ResponseEntity.status(201).body(
                    new kwonjh0406.sns.post.dto.ApiResponse(201, "게시글이 성공적으로 작성되었습니다.")
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body(
                    new kwonjh0406.sns.post.dto.ApiResponse(401, "인증되지 않은 사용자입니다.")
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    new kwonjh0406.sns.post.dto.ApiResponse(500, "서버 오류가 발생했습니다.")
            );
        }
    }

    @GetMapping("/api/posts")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        try {
            PostResponse postResponse = postService.getPostByPostId(postId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            ApiResponse.<PostResponse>builder()
                                    .data(postResponse)
                                    .message(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<PostResponse>builder()
                                    .data(null)
                                    .message(e.getMessage())
                                    .build()
                    );
        }
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePostByPostId(postId);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(
                            ApiResponse.<Void>builder()
                                    .data(null)
                                    .message(null)
                                    .build()
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<Void>builder()
                                    .data(null)
                                    .message(e.getMessage())
                                    .build()
                    );
        }
    }
}
