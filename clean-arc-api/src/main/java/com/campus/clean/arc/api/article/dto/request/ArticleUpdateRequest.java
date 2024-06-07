package com.campus.clean.arc.api.article.dto.request;

import lombok.Getter;

@Getter
public class ArticleUpdateRequest {
    protected String id;
    protected String title;
    protected String description;
}
