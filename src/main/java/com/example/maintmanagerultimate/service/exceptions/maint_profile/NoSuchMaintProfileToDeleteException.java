package com.example.maintmanagerultimate.service.exceptions.maint_profile;

public class NoSuchMaintProfileToDeleteException extends RuntimeException{

    public NoSuchMaintProfileToDeleteException(Long maintProfileId) {
        super("No such maint profile id '%s' to delete".formatted(maintProfileId));
    }
}
