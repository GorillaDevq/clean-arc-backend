package com.campus.clean.arc.domain.article.port.filters;

import com.rcore.domain.commons.port.dto.SearchFilters;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class ArticleFilters extends SearchFilters {
}
