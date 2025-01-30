package kwonjh0406.sns.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import kwonjh0406.sns.comment.dto.CommentRequest;
import kwonjh0406.sns.comment.dto.CommentResponse;
import kwonjh0406.sns.comment.service.CommentService;
import kwonjh0406.sns.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 조회", description = "게시글 id로 해당 게시글의 댓글을 조회")
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        List<CommentResponse> commentResponseList = commentService.getComments(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<List<CommentResponse>>builder()
                                .message(null)
                                .data(commentResponseList)
                                .build()
                );
    }

    @Operation(summary = "댓글 추가", description = "게시글 id로 해당 게시글에 부모 댓글의 여부에 따라 댓글/답글을 작성")
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        commentService.createComment(postId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<Void>builder()
                                .message(null)
                                .data(null)
                                .build()
                );
    }

    @Operation(summary = "댓글 삭제", description = "댓글 id로 댓글을 삭제하고 게시글 id로 게시글의 댓글 수 동기화")
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.<Void>builder()
                                .message(null)
                                .data(null)
                                .build()
                );
    }
}
