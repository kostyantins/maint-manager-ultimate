package com.example.maintmanagerultimate.service.exceptions.maint_profile;

public class NoSuchMaintProfileException extends RuntimeException {

    public NoSuchMaintProfileException(Long maintProfileId) {
        super("The maint profile with id '%s' was not found !!".formatted(maintProfileId));
    }
}
