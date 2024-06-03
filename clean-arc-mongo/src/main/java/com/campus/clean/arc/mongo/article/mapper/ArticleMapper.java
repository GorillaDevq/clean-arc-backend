package com.campus.clean.arc.mongo.article.mapper;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.mongo.article.document.ArticleDoc;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;

import java.time.Instant;

public class ArticleMapper implements ExampleDataMapper<ArticleEntity, ArticleDoc> {

    private final ObjectIdGenerator objectIdGenerator = new ObjectIdGenerator();

    @Override
    public ArticleEntity inverseMap(ArticleDoc articleDoc) {
        return ArticleEntity.builder()
                .id(articleDoc.getId().toString())
                .title(articleDoc.getTitle())
                .description(articleDoc.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Override
    public ArticleDoc map(ArticleEntity articleEntity) {
        return ArticleDoc.builder()
                .id(objectIdGenerator.parse(articleEntity.getId()))
                .title(articleEntity.getTitle())
                .description(articleEntity.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
}
