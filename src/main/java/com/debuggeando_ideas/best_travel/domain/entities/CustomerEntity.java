package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity(name = "customer")
public class CustomerEntity {

    @Id
    @Column(length = 20)
    private String dni;
    @Column(length = 50)
    private String fullName;
    @Column(length = 20)
    private String creditCard;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;
    @Column(length = 20)
    private String phoneNumber;
    @ToString.Exclude // annotation to prevent infinite loops
    @EqualsAndHashCode.Exclude // annotation to prevent infinite loops
    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<TicketEntity> tickets;
    @ToString.Exclude // annotation to prevent infinite loops
    @EqualsAndHashCode.Exclude // annotation to prevent infinite loops
    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<TourEntity> tours;
    @ToString.Exclude // annotation to prevent infinite loops
    @EqualsAndHashCode.Exclude // annotation to prevent infinite loops
    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<ReservationEntity> reservations;
}
