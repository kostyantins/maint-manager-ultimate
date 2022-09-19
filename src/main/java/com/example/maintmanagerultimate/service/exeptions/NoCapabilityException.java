package com.example.maintmanagerultimate.service.exeptions;

import static java.lang.String.format;

public class NoCapabilityException extends RuntimeException{

    public NoCapabilityException(Long capabilityId) {
        super(format("The capability with id '%s' was not found !!", capabilityId));
    }
}
