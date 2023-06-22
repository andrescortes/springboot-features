package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel.api.models.response.TourResponse;
import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.HotelEntity;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TourEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TourRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITourService;
import com.debuggeando_ideas.best_travel.infraestructure.helper.TourHelper;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final TourHelper tourHelper;

    private static Set<UUID> getTicketIds(TourEntity tourSaved) {
        return tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet());
    }

    private static Set<UUID> getReservationIds(TourEntity tourSaved) {
        return tourSaved.getReservations().stream().map(ReservationEntity::getId)
            .collect(Collectors.toSet());
    }

    @Override
    public void removeTicket(UUID ticketId, Long tourId) {

    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        return null;
    }

    @Override
    public void removeReservation(UUID reservationId, Long tourId) {

    }

    @Override
    public UUID addReservation(Long reservationId, Long tourId) {
        return null;
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
        return TourResponse.builder()
            .id(tourSaved.getId())
            .reservationIds(getReservationIds(tourSaved))
            .ticketIds(getTicketIds(tourSaved))
            .build();
    }

    private HotelEntity getHotelEntity(Long hotelId) {
        return hotelRepository.findById(hotelId)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
    }

    private FlyEntity getFlyEntity(Long flyId) {
        return flyRepository.findById(flyId)
            .orElseThrow(() -> new IllegalArgumentException("Fly not found"));
    }

    private CustomerEntity getCustomer(String customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Override
    public TourResponse read(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
