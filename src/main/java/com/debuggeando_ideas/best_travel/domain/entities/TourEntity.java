package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "tour")
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude // annotation to prevent infinite loops
    @EqualsAndHashCode.Exclude // annotation to prevent infinite loops
    @OneToMany(
        mappedBy = "tour",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<TicketEntity> tickets;
    @ToString.Exclude // annotation to prevent infinite loops
    @EqualsAndHashCode.Exclude // annotation to prevent infinite loops
    @OneToMany(
        mappedBy = "tour",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<ReservationEntity> reservations;
    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "dni")
    private CustomerEntity customer;

}
