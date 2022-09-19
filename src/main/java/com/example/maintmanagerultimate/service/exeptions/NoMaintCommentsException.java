package com.example.maintmanagerultimate.service.exeptions;

import static java.lang.String.format;

public class NoMaintCommentsException extends RuntimeException{

    public NoMaintCommentsException(Long maintId) {
        super(format("The Maint comment with id '%s' was not found !!", maintId));
    }
}
