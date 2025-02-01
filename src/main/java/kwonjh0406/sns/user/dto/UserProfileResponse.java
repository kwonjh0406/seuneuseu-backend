package kwonjh0406.sns.user.dto;

import kwonjh0406.sns.user.entity.User;
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
    int follower;
    int following;
    Timestamp createdAt;

    public UserProfileResponse(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.bio = user.getBio();
        this.profileImageUrl = user.getProfileImageUrl();
        this.follower = user.getFollower();
        this.following = user.getFollowing();
        this.createdAt = user.getCreatedAt();
    }
}
