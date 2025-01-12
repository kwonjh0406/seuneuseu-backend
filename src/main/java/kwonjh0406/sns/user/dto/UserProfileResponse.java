package kwonjh0406.sns.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    String username;
    String name;
    String bio;
    String profileImageUrl;
    Long follower;
    Long following;
    Timestamp createdAt;
}
