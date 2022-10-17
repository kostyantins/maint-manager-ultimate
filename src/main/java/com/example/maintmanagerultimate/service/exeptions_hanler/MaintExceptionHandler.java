package com.example.maintmanagerultimate.service.exeptions_hanler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exeptions.NoSuchMaintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class MaintExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleNoSuchMaintException(NoSuchMaintException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(exception.getMessage())
                        .date(now())
                        .build());
    }
}
