package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel.api.models.response.HotelResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ReservationResponse;
import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.HotelEntity;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IReservationService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        HotelEntity hotelEntity = getHotelEntity(request.getIdHotel());
        CustomerEntity customerEntity = getCustomerEntity(request.getIdClient());

        ReservationEntity reservationEntity = ReservationEntity
            .builder()
            .id(UUID.randomUUID())
            .hotel(hotelEntity)
            .customer(customerEntity)
            .totalDays(request.getTotalDays())
            .dateTimeReservation(LocalDateTime.now())
            .dateStart(LocalDate.now())
            .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
            .price(hotelEntity.getPrice()
                .add(hotelEntity.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE_RESERVATION)))
            .build();
        ReservationEntity save = saveReservationEntity(reservationEntity);
        log.info("Reservation created: {}", save.getId());
        return entityToResponse(save);
    }

    @Override
    public ReservationResponse read(UUID id) {
        return null;
    }

    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    private ReservationEntity saveReservationEntity(ReservationEntity reservationEntity) {
        return reservationRepository.save(reservationEntity);
    }

    private CustomerEntity getCustomerEntity(String idCustomer) {
        return customerRepository.findById(idCustomer)
            .orElseThrow(() -> new IllegalStateException(Constant.ERROR_CUSTOMER_NOT_FOUND));
    }

    private HotelEntity getHotelEntity(Long idHotel) {
        return hotelRepository.findById(idHotel)
            .orElseThrow(() -> new IllegalStateException(
                Constant.HOTEL_NOT_FOUND));
    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {
        ReservationResponse reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(entity, reservationResponse);
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        reservationResponse.setHotel(hotelResponse);
        return reservationResponse;
    }
}
