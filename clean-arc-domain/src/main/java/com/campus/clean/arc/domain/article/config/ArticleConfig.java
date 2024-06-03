package com.campus.clean.arc.domain.article.config;

import com.campus.clean.arc.domain.article.port.ArticleIdGenerator;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.usecases.*;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class ArticleConfig {
    private final CreateArticleUseCase createArticleUseCase;
    private final DeleteArticleUseCase deleteArticleUseCase;
    private final FindArticleWithFiltersUseCase findArticleWithFiltersUseCase;
    private final FindByIdArticleUseCase findByIdArticleUseCase;
    private final UpdateArticleUseCase updateArticleUseCase;

    public ArticleConfig(ArticleRepository articleRepository, ArticleIdGenerator articleIdGenerator) {
        this.createArticleUseCase = new CreateArticleUseCase(articleIdGenerator, articleRepository);
        this.deleteArticleUseCase = new DeleteArticleUseCase(articleRepository);
        this.findArticleWithFiltersUseCase = new FindArticleWithFiltersUseCase(articleRepository);
        this.findByIdArticleUseCase = new FindByIdArticleUseCase(articleRepository);
        this.updateArticleUseCase = new UpdateArticleUseCase(articleRepository);
    }
}
