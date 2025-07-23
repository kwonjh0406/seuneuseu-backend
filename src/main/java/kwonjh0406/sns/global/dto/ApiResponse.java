package kwonjh0406.sns.global.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private final String message;
    private final T data;

    // 생성자
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("요청이 성공적으로 처리되었습니다.", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(null, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }

    // getter 생략 or lombok 사용 가능
}

