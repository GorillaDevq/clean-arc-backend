package com.campus.clean.arc.domain.author.port;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.rcore.domain.commons.port.CRUDRepository;

import java.util.Optional;

public interface AuthorRepository extends CRUDRepository<String, AuthorEntity, AuthorFilters> {
    Optional<AuthorEntity> findByEmail(String email);
    Optional<AuthorEntity> findByCredentialId(String credentialId);
}
