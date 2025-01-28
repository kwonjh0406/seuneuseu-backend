package kwonjh0406.sns.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDifference {

    public static String getTimeDifference(Timestamp createdAt) {
        // 예시: Timestamp 형태로 작성 시간 (2024년 12월 25일 10시)
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // Timestamp를 LocalDateTime으로 변환
        LocalDateTime time = createdAt.toLocalDateTime();

        // 두 시간의 차이를 Duration으로 계산
        Duration duration = Duration.between(time, now);

        // 24시간 이내인 경우
        if (duration.toHours() < 24) {
            if (duration.toHours() < 1) {
                // 1시간 이내인 경우
                return duration.toMinutes() + "분";
            } else {
                // 24시간 이내인 경우
                return duration.toHours() + "시간";
            }
        } else if (duration.toDays() < 7) {
            // 7일 이내인 경우 (일 단위로 출력)
            return duration.toDays() + "일";
        } else {
            LocalDateTime dateTime = createdAt.toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
            return dateTime.format(formatter);
        }
    }
}
