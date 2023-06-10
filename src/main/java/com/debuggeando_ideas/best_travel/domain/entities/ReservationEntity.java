package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
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
@Entity(name = "reservation")
public class ReservationEntity {

    @Id
    private UUID id;
    @Column(name = "date_reservation")
    private LocalDateTime dateTimeReservation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer totalDays;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private HotelEntity hotel;
    @ManyToOne
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourEntity tour;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "dni")
    private CustomerEntity customer;

}
