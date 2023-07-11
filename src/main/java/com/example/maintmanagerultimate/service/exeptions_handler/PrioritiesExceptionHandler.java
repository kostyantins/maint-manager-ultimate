package com.example.maintmanagerultimate.service.exeptions_handler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exeptions.priority.NoSuchPrioritiesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class PrioritiesExceptionHandler {

    @ExceptionHandler(NoSuchPrioritiesException.class)
    public ResponseEntity<ResponseErrorDto> handleNoSuchPriorityException(NoSuchPrioritiesException noSuchPrioritiesException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(noSuchPrioritiesException.getMessage())
                        .date(now())
                        .build());
    }
}
