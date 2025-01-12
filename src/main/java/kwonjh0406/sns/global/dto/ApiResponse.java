package kwonjh0406.sns.global.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    int statusCode;
    String message;
    private T data;
}
