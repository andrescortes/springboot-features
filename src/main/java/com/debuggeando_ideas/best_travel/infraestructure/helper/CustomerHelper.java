package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Transactional
@Component
@AllArgsConstructor
@Slf4j
public class CustomerHelper {

    private final CustomerRepository customerRepository;

    public void increaseCustomerParams(String customerId, Class<?> type) {
        customerRepository
            .findById(customerId)
            .ifPresent(customer -> {
                switch (type.getSimpleName()) {
                    case "TourServiceImpl" -> customer.setTotalTours(customer.getTotalTours() + 1);
                    case "TicketServiceImpl" -> customer.setTotalFlights(customer.getTotalFlights() + 1);
                    case "ReservationServiceImpl" -> customer.setTotalLodgings(customer.getTotalLodgings() + 1);
                    default -> log.info("Customer not updated");
                }
                customerRepository.save(customer);
            });
    }
}
