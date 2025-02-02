package kwonjh0406.sns.follow.entity;

import jakarta.persistence.*;
import kwonjh0406.sns.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User following;

    @ManyToOne
    private User follower;

    @CreationTimestamp
    Timestamp createdAt;
}
