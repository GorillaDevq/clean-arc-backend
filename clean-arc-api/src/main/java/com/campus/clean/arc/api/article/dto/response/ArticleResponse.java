package com.campus.clean.arc.api.article.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ArticleResponse {
    private String id;
    private String title;
    private String description;
    private Instant createdAt;
}
