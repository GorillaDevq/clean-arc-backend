package com.campus.clean.arc.mongo.author.mapper;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.mongo.author.document.AuthorDoc;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;

import java.time.Instant;

public class AuthorMapper implements ExampleDataMapper<AuthorEntity, AuthorDoc> {
    private  final ObjectIdGenerator objectIdGenerator = new ObjectIdGenerator();
    @Override
    public AuthorEntity inverseMap(AuthorDoc item) {
        return AuthorEntity.builder()
                .id(item.getId().toString())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())
                .credentialId(item.getCredentialId())
                .build();
    }

    @Override
    public AuthorDoc map(AuthorEntity item) {
        return AuthorDoc.builder()
                .id(objectIdGenerator.parse(item.getId()))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())
                .credentialId(item.getCredentialId())
                .build();
    }
}
