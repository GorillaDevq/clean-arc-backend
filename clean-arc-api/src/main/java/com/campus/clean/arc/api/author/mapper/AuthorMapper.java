package com.campus.clean.arc.api.author.mapper;

import com.campus.clean.arc.api.author.dto.request.AuthorEditRequest;
import com.campus.clean.arc.api.author.dto.request.ChangePasswordRequest;
import com.campus.clean.arc.api.author.dto.request.RegistrationRequest;
import com.campus.clean.arc.api.author.dto.response.AuthorResponse;
import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.campus.clean.arc.domain.author.usecases.ChangePasswordUseCase;
import com.campus.clean.arc.domain.author.usecases.CreateAuthorUseCase;
import com.campus.clean.arc.domain.author.usecases.EditAuthorUseCase;
import com.rcore.rest.api.commons.request.SearchApiRequest;

public class AuthorMapper {
    public static EditAuthorUseCase.InputValues map(AuthorEditRequest request) {
        return EditAuthorUseCase.InputValues.of(request.getId(), request.getFirstName(), request.getLastName());
    }

    public static ChangePasswordUseCase.InputValues map(String authorId, ChangePasswordRequest request) {
        return ChangePasswordUseCase.InputValues.of(authorId, request.getPassword());
    }

    public static CreateAuthorUseCase.InputValues map(RegistrationRequest request) {
        return CreateAuthorUseCase.InputValues.of(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());
    }

    public static AuthorFilters map(SearchApiRequest request) {
        return AuthorFilters.builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .query(request.getQuery())
                .sortDirection(request.getSortDirection())
                .sortName(request.getSortName())
                .build();
    }

    public static AuthorResponse map(AuthorEntity entity) {
        return  AuthorResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
