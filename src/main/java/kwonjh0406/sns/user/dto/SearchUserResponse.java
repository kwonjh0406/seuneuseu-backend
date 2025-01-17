package kwonjh0406.sns.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserResponse {
    private String username;
    private String name;
    private String profileImageUrl;
    private String bio;
}
