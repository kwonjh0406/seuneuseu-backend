package kwonjh0406.sns.post.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostContentDto {
    private String content;
    private List<String> images;
}
