package kwonjh0406.sns.notification;

import kwonjh0406.sns.oauth2.dto.CustomOAuth2User;
import kwonjh0406.sns.user.entity.User;
import kwonjh0406.sns.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponse> getNotifications() {
        User user = SecurityUtil.getCurrentUser();
        System.out.println(user);
        List<Notification> notifications = notificationRepository.findByReceiver(user);
        return notifications.stream()
                .map(NotificationResponse::new)
                .collect(Collectors.toList());
    }

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
