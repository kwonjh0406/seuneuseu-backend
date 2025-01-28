package kwonjh0406.sns.comment.controller;

import kwonjh0406.sns.comment.dto.CommentRequest;
import kwonjh0406.sns.comment.dto.CommentResponse;
import kwonjh0406.sns.comment.service.CommentService;
import kwonjh0406.sns.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        List<CommentResponse> commentResponseList = commentService.getComments(postId);
        return ResponseEntity.ok(
                ApiResponse.<List<CommentResponse>>builder().message(null).data(commentResponseList).build()
        );
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(postId, commentRequest);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder().message(null).data(null).build()
        );
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder().message(null).data(null).build()
        );
    }
}
