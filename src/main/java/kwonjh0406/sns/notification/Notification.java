package kwonjh0406.sns.notification;

import jakarta.persistence.*;
import kwonjh0406.sns.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    private String type;

    private Long targetId;

    private String message;

    private boolean isRead = false;

    @CreationTimestamp
    private Timestamp createdAt;
}
