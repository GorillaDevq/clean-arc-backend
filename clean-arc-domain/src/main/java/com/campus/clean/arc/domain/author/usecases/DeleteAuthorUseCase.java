package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.AbstractDeleteUseCase;

public class DeleteAuthorUseCase extends AbstractDeleteUseCase<String, AuthorRepository> {
    public DeleteAuthorUseCase(AuthorRepository repository) {
        super(repository);
    }
}
