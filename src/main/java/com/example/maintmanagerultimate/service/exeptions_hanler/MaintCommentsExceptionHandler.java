package com.example.maintmanagerultimate.service.exeptions_hanler;

import com.example.maintmanagerultimate.service.errors.ResponseError;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintCommentsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class MaintCommentsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleNoSuchMaintCommentException(NoSuchMaintCommentsException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseError.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(exception.getMessage())
                        .date(now())
                        .build());
    }
}
