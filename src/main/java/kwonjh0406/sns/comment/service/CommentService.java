package kwonjh0406.sns.comment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kwonjh0406.sns.comment.dto.CommentRequest;
import kwonjh0406.sns.comment.dto.CommentResponse;
import kwonjh0406.sns.comment.entity.Comment;
import kwonjh0406.sns.comment.repository.CommentRepository;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.repository.PostRepository;
import kwonjh0406.sns.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<CommentResponse> getComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseList.add(new CommentResponse(comment));
        }
        return commentResponseList;
    }

    @Transactional
    public void createComment(Long postId, CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new EntityNotFoundException("포스트를 찾을 수 없거나 이미 삭제되었습니다.")
            );
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomOAuth2User oAuth2User) {
                // 로그인 한 사용자와 게시글 작성자가 동일한지 확인
                User user = oAuth2User.getUser();
                Comment comment = Comment.builder()
                        .parentCommentId(commentRequest.getParentId())
                        .content(commentRequest.getContent())
                        .user(user)
                        .post(post)
                        .build();
                int maxTry = 3;  // 최대 재시도 횟수
                int currentTry = 0;
                while (currentTry < maxTry) {
                    // 낙관적 락 실패 시 재시도
                    if (postRepository.updateReplies(post) == 0) {
                        currentTry++;
                    } else {
                        commentRepository.save(comment);
                        return;
                    }
                }
                throw new OptimisticLockException("낙관적 락 재시도 횟수 초과: 댓글 작성 로직");
            }
        }
    }

    public void deleteComment(Long postId, Long commentId) {

        // 임시로 작성된 코드

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new EntityNotFoundException("포스트를 찾을 수 없거나 이미 삭제되었습니다.")
            );
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomOAuth2User oAuth2User) {
                Comment comment = commentRepository.findById(commentId).get();
                User user = oAuth2User.getUser();
                if (comment.getUser().getUsername().equals(user.getUsername())) {
                    post.setReplies(post.getReplies() - 1);
                    postRepository.save(post);
                    commentRepository.delete(comment);
                }
            }
        }
    }
}
