package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleIdGenerator;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
public class CreateArticleUseCase extends UseCase<CreateArticleUseCase.InputValues, SingletonEntityOutputValues<ArticleEntity>> {

    private final ArticleIdGenerator articleIdGenerator;
    private final ArticleRepository articleRepository;

    @Override
    public SingletonEntityOutputValues<ArticleEntity> execute(InputValues inputValues) {
        ArticleEntity articleEntity = ArticleEntity.builder()
                .id(articleIdGenerator.generate())
                .title(inputValues.getTitle())
                .description(inputValues.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        articleEntity = articleRepository.save(articleEntity);
        return SingletonEntityOutputValues.of(articleEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    @Setter
    public static class InputValues implements UseCase.InputValues {
        private String title;
        private String description;
    }
}
