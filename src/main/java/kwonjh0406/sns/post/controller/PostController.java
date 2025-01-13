package kwonjh0406.sns.post.controller;

import kwonjh0406.sns.post.dto.ApiResponse;
import kwonjh0406.sns.post.dto.CreatePostRequest;
import kwonjh0406.sns.post.dto.PostResponse;
import kwonjh0406.sns.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@ModelAttribute CreatePostRequest createPostRequest) {

        try {
            postService.createPost(createPostRequest);
            return ResponseEntity.status(201).body(
                    new ApiResponse(201, "게시글이 성공적으로 작성되었습니다.")
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body(
                    new ApiResponse(401, "인증되지 않은 사용자입니다.")
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(500).body(
                    new ApiResponse(500, "서버 오류가 발생했습니다.")
            );
        }
    }

    @GetMapping("/api/posts/username")
    public List<PostResponse> getAllPostsByUsername(@RequestParam String username) {
        return postService.getAllPostsByUsername(username);
    }

    @GetMapping("/api/posts")
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }
}
