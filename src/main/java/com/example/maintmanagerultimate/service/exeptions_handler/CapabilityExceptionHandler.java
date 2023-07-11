package com.example.maintmanagerultimate.service.exeptions_handler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exeptions.capability.NoSuhcCapabilityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class CapabilityExceptionHandler {

    @ExceptionHandler(NoSuhcCapabilityException.class)
    public ResponseEntity<ResponseErrorDto> handleNoSuchCapabilityException(NoSuhcCapabilityException noSuhcCapabilityException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(noSuhcCapabilityException.getMessage())
                        .date(now())
                        .build());
    }
}
