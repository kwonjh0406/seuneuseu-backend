package kwonjh0406.sns.global.exception;

import kwonjh0406.sns.global.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.<Void>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("서버 에러: " + e.getMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<Void>builder()
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(Optional.ofNullable(ex.getBindingResult().getFieldError())
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .orElse("Unknown validation error")
                                )
                                .data(null)
                                .build()
                );
    }
}
