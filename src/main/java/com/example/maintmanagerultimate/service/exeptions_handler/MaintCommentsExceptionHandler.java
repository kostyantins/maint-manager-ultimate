package com.example.maintmanagerultimate.service.exeptions_handler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exceptions.maint_comments.NoSuchMaintCommentsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class MaintCommentsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleNoSuchMaintCommentException(NoSuchMaintCommentsException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(exception.getMessage())
                        .date(now())
                        .build());
    }
}
