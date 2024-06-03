package com.campus.clean.arc.domain.article.exceptions;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.rcore.domain.commons.exception.NotFoundDomainException;

public class ArticleNotFoundException extends NotFoundDomainException {
    public ArticleNotFoundException() {
        super(
                ArticleEntity.class.getName(),
                ArticleErrorReason.NOT_FOUND.name(),
                "Article not found"
        );
    }

    public ArticleNotFoundException(String id) {
        super(
                ArticleEntity.class.getName(),
                ArticleErrorReason.NOT_FOUND.name(),
                "Article not found " + id
        );
    }
}
