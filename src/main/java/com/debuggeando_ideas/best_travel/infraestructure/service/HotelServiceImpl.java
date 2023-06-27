package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.response.HotelResponse;
import com.debuggeando_ideas.best_travel.domain.entities.HotelEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IHotelService;
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
@AllArgsConstructor
@Slf4j
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = BestTravelUtil.chooseSortType(page, size, sortType,
            Sort.by(FIELD_BY_SORT));
        Page<HotelEntity> hotelEntities = hotelRepository.findAll(pageRequest);
        return hotelEntities.map(this::entityToResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return hotelRepository
            .findByPriceLessThan(price)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrice(BigDecimal priceMin, BigDecimal priceMax) {
        return hotelRepository
            .findByPriceBetween(priceMin, priceMax)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readGreaterThanRating(Integer rating) {
        return hotelRepository
            .findByRatingGreaterThan(rating)
            .stream()
            .map(this::entityToResponse)
            .collect(Collectors.toSet());
    }

    private HotelResponse entityToResponse(HotelEntity hotelEntity) {
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotelEntity, hotelResponse);
        return hotelResponse;
    }
}
