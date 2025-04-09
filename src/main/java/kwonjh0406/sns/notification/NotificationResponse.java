package kwonjh0406.sns.notification;

import kwonjh0406.sns.util.TimeDifference;

import java.sql.Timestamp;

public class NotificationResponse {

    public Long id;
    public String username;
    public String profileImageUrl;
    public Long postId;
    public String message;
    public String type;
    public String timeAgo;
    public boolean isRead;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.username = notification.getSender().getUsername();
        this.profileImageUrl = notification.getSender().getProfileImageUrl();
        this.postId = notification.getTargetId();
        this.message = notification.getMessage();
        this.type = notification.getType();
        this.timeAgo = TimeDifference.getTimeDifference(notification.getCreatedAt());
        this.isRead = notification.isRead();
    }
}
