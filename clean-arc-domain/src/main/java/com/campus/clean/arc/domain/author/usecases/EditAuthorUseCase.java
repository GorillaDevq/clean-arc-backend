package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorNotFoundException;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.*;

import java.time.Instant;

@RequiredArgsConstructor
public class EditAuthorUseCase extends UseCase<EditAuthorUseCase.InputValues, SingletonEntityOutputValues<AuthorEntity>> {

    protected final AuthorRepository authorRepository;

    @Override
    public SingletonEntityOutputValues<AuthorEntity> execute(InputValues inputValues) {
        AuthorEntity authorEntity = authorRepository.findById(inputValues.id).orElseThrow(AuthorNotFoundException::new);
        authorEntity.setFirstName(inputValues.getFirstName());
        authorEntity.setLastName(inputValues.getLastName());
        authorEntity.setUpdatedAt(Instant.now());

        authorEntity = authorRepository.save(authorEntity);
        return SingletonEntityOutputValues.of(authorEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Builder
    @Data
    public static class InputValues implements UseCase.InputValues {
        private String id;
        private String firstName;
        private String lastName;
    }
}
