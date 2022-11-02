package com.example.maintmanagerultimate.service.exeptions.priority;

import static java.lang.String.format;

public class NoSuchPrioritiesException extends RuntimeException {

    public NoSuchPrioritiesException(Long priorityId) {
        super(format("The capability with id '%s' was not found !!", priorityId));
    }
}
