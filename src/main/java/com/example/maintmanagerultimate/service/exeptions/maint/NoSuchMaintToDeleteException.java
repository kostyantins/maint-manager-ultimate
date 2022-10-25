package com.example.maintmanagerultimate.service.exeptions.maint;

import static java.lang.String.format;

public class NoSuchMaintToDeleteException extends RuntimeException {

    public NoSuchMaintToDeleteException(Long maintId) {
        super(format("Could not delete maint with id '%s' because such maint doesnt exist", maintId));
    }
}
