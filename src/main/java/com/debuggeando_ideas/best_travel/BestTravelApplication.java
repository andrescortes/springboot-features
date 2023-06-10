package com.debuggeando_ideas.best_travel;

import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner {

    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;

    public BestTravelApplication(FlyRepository flyRepository, HotelRepository hotelRepository) {
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

    @Override
    public void run(String... args) {
        flyRepository.findById(15L)
            .ifPresent(flyEntity -> log.info("fly: {}", flyEntity));
        hotelRepository.findById(7L).ifPresent(hotelEntity -> log.info("hotel: {}", hotelEntity.toString()));
    }
}
