package com.campus.clean.arc.mongo.author.port;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.campus.clean.arc.mongo.author.document.AuthorDoc;
import com.campus.clean.arc.mongo.author.mapper.AuthorMapper;
import com.campus.clean.arc.mongo.author.queries.AuthorFiltersQuery;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.AbstractMongoRepository;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoAuthorRepository
        extends AbstractMongoRepository<String, AuthorEntity, AuthorDoc, AuthorFilters>
        implements AuthorRepository {

    public MongoAuthorRepository(MongoTemplate mongoTemplate) {
        super(AuthorDoc.class, new AuthorMapper(), mongoTemplate);
    }

    @Override
    public Optional<AuthorEntity> findByEmail(String email) {
        Criteria criteria = Criteria.where("email").is(email);
        Query query = new Query(criteria);
        AuthorDoc authorDoc = mongoTemplate.findOne(query, documentClass);
        return Optional.ofNullable(authorDoc == null ? null : mapper.inverseMap(authorDoc));
    }

    @Override
    public Optional<AuthorEntity> findByCredentialId(String credentialId) {
        Criteria criteria = Criteria.where("credentialId").is(credentialId);
        Query query = new Query(criteria);
        AuthorDoc authorDoc = mongoTemplate.findOne(query, documentClass);
        return Optional.ofNullable(authorDoc == null ? null : mapper.inverseMap(authorDoc));
    }

    @Override
    protected AbstractExampleQuery getSearchQuery(AuthorFilters authorFilters) {
        return new AuthorFiltersQuery(authorFilters);
    }
}
