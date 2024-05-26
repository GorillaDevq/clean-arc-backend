package com.campus.clean.arc.domain.author.exceptions;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.rcore.domain.commons.exception.NotFoundDomainException;

public class AuthorNotFoundException extends NotFoundDomainException {
    public AuthorNotFoundException() {
        super(
                AuthorEntity.class.getName(),
                AuthorErrorReason.NOT_FOUND.name(),
                "Author not found"
        );
    }
}
