package com.example.maintmanagerultimate.service.exeptions_hanler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintIdentifierException;
import com.example.maintmanagerultimate.service.exeptions.maint.NoSuchMaintToDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class MaintExceptionHandler {

    @ExceptionHandler({NoSuchMaintIdentifierException.class, NoSuchMaintException.class})
    public ResponseEntity<ResponseErrorDto> handleNoSuchMaintException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .comment(exception.getMessage())
                        .date(now())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleNoSuchMaintException(NoSuchMaintToDeleteException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(ResponseErrorDto.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .comment(exception.getMessage())
                        .date(now())
                        .build());
    }
}
