package com.campus.clean.arc.api.article.mapper;

import com.campus.clean.arc.api.article.dto.request.ArticleCreateRequest;
import com.campus.clean.arc.api.article.dto.request.ArticleUpdateRequest;
import com.campus.clean.arc.api.article.dto.response.ArticleResponse;
import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.campus.clean.arc.domain.article.usecases.CreateArticleUseCase;
import com.campus.clean.arc.domain.article.usecases.UpdateArticleUseCase;
import com.rcore.rest.api.commons.request.SearchApiRequest;

public class ArticleMapper {
    public static ArticleFilters map(SearchApiRequest request) {
        return ArticleFilters.builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .query(request.getQuery())
                .sortDirection(request.getSortDirection())
                .sortName(request.getSortName())
                .build();
    }

    public static ArticleResponse map(ArticleEntity entity) {
        return ArticleResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static CreateArticleUseCase.InputValues map(ArticleCreateRequest request) {
        return CreateArticleUseCase.InputValues.of(request.getTitle(), request.getDescription());
    }

    public static UpdateArticleUseCase.InputValues map(ArticleUpdateRequest request) {
        return UpdateArticleUseCase.InputValues.of(request.getId(), request.getTitle(), request.getDescription());
    }
}
