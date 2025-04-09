package kwonjh0406.sns.comment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import kwonjh0406.sns.comment.dto.CommentRequest;
import kwonjh0406.sns.comment.dto.CommentResponse;
import kwonjh0406.sns.comment.entity.Comment;
import kwonjh0406.sns.comment.repository.CommentRepository;
import kwonjh0406.sns.notification.NotificationService;
import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.post.entity.Post;
import kwonjh0406.sns.post.repository.PostRepository;
import kwonjh0406.sns.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public void createComment(Long postId, CommentRequest commentRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 인증된 사용자가 존재할 때
        if (principal instanceof CustomOAuth2User oAuth2User) {
            // 현재 로그인된 사용자
            User user = oAuth2User.getUser();
            // 댓글을 작성하려는 게시글 가져올 겸 게시글 존재 확인
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new EntityNotFoundException("게시글이 존재하지 않습니다.")
            );



            Comment comment = Comment.builder()
                    .parentCommentId(commentRequest.getParentId())
                    .content(commentRequest.getContent())
                    .user(user)
                    .post(post)
                    .build();
            // 낙관적 락 적용 최대 재시도 3회
            for (int attempts = 0; attempts < 3; attempts++) {
                System.out.println(postId + ", " + post.getLastCommentedAt().toString());
                if (postRepository.addReplies(postId, post.getLastCommentedAt()) == 1) {
                    // 댓글 수 증가에 성공한 경우 댓글 추가 후 성공 응답
                    commentRepository.save(comment);
                    return;
                }
            }
            throw new OptimisticLockException("댓글 작성에 실패했습니다. 다시 시도해 주세요.");
        }
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            // 현재 로그인된 사용자
            User user = oAuth2User.getUser();
            // 삭제하려는 대상 댓글을 가져옴
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new EntityNotFoundException("댓글이 존재하지 않습니다.")
            );
            // 삭제하려는 댓글이 현재 사용자의 댓글이 맞는지 확인
            if (comment.getUser().getUsername().equals(user.getUsername())) {
                Post post = postRepository.findById(postId).orElseThrow(
                        () -> new EntityNotFoundException("포스트를 찾을 수 없거나 이미 삭제되었습니다.")
                );
                // 먼저 삭제 후 댓글 수 동기화 수행 -> 삭제 연산은 추가 처럼 단순 댓글 + 1이 아닌 부모 댓글 삭제로 인한 자식 댓글 삭제까지 고려
                commentRepository.deleteByParentCommentId(comment.getId());
                commentRepository.delete(comment);
                // 낙관적 락 적용 최대 재시도 3회
                for (int attempts = 0; attempts < 3; attempts++) {
                    if (postRepository.updateReplies(postId, post.getLastCommentedAt()) == 1) {
                        // 댓글 수 재설정에 성공한 경우 성공 응답
                        return;
                    }
                }
                throw new OptimisticLockException("댓글 삭제에 실패했습니다. 다시 시도해 주세요.");
            }
        }
    }
}
