package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel.api.models.response.TourResponse;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.HotelEntity;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.TourEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.TourRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITourService;
import com.debuggeando_ideas.best_travel.infraestructure.helper.CustomerHelper;
import com.debuggeando_ideas.best_travel.infraestructure.helper.EmailHelper;
import com.debuggeando_ideas.best_travel.infraestructure.helper.TourHelper;
import com.debuggeando_ideas.best_travel.util.enums.Tables;
import com.debuggeando_ideas.best_travel.util.exceptions.IdNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;
    private final EmailHelper emailHelper;

    private static Set<UUID> getTicketIds(TourEntity tourSaved) {
        return tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet());
    }

    private static Set<UUID> getReservationIds(TourEntity tourSaved) {
        return tourSaved.getReservations().stream().map(ReservationEntity::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {
        TourEntity tourEntity = getTourEntity(tourId);
        tourEntity.removeTicket(ticketId);
        tourRepository.save(tourEntity);
    }

    @Override
    public UUID addTicket(Long tourId, Long flyId) {
        TourEntity tourEntity = getTourEntity(tourId);
        FlyEntity flyEntity = getFlyEntity(flyId);
        TicketEntity ticket = tourHelper.createTicket(flyEntity, tourEntity.getCustomer());
        tourEntity.addTicket(ticket);
        tourRepository.save(tourEntity);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        TourEntity tourEntity = getTourEntity(tourId);
        tourEntity.removeReservation(reservationId);
        tourRepository.save(tourEntity);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        TourEntity tourEntity = getTourEntity(tourId);
        HotelEntity hotelEntity = getHotelEntity(hotelId);
        ReservationEntity reservationEntity = tourHelper.createReservation(hotelEntity,
            tourEntity.getCustomer(), totalDays);
        tourEntity.addReservation(reservationEntity);
        tourRepository.save(tourEntity);
        return reservationEntity.getId();
    }


    @Override
    public TourResponse create(TourRequest request) {
        HashSet<FlyEntity> flyEntities = new HashSet<>();
        HashMap<HotelEntity, Integer> hotels = new HashMap<>();

        request.getFlights().forEach(fly -> flyEntities.add(getFlyEntity(fly.getId())));
        request.getHotels()
            .forEach(hotel -> hotels.put(getHotelEntity(hotel.getId()), hotel.getTotalDays()));
        CustomerEntity customer = getCustomer(request.getCustomerId());

        TourEntity tourEntity = TourEntity.builder()
            .tickets(tourHelper.createdTickets(flyEntities, customer))
            .reservations(tourHelper.createReservations(hotels, customer))
            .customer(customer)
            .build();
        TourEntity tourSaved = tourRepository.save(tourEntity);
        customerHelper.increaseCustomerParams(customer.getDni(), TourServiceImpl.class);
        if (Objects.nonNull(request.getEmail())){
            emailHelper.sendMail(request.getEmail(), customer.getFullName(),Tables.TOURS.getTableName());
        }

        return TourResponse.builder()
            .id(tourSaved.getId())
            .reservationIds(getReservationIds(tourSaved))
            .ticketIds(getTicketIds(tourSaved))
            .build();
    }

    private HotelEntity getHotelEntity(Long hotelId) {
        return hotelRepository.findById(hotelId)
            .orElseThrow(() -> new IdNotFoundException(Tables.HOTELS.getTableName()));
    }

    private FlyEntity getFlyEntity(Long flyId) {
        return flyRepository.findById(flyId)
            .orElseThrow(() -> new IllegalArgumentException(Tables.FLIES.getTableName()));
    }

    private CustomerEntity getCustomer(String customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException(Tables.CUSTOMERS.getTableName()));
    }

    @Override
    public TourResponse read(Long id) {
        TourEntity tourEntity = getTourEntity(id);
        return TourResponse.builder()
            .id(tourEntity.getId())
            .reservationIds(getReservationIds(tourEntity))
            .ticketIds(getTicketIds(tourEntity))
            .build();

    }

    private TourEntity getTourEntity(Long id) {
        return tourRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(Tables.TOURS.getTableName()));
    }

    @Override
    public void delete(Long id) {
        TourEntity tourEntity = getTourEntity(id);
        tourRepository.delete(tourEntity);
    }
}
