package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.rcore.domain.commons.usecase.AbstractFindWithFiltersUseCase;
import com.rcore.domain.commons.usecase.model.FiltersInputValues;
import com.rcore.domain.commons.usecase.model.SearchResultEntityOutputValues;

public class FindAuthorWithFiltersUseCase extends AbstractFindWithFiltersUseCase<AuthorEntity, AuthorFilters, AuthorRepository> {
    public FindAuthorWithFiltersUseCase(AuthorRepository repository) {
        super(repository);
    }

    @Override
    public SearchResultEntityOutputValues<AuthorEntity> execute(FiltersInputValues<AuthorFilters> inputValues) {
        return SearchResultEntityOutputValues.of(repository.find(inputValues.getFilters()));
    }
}
