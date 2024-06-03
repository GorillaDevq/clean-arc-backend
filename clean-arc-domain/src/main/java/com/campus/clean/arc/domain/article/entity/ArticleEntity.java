package com.campus.clean.arc.domain.article.entity;

import com.rcore.domain.commons.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class ArticleEntity extends BaseEntity<String> {
    protected String title;
    protected String description;
}
