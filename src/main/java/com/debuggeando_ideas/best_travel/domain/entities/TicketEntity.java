package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity(name = "ticket")
public class TicketEntity {

    @Id
    private UUID id;
    private BigDecimal price;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;
    @ManyToOne
    @JoinColumn(name = "fly_id", referencedColumnName = "id")
    private FlyEntity fly;
    @ManyToOne
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourEntity tour;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "dni")
    private CustomerEntity customer;
}
