package com.campus.clean.arc.domain.author.exceptions;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.rcore.domain.commons.exception.NotFoundDomainException;

public class AuthorAlreadyExistException extends NotFoundDomainException {
    public AuthorAlreadyExistException() {
        super(
                AuthorEntity.class.getName(),
                AuthorErrorReason.ALREADY_EXISTS.name(),
                "Author already exist"
        );
    }
}
