package com.example.maintmanagerultimate.service.exeptions.maint;

import static java.lang.String.format;

public class NoSuchMaintException extends RuntimeException{

    public NoSuchMaintException(Long maintId) {
        super(format("The maint with id '%s' was not found !!", maintId));
    }
}
