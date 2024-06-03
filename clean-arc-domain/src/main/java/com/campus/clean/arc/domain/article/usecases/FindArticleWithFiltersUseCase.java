package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.rcore.domain.commons.usecase.AbstractFindWithFiltersUseCase;

public class FindArticleWithFiltersUseCase extends AbstractFindWithFiltersUseCase<ArticleEntity, ArticleFilters, ArticleRepository> {
    public FindArticleWithFiltersUseCase(ArticleRepository repository) {
        super(repository);
    }
}
