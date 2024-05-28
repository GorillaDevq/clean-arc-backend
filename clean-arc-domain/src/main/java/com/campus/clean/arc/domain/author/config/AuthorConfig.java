package com.campus.clean.arc.domain.author.config;

import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.usecases.*;
import lombok.Getter;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import lombok.experimental.Accessors;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;

@Accessors(fluent = true)
@Getter
public class AuthorConfig {
    private final ChangePasswordUseCase changePasswordUseCase;
    private final CreateAuthorUseCase createAuthorUseCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;
    private final EditAuthorUseCase editAuthorUseCase;
    private final FindAuthorByCredentialsUseCase findAuthorByCredentialsUseCase;
    private final FindAuthorByIdUseCase findAuthorByIdUseCase;
    private final FindAuthorWithFiltersUseCase findAuthorWithFiltersUseCase;

    public AuthorConfig(
            AuthorRepository authorRepository,
            CredentialServiceFacade credentialServiceFacade,
            AuthorIdGenerator authorIdGenerator,
            RoleServiceFacade roleServiceFacade,
            RoleAccessServiceFacade roleAccessServiceFacade
            ) {
        this.changePasswordUseCase = new ChangePasswordUseCase(authorRepository, credentialServiceFacade);
        this.createAuthorUseCase = new CreateAuthorUseCase(
                authorRepository,
                authorIdGenerator,
                credentialServiceFacade,
                roleServiceFacade,
                roleAccessServiceFacade
        );
        this.deleteAuthorUseCase = new DeleteAuthorUseCase(authorRepository);
        this.editAuthorUseCase = new EditAuthorUseCase(authorRepository);
        this.findAuthorByCredentialsUseCase = new FindAuthorByCredentialsUseCase(authorRepository);
        this.findAuthorByIdUseCase = new FindAuthorByIdUseCase(authorRepository);
        this.findAuthorWithFiltersUseCase = new FindAuthorWithFiltersUseCase(authorRepository);
    }
}
