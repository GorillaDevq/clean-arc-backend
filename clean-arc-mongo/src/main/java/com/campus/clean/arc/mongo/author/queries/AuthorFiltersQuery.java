package com.campus.clean.arc.mongo.author.queries;

import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import com.rcore.domain.commons.port.dto.SearchFilters;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class AuthorFiltersQuery extends AbstractExampleQuery {
    private final AuthorFilters filters;

    public AuthorFiltersQuery(AuthorFilters filters) {
        super(filters);
        this.filters = filters;
    }

    @Override
    public Criteria getCriteria() {
        Criteria finalCriteria = new Criteria();

        List<Criteria> currentCriteria = new ArrayList<>();

        if (filters.getCredentialId() != null) {
            currentCriteria.add(Criteria.where("credentialId").is(filters.getCredentialId()));
        }
        if (filters.getEmail() != null) {
            currentCriteria.add(Criteria.where("email").is(filters.getEmail()));
        }

        if (filters.getQuery() != null && !filters.getQuery().isEmpty()) {
            String[] query = filters.getQuery().trim().split(" ");
            List<Criteria> textSearch = new ArrayList<>();
            for (String q : query) {
                textSearch.add(Criteria.where("firstName").regex(q, "i"));
                textSearch.add(Criteria.where("lastName").regex(q, "i"));
            }
            currentCriteria.add(
                    new Criteria().orOperator(
                            textSearch.toArray(
                                    new Criteria[textSearch.size()]
                            )));
        }

        if (currentCriteria.isEmpty()) return finalCriteria;
        if (currentCriteria.size() == 1) return currentCriteria.get(0);

        finalCriteria.andOperator(
                currentCriteria.toArray(
                        new Criteria[currentCriteria.size()]
                ));

        return finalCriteria;
    }
}
