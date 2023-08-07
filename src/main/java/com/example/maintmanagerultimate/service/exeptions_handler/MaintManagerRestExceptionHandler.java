package com.example.maintmanagerultimate.service.exeptions_handler;

import com.example.maintmanagerultimate.service.dto.ResponseErrorDto;
import com.example.maintmanagerultimate.service.exceptions.capability.NoSuhcCapabilityException;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintException;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintIdentifierException;
import com.example.maintmanagerultimate.service.exceptions.maint.NoSuchMaintToDeleteException;
import com.example.maintmanagerultimate.service.exceptions.maint_comments.NoSuchMaintCommentsException;
import com.example.maintmanagerultimate.service.exceptions.maint_profile.NoSuchMaintProfileException;
import com.example.maintmanagerultimate.service.exceptions.maint_profile.NoSuchMaintProfileToDeleteException;
import com.example.maintmanagerultimate.service.exceptions.priority.NoSuchPrioritiesException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

import static java.time.LocalDate.now;

@ControllerAdvice
@RequiredArgsConstructor
public class MaintManagerRestExceptionHandler {

    private final Provider<HttpServletRequest> provider;

    @ExceptionHandler({
            NoSuchPrioritiesException.class,
            NoSuhcCapabilityException.class,
            NoSuchMaintCommentsException.class,
            NoSuchMaintIdentifierException.class,
            NoSuchMaintException.class,
            NoSuchMaintProfileException.class
    })
    public ResponseEntity<ResponseErrorDto> handleNoSuchPriorityException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(getResponseErrorDto(exception));
    }

    @ExceptionHandler({
            NoSuchMaintToDeleteException.class,
            NoSuchMaintProfileToDeleteException.class,
    })
    public ResponseEntity<ResponseErrorDto> handleNoSuchMaintException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(getResponseErrorDto(exception));
    }

    private ResponseErrorDto getResponseErrorDto(Exception exception) {
        return ResponseErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.getMessage())
                .date(now())
                .path("%s %s".formatted(provider.get().getMethod(), provider.get().getRequestURI()))
                .build();
    }
}
