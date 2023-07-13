package com.example.maintmanagerultimate.service.exceptions.maint_comments;

import static java.lang.String.format;

public class NoSuchMaintCommentsToDeleteException extends RuntimeException{

    public NoSuchMaintCommentsToDeleteException(Long maintCommentId) {
        super(format("Could not delete maint comment with id '%s' because such maint comment doesnt exist", maintCommentId));
    }
}
