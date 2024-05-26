package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.AbstractFindByIdUseCase;

public class FindAuthorByIdUseCase extends AbstractFindByIdUseCase<String, AuthorEntity, AuthorRepository> {
    public FindAuthorByIdUseCase(AuthorRepository authorRepository) {
        super(authorRepository);
    }
}
