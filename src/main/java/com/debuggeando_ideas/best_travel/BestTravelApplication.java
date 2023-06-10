package com.debuggeando_ideas.best_travel;

import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TourRepository;
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
        flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678"))
            .ifPresent(flyEntity -> {
                System.out.println("flyEntity = " + flyEntity.getId());
                System.out.println("flyEntity = " + flyEntity.getPrice());
                System.out.println("flyEntity = " + flyEntity.getOriginName());
                System.out.println("flyEntity = " + flyEntity.getDestinyName());
                flyEntity.getTickets().forEach(t -> {
                    System.out.println("t = " + t.getId());
                    System.out.println("t = " + t.getPrice());
                    System.out.println("t.getDepartureDate() = " + t.getDepartureDate());
                    System.out.println("t.getPurchaseDate() = " + t.getPurchaseDate());
                });
            });
    }
}
