package com.debuggeando_ideas.best_travel.infraestructure.abstractservice;

import com.debuggeando_ideas.best_travel.util.enums.SortType;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.data.domain.Page;

public interface CatalogService<R> {

    String FIELD_BY_SORT = "price";

    Page<R> readAll(Integer page, Integer size, SortType sortType);

    Set<R> readLessPrice(BigDecimal price);

    Set<R> readBetweenPrice(BigDecimal priceMin, BigDecimal priceMax);
}
