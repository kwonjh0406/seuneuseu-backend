package kwonjh0406.sns.post.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.dto.request.CreatePostRequest;
import kwonjh0406.sns.post.dto.PageRequestDto;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(PageRequestDto pageRequestDto) {
        log.info("pageRequest: page: {}, size: {}", pageRequestDto.getPage(), pageRequestDto.getSize());
        List<PostResponse> postResponseList = postService.getPosts(pageRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<List<PostResponse>>builder()
                                .message(null)
                                .data(postResponseList)
                                .build()
                );
    }

    @PostMapping("/api/posts")
    public ResponseEntity<ApiResponse<Void>> createPost(@ModelAttribute CreatePostRequest createPostRequest) throws IOException {
        postService.createPost(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("게시물이 성공적으로 생성되었습니다.", null));
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
