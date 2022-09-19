package com.example.maintmanagerultimate.service.exeptions;

import static java.lang.String.format;

public class NoMaintException extends RuntimeException{

    public NoMaintException(Long maintId) {
        super(format("The maint with id '%s' was not found !!", maintId));
    }
}
