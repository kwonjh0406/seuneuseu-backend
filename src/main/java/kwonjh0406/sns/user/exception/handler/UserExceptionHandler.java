package kwonjh0406.sns.user.exception.handler;

import kwonjh0406.sns.global.dto.ApiResponse;
import kwonjh0406.sns.user.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameAlreadyExistsExceptions(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponse.<Void>builder()
                                .message(ex.getMessage())
                                .data(null)
                                .build()
                );
    }
}
