package com.example.maintmanagerultimate.service.exceptions.maint;

import static java.lang.String.format;

public class NoSuchMaintIdentifierException extends RuntimeException {

    public NoSuchMaintIdentifierException(String maintIdentifier) {
        super(format("The maint with identifier '%s' was not found !!", maintIdentifier));
    }
}
