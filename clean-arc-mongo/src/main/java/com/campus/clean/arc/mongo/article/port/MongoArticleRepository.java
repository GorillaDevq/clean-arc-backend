package com.campus.clean.arc.mongo.article.port;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.campus.clean.arc.mongo.article.document.ArticleDoc;
import com.campus.clean.arc.mongo.article.mapper.ArticleMapper;
import com.campus.clean.arc.mongo.article.port.queries.ArticleFilterQuery;
import com.mongodb.client.MongoCollection;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.AbstractMongoRepository;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoArticleRepository
        extends AbstractMongoRepository<String, ArticleEntity, ArticleDoc, ArticleFilters>
        implements ArticleRepository {
    public MongoArticleRepository(MongoTemplate mongoTemplate) {
        super(ArticleDoc.class, new ArticleMapper(), mongoTemplate);
    }

    @Override
    protected AbstractExampleQuery getSearchQuery(ArticleFilters articleFilters) {
        return new ArticleFilterQuery(articleFilters);
    }

    @Override
    public Long count() {
        return mongoTemplate.execute(this.documentClass, MongoCollection::estimatedDocumentCount);
    }
}
