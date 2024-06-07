package com.campus.clean.arc.api.article.controller;

import com.campus.clean.arc.api.article.dto.request.ArticleCreateRequest;
import com.campus.clean.arc.api.article.dto.request.ArticleUpdateRequest;
import com.campus.clean.arc.api.article.dto.response.ArticleResponse;
import com.campus.clean.arc.api.article.mapper.ArticleMapper;
import com.campus.clean.arc.api.article.routes.ArticleRoutes;
import com.campus.clean.arc.domain.article.config.ArticleConfig;
import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.rcore.domain.commons.usecase.UseCaseExecutor;
import com.rcore.domain.commons.usecase.model.FiltersInputValues;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.rest.api.commons.request.SearchApiRequest;
import com.rcore.rest.api.commons.response.SearchApiResponse;
import com.rcore.rest.api.commons.response.SuccessApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Tag(name = "Article API")
public class ArticleApiController {

    private final UseCaseExecutor useCaseExecutor;
    private final ArticleConfig articleConfig;

    @Operation(summary = "Create Article")
    @PostMapping(value = ArticleRoutes.CREATE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> create(@RequestBody ArticleCreateRequest request) {
        ArticleEntity entity = useCaseExecutor.execute(
                articleConfig.createArticleUseCase(),
                ArticleMapper.map(request)
        ).getEntity();

        return SuccessApiResponse.of(ArticleMapper.map(entity));
    }

    @Operation(summary = "Update Article")
    @PutMapping(value = ArticleRoutes.EDIT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> update(@PathVariable String id, @RequestBody ArticleUpdateRequest request) {
        ArticleEntity entity = useCaseExecutor.execute(
                articleConfig.updateArticleUseCase(),
                ArticleMapper.map(request)
        ).getEntity();

        return SuccessApiResponse.of(ArticleMapper.map(entity));
    }

    @Operation(summary = "Search articles")
    @GetMapping(value = ArticleRoutes.SEARCH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<SearchApiResponse<ArticleResponse>> search(
            @ModelAttribute SearchApiRequest request
    ) {
        return useCaseExecutor.execute(
                articleConfig.findArticleWithFiltersUseCase(),
                FiltersInputValues.of(ArticleMapper.map(request)),
                o -> SuccessApiResponse.of(
                        SearchApiResponse.withItemsAndCount(
                                o.getResult().getItems().stream()
                                        .map(ArticleMapper::map)
                                        .collect(Collectors.toList()),
                                o.getResult().getCount()
                        )
                )
        );
    }

    @Operation(summary = "Search by id")
    @GetMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> byId(@PathVariable String id) {
        return useCaseExecutor.execute(
                articleConfig.findByIdArticleUseCase(),
                IdInputValues.of(id),
                o -> SuccessApiResponse.of(
                        o.getEntity().map(ArticleMapper::map)
                                .orElseThrow(ArticleNotFoundException::new)
                )
        );
    }

    @Operation(summary = "Delete Article by id")
    @DeleteMapping(value = ArticleRoutes.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<String> update(@PathVariable String id) {
        return useCaseExecutor.execute(
                articleConfig.deleteArticleUseCase(),
                IdInputValues.of(id),
                o -> SuccessApiResponse.of(HttpStatus.OK.name())
        );
    }

}
