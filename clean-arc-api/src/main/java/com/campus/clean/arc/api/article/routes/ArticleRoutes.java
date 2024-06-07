package com.campus.clean.arc.api.article.routes;

import com.rcore.rest.api.commons.routes.BaseRoutes;

public class ArticleRoutes {
    private static final String ROOT = BaseRoutes.API + BaseRoutes.V1 + "/article";
    public static final String CREATE = ROOT;
    public static final String SEARCH = ROOT;
    public static final String BY_ID = ROOT + "/{id}";
    public static final String EDIT = BY_ID;
    public static final String DELETE = BY_ID;
}
