package com.campus.clean.arc.mongo.author.port;

import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MongoAuthorIdGenerator extends ObjectIdGenerator implements AuthorIdGenerator<ObjectId> {
}
