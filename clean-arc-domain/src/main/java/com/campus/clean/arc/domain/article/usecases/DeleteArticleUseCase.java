package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.AbstractDeleteUseCase;

public class DeleteArticleUseCase extends AbstractDeleteUseCase<String, ArticleRepository> {
    public DeleteArticleUseCase(ArticleRepository repository) {
        super(repository);
    }
}
