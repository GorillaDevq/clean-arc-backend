package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@RequiredArgsConstructor
public class UpdateArticleUseCase extends UseCase<UpdateArticleUseCase.InputValues, SingletonEntityOutputValues<ArticleEntity>> {

    private final ArticleRepository articleRepository;

    @Override
    public SingletonEntityOutputValues<ArticleEntity> execute(InputValues inputValues) {
        ArticleEntity articleEntity = articleRepository.findById(inputValues.getId()).orElseThrow(ArticleNotFoundException::new);

        articleEntity.setTitle(inputValues.getTitle());
        articleEntity.setDescription(inputValues.getDescription());
        articleEntity.setUpdatedAt(Instant.now());

        articleEntity = articleRepository.save(articleEntity);

        return SingletonEntityOutputValues.of(articleEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    @Setter
    public static class InputValues implements UseCase.InputValues {
        private String id;
        private String title;
        private String description;
    }
}
