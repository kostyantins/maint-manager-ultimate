package com.example.maintmanagerultimate.service.exceptions.maint_comments;

import static java.lang.String.format;

public class NoSuchMaintCommentsException extends RuntimeException{

    public NoSuchMaintCommentsException(Long maintId) {
        super(format("The Maint comment with id '%s' was not found !!", maintId));
    }
}
