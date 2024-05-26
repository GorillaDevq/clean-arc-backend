package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorAlreadyExistException;
import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.*;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;
import ru.foodtechlab.lib.auth.service.facade.credential.dto.responses.CredentialResponse;
import ru.foodtechlab.lib.auth.service.facade.role.dto.responses.RoleResponse;
import ru.foodtechlab.lib.auth.service.facade.roleAccess.dto.requests.FindRoleAccessWithFiltersRequest;
import ru.foodtechlab.lib.auth.service.facade.roleAccess.dto.responses.RoleAccessResponse;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

@RequiredArgsConstructor
public class CreateAuthorUseCase extends UseCase<CreateAuthorUseCase.InputValues, SingletonEntityOutputValues<AuthorEntity>> {

    protected final AuthorRepository authorRepository;
    protected final AuthorIdGenerator authorIdGenerator;
    protected final CredentialServiceFacade credentialServiceFacade;
    protected final RoleServiceFacade roleServiceFacade;
    protected final RoleAccessServiceFacade roleAccessServiceFacade;

    private RoleResponse buildRole() {
        String roleCode = "AUTHOR";
        RoleResponse roleResponse = null;

        try {
            roleResponse = roleServiceFacade.findByCode(roleCode).orElse(null);
        } catch (Exception ignore) {}

        if (roleResponse == null) {
            FindRoleAccessWithFiltersRequest req = FindRoleAccessWithFiltersRequest.builder()
                    .limit(1000l)
                    .build();
            List<RoleAccessResponse> accessResponseList = roleAccessServiceFacade.find(req).getItems();

        }
    }

    @Override
    public SingletonEntityOutputValues<AuthorEntity> execute(InputValues inputValues) {
        CredentialResponse response;
        try {
            response = credentialServiceFacade.findByEmail(inputValues.email).orElse(null);
            if (response != null) throw new AuthorAlreadyExistException();
        } catch (CredentialNotFoundException ignore) {}

        RoleResponse roleResponse;
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Builder
    @Data
    public static class InputValues implements UseCase.InputValues {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
    }
}
