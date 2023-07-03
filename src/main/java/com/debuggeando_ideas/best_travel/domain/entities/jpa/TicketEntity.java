package com.debuggeando_ideas.best_travel.domain.entities.jpa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime departureDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime arrivalDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime purchaseDate;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fly_id", referencedColumnName = "id")
    private FlyEntity fly;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourEntity tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "dni")
    private CustomerEntity customer;
}
