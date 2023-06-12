package com.debuggeando_ideas.best_travel;

import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TourEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TourRepository;
import com.debuggeando_ideas.best_travel.util.AeroLineType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

    @Override
    public void run(String... args) {
        CustomerEntity customerEntity = customerRepository.findById("VIKI771012HMCRG093")
            .orElse(CustomerEntity.builder()
                .dni("VIKI771012HMCRG094")
                .fullName("Víctor Robles")
                .phoneNumber("0987654321")
                .creditCard("1234567890123456")
                .totalFlights(0)
                .totalLodgings(0)
                .totalTours(0)
                .build());
        FlyEntity flyEntity = FlyEntity.builder()
            .aeroLine(AeroLineType.aero_gold)
            .build();
        TourEntity tourOne = TourEntity.builder()
            .customer(customerEntity)
            .build();
        TicketEntity ticketEntity = TicketEntity.builder()
            .id(UUID.randomUUID())
            .price(new BigDecimal("4500.10"))
            .arrivalDate(LocalDate.now())
            .purchaseDate(LocalDate.now())
            .fly(flyEntity)
            .tour(tourOne)
            .build();

        ReservationEntity reservationEntity = ReservationEntity.builder()
            .id(UUID.randomUUID())
            .dateEnd(LocalDate.now().plusDays(4))
            .dateStart(LocalDate.now())
            .dateTimeReservation(LocalDateTime.now())
            .totalDays(10)
            .price(2500.10)
            .customer(customerEntity)
            .build();



        TourEntity tourEntity = TourEntity.builder()
            .customer(customerEntity)
            .build();
        tourEntity.addReservation(reservationEntity);
        tourEntity.updateReservations();

        tourEntity.addTicket(ticketEntity);
        tourEntity.updateTicket();
        TourEntity save = tourRepository.save(tourEntity);
        log.info("Tour: {}", save.getId());
        String fullName = save.getCustomer().getFullName();
        System.out.println("fullName = " + fullName);
        save.getReservations().forEach(reservation -> {
            System.out.println("reservation = " + reservation.getId());
            System.out.println("reservation.getDateStart() = " + reservation.getDateStart());
        });
        save.getTickets().forEach(ticket -> {
            System.out.println("ticket = " + ticket.getId());
            System.out.println("ticket.getPurchaseDate() = " + ticket.getPurchaseDate());
            System.out.println("date = " + ticket.getDepartureDate());
        });

    }
}
