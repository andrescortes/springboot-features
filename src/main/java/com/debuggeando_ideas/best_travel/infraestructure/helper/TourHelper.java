package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.HotelEntity;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    public Set<TicketEntity> createdTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        HashSet<TicketEntity> ticketHashSet = new HashSet<>(flights.size());
        flights.forEach(fly -> {
            TicketEntity ticketEntity = TicketEntity
                .builder()
                .id(UUID.randomUUID())
                .price(fly.getPrice()
                    .add(fly.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE)))
                .fly(fly)
                .customer(customer)
                .departureDate(BestTravelUtil.getRandomSoon())
                .arrivalDate(BestTravelUtil.getRandomLater())
                .purchaseDate(LocalDateTime.now())
                .build();
            ticketHashSet.add(ticketRepository.save(ticketEntity));
        });
        return ticketHashSet;
    }

    public Set<ReservationEntity> createReservations(Map<HotelEntity, Integer> hotels, CustomerEntity customer) {
        HashSet<ReservationEntity> reservationHashSet = new HashSet<>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            ReservationEntity reservationEntity = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(hotel.getPrice()
                    .add(hotel.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE_RESERVATION)))
                .build();
            reservationHashSet.add(reservationRepository.save(reservationEntity));
        });
        return reservationHashSet;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
        TicketEntity ticketEntity = TicketEntity.builder()
            .id(UUID.randomUUID())
            .price(fly.getPrice()
                .add(fly.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE)))
            .fly(fly)
            .customer(customer)
            .departureDate(BestTravelUtil.getRandomSoon())
            .arrivalDate(BestTravelUtil.getRandomLater())
            .purchaseDate(LocalDateTime.now())
            .build();
        return ticketRepository.save(ticketEntity);
    }

    public ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays) {
        ReservationEntity reservationEntity = ReservationEntity.builder()
            .id(UUID.randomUUID())
            .hotel(hotel)
            .customer(customer)
            .totalDays(totalDays)
            .dateTimeReservation(LocalDateTime.now())
            .dateStart(LocalDate.now())
            .dateEnd(LocalDate.now().plusDays(totalDays))
            .price(hotel.getPrice()
                .add(hotel.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE_RESERVATION)))
            .build();
        return reservationRepository.save(reservationEntity);
    }
}
