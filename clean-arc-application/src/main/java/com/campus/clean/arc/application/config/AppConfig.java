package com.campus.clean.arc.application.config;

import com.campus.clean.arc.domain.author.config.AuthorConfig;
import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcore.commons.utils.StringUtils;
import com.rcore.domain.commons.usecase.UseCaseExecutor;
import com.rcore.domain.commons.usecase.impl.ValidatingUseCaseExecutor;
import com.rcore.domain.security.port.AccessChecker;
import com.rcore.domain.security.port.CredentialIdentityService;
import com.rcore.rest.api.spring.commons.jackson.datetime.InstantDeserializer;
import com.rcore.rest.api.spring.commons.jackson.datetime.InstantSerializer;
import com.rcore.rest.api.spring.commons.jackson.datetime.LocalDateTimeDeserializer;
import com.rcore.rest.api.spring.commons.jackson.datetime.LocalDateTimeSerializer;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import ru.foodtechlab.lib.auth.integration.core.authorizartion.impl.AccessCheckerViaAuthService;
import ru.foodtechlab.lib.auth.integration.core.authorizartion.impl.CredentialIdentityServiceViaAuthService;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.CheckAccessServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;
import ru.foodtechlab.lib.auth.integration.restapi.feign.authorization.FeignCredentialServiceClient;
import ru.foodtechlab.lib.auth.integration.restapi.feign.authorization.impl.FeignHTTPAuthCredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.restapi.feign.role.access.FeignRoleAccessServiceClient;
import ru.foodtechlab.lib.auth.integration.restapi.feign.role.access.impl.FeignHTTPRoleAccessFacade;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        builder.serializerByType(LocalDate.class, new LocalDateTimeSerializer());
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.deserializerByType(LocalDate.class, new LocalDateTimeDeserializer());
        builder.serializerByType(Instant.class, new InstantSerializer());
        builder.deserializerByType(Instant.class, new InstantDeserializer());
        return builder.build();
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties,
                                               Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() &&
                (StringUtils.hasText(basePath) ||
                        ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }

    @Bean
    public WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping(
            WebEndpointsSupplier webEndpointsSupplier,
            ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier,
            EndpointMediaTypes endpointMediaTypes,
            CorsEndpointProperties corsEndpointProperties,
            WebEndpointProperties webEndpointProperties,
            Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(
                webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints,
                endpointMediaTypes, corsEndpointProperties.toCorsConfiguration(),
                new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping, null);
    }

    @Bean
    public UseCaseExecutor useCaseExecutor() {
        return new ValidatingUseCaseExecutor();
    }

    @Bean
    public CredentialIdentityService credentialIdentityService(FeignCredentialServiceClient feignCredentialServiceClient) {
        return new CredentialIdentityServiceViaAuthService(new FeignHTTPAuthCredentialServiceFacade(feignCredentialServiceClient));
    }

    @Bean
    RoleAccessServiceFacade roleAccessServiceFacade(FeignRoleAccessServiceClient feignRoleAccessServiceClient) {
        return new FeignHTTPRoleAccessFacade(feignRoleAccessServiceClient);
    }

    @Bean
    public AccessChecker accessChecker(CheckAccessServiceFacade checkAccessServiceFacade) {
        return new AccessCheckerViaAuthService(checkAccessServiceFacade);
    }

    //// Block for our app beans
    @Bean
    public AuthorConfig authorConfig(
            CredentialServiceFacade credentialServiceFacade,
            RoleServiceFacade roleFacade,
            RoleAccessServiceFacade roleAccessServiceFacade,
            AuthorRepository repository,
            AuthorIdGenerator idGenerator) {
        return new AuthorConfig (
                repository,
                credentialServiceFacade,
                idGenerator,
                roleFacade,
                roleAccessServiceFacade
        );
    }

}
