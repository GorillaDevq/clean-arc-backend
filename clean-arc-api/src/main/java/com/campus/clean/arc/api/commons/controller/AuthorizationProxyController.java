package com.campus.clean.arc.api.commons.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import ru.foodtechlab.lib.auth.integration.core.authorizartion.AuthorizationServiceFacade;
import ru.foodtechlab.lib.auth.integration.proxy.api.authorization.v1.controllers.AuthorizationProxyApiController;

@RestController
@Component
public class AuthorizationProxyController extends AuthorizationProxyApiController {
    public AuthorizationProxyController(AuthorizationServiceFacade authorizationServiceFacade) {
        super(authorizationServiceFacade);
    }
}
