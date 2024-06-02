package com.campus.clean.arc.api.author.routes;

import com.rcore.rest.api.commons.routes.BaseRoutes;

public class AuthorRoutes {
    public static final String ROOT = BaseRoutes.API + BaseRoutes.V1 + "/author";
    public static final String BY_ID = ROOT + "/{id}";
    public static final String ME = ROOT + "/me";
    public static final String CHANGE_PASSWORD = ME + "/change-password";
    public static final String REGISTRATION = BaseRoutes.NOT_SECURE + BaseRoutes.API + BaseRoutes.V1 + "/registration";
}
