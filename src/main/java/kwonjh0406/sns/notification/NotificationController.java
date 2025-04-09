package kwonjh0406.sns.notification;

import kwonjh0406.sns.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotifications() {
        List<NotificationResponse> notificationResponses = notificationService.getNotifications();
        return ResponseEntity.ok(
                ApiResponse.<List<NotificationResponse>>builder()
                        .data(notificationResponses)
                        .build()
        );
    }
}
