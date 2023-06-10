package com.debuggeando_ideas.best_travel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BestTravelApplication implements CommandLineRunner {

    private final MyService service;

    public BestTravelApplication(MyService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestTravelApplication.class, args);
    }

    @Override
    public void run(String... args) {
        service.doSomething();
    }
}

@Component
class MyService {

    public void doSomething() {
        System.out.println("Hola mundo");
    }
}
