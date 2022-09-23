package com.example.maintmanagerultimate.service.exeptions_hanler;

import com.example.maintmanagerultimate.service.errors.MaintError;
import com.example.maintmanagerultimate.service.exeptions.NoMaintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDate.now;

@ControllerAdvice
public class MaintExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<MaintError> handleNoMaintException(NoMaintException exception) {
        return new ResponseEntity<>(MaintError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .comment(exception.getLocalizedMessage())
                .date(now())
                .build(), HttpStatus.NOT_FOUND);
    }
}
