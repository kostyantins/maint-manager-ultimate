package com.example.maintmanagerultimate.service.exeptions.capability;

import static java.lang.String.format;

public class NoSuhcCapabilityException extends RuntimeException{

    public NoSuhcCapabilityException(Long capabilityId) {
        super(format("The capability with id '%s' was not found !!", capabilityId));
    }
}
