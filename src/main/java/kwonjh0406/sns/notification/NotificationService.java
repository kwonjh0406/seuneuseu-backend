package kwonjh0406.sns.notification;

import kwonjh0406.sns.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void createCommentNotification(User sender, User recipient, String comment, Long postId) {
        Notification notification = Notification
                .builder()
                .type("COMMENT")
                .receiver(recipient)
                .sender(sender)
                .message(comment)
                .targetId(postId)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }


}
