package com.campus.clean.arc.mongo.article.port;

import com.campus.clean.arc.domain.article.port.ArticleIdGenerator;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MongoArticleIdGenerator extends ObjectIdGenerator implements ArticleIdGenerator<ObjectId> {
}
