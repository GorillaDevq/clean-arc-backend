package com.campus.clean.arc.api.author.controller;

import com.campus.clean.arc.api.author.dto.request.AuthorEditRequest;
import com.campus.clean.arc.api.author.dto.request.ChangePasswordRequest;
import com.campus.clean.arc.api.author.dto.request.RegistrationRequest;
import com.campus.clean.arc.api.author.dto.response.AuthorResponse;
import com.campus.clean.arc.api.author.mapper.AuthorMapper;
import com.campus.clean.arc.api.author.routes.AuthorRoutes;
import com.campus.clean.arc.api.commons.utils.CredentialUtils;
import com.campus.clean.arc.domain.author.config.AuthorConfig;
import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorNotFoundException;
import com.rcore.domain.commons.usecase.UseCaseExecutor;
import com.rcore.domain.commons.usecase.model.FiltersInputValues;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.rest.api.commons.request.SearchApiRequest;
import com.rcore.rest.api.commons.response.SearchApiResponse;
import com.rcore.rest.api.commons.response.SuccessApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.foodtechlab.lib.auth.integration.core.authorizartion.AuthorizationServiceFacade;
import ru.foodtechlab.lib.auth.service.facade.authorization.dto.requests.EmailAuthorizationRequest;
import ru.foodtechlab.lib.auth.service.facade.authorization.dto.responses.BasicAuthorizationResponse;
import ru.foodtechlab.lib.auth.service.facade.authorizationSession.dto.responses.AuthorizationSessionResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Tag(name = "Author API")
public class AuthorApiController {
    private final UseCaseExecutor useCaseExecutor;
    private final AuthorConfig authorConfig;
    private final AuthorizationServiceFacade authorizationServiceFacade;

    @Operation(summary = "Registration")
    @PostMapping(value = AuthorRoutes.REGISTRATION, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<BasicAuthorizationResponse> registration(@RequestBody RegistrationRequest request) {
        AuthorEntity newAuthor = useCaseExecutor.execute(
                authorConfig.createAuthorUseCase(),
                AuthorMapper.map(request)
        ).getEntity();

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
        EmailAuthorizationRequest emailAuthorizationRequest = EmailAuthorizationRequest.of(request.getEmail(), request.getPassword());

        emailAuthorizationRequest.setIp(httpServletRequest.getRemoteAddr());
        emailAuthorizationRequest.setDeviceId("proxy-support-legacy-device-id");
        emailAuthorizationRequest.setApplicationDetails(
                AuthorizationSessionResponse.ApplicationDetails.builder()
                        .name("proxy-support-legacy-device-id")
                        .platform("consumer")
                        .versionName("legacy")
                        .build()
        );

        return SuccessApiResponse.of(authorizationServiceFacade.emailAuthorization(emailAuthorizationRequest));
    }

    @Operation(summary = "Search authors")
    @GetMapping(value = AuthorRoutes.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<SearchApiResponse<AuthorResponse>> search(
            @ModelAttribute SearchApiRequest request
    ) {
        return useCaseExecutor.execute(
            authorConfig.findAuthorWithFiltersUseCase(),
            FiltersInputValues.of(AuthorMapper.map(request)),
            o -> SuccessApiResponse.of(
                    SearchApiResponse.withItemsAndCount(
                            o.getResult().getItems().stream()
                                    .map(AuthorMapper::map)
                                    .collect(Collectors.toList()),
                            o.getResult().getCount()
                    )
            )
        );
    }

    @Operation(summary = "Get Author by ID")
    @GetMapping(value = AuthorRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<AuthorResponse> searchById(@PathVariable String id) {
        return useCaseExecutor.execute(
                authorConfig.findAuthorByIdUseCase(),
                IdInputValues.of(id),
                o -> SuccessApiResponse.of(
                        o.getEntity().map(AuthorMapper::map)
                                .orElseThrow(AuthorNotFoundException::new)
                )
        );
    }

    @Operation(summary = "Get Author by Credential")
    @GetMapping(value = AuthorRoutes.ME, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<AuthorResponse> me() {
        return useCaseExecutor.execute(
                authorConfig.findAuthorByCredentialsUseCase(),
                IdInputValues.of(CredentialUtils.getCredentialId()),
                o -> SuccessApiResponse.of(
                        o.getEntity().map(AuthorMapper::map)
                                .orElseThrow(AuthorNotFoundException::new)
                )
        );
    }

    @Operation(summary = "Edit Author info by ID")
    @PutMapping(value = AuthorRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<AuthorResponse> editById(
            @PathVariable String id,
            @RequestBody AuthorEditRequest request) {
        AuthorEntity entity = useCaseExecutor.execute(
                authorConfig.editAuthorUseCase(),
                AuthorMapper.map(request)
        ).getEntity();

        return SuccessApiResponse.of(AuthorMapper.map(entity));
    }

    @Operation(summary = "Edit Author info by Credential")
    @PutMapping(value = AuthorRoutes.ME, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<AuthorResponse> editByCredential(
            @RequestBody AuthorEditRequest request) {
        AuthorEntity current = useCaseExecutor.execute(
                authorConfig.findAuthorByCredentialsUseCase(),
                IdInputValues.of(CredentialUtils.getCredentialId())
        ).getEntity().orElseThrow(AuthorNotFoundException::new);

        request.setId(current.getId());
        AuthorEntity entity = useCaseExecutor.execute(
                authorConfig.editAuthorUseCase(),
                AuthorMapper.map(request)
        ).getEntity();

        return SuccessApiResponse.of(AuthorMapper.map(entity));
    }

    @Operation(summary = "Change Author password for current Author")
    @PostMapping(value = AuthorRoutes.CHANGE_PASSWORD, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<AuthorResponse> editByCredential(
            @RequestBody ChangePasswordRequest request) {
        AuthorEntity current = useCaseExecutor.execute(
                authorConfig.findAuthorByCredentialsUseCase(),
                IdInputValues.of(CredentialUtils.getCredentialId())
        ).getEntity().orElseThrow(AuthorNotFoundException::new);

        useCaseExecutor.execute(
                authorConfig.changePasswordUseCase(),
                AuthorMapper.map(current.getId(), request)
        );

        return SuccessApiResponse.of(AuthorMapper.map(current));
    }
}
