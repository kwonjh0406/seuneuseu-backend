package kwonjh0406.sns.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private int status;    // HTTP 상태 코드
    private String message; // 응답 메시지

    // 기본 생성자
    public ApiResponse() {
    }

    // 파라미터 생성자
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // toString (디버깅 용도)
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
