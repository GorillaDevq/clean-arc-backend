package com.campus.clean.arc.mongo.article.document;

import com.rcore.database.mongo.commons.document.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Document
public class ArticleDoc extends BaseDocument {
    protected String title;
    protected String description;
}
