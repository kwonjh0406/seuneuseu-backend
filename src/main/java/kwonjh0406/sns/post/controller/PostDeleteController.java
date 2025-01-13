package kwonjh0406.sns.post.controller;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostDeleteController {

    private final PostService postService;

    @Autowired
    public PostDeleteController(PostService postService) {
        this.postService = postService;
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {

        try {
            postService.deletePost(postId);
            //log.info("Successfully deleted post with postId {}", postId);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.NO_CONTENT.value())
                                    .message("Successfully deleted post with postId " + postId)
                                    .build()
                    );
        } catch (Exception e) {
            //log.error("Error while deleting post with postId {}", postId, e);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<Void>builder()
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .message("Error while deleting post with postId " + postId)
                                    .build()
                    );
        }
    }
}
