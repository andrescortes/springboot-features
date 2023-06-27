package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IFlyService;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import com.debuggeando_ideas.best_travel.util.SortType;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
@AllArgsConstructor
public class FlyServiceImpl implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = BestTravelUtil.chooseSortType(page, size, sortType,
            Sort.by(FIELD_BY_SORT));
        Page<FlyEntity> flyEntities = flyRepository.findAll(pageRequest);
        return flyEntities.map(this::entityToResponse);
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return flyRepository
            .selectLessPrice(price)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal priceMin, BigDecimal priceMax) {
        return flyRepository
            .selectBetweenPrice(priceMin, priceMax)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readByOriginAndDestination(String origin, String destination) {
        return flyRepository
            .selectOriginAndDestiny(origin, destination)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    private FlyResponse entityToResponse(FlyEntity flyEntity) {
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(flyEntity, flyResponse);
        return flyResponse;
    }
}
