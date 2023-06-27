package com.debuggeando_ideas.best_travel.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
@Entity(name = "tour")
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //no delete tickets because for type fetch is EAGER, when is LAZY its deleted
    @JsonIgnore
    @OneToMany(
        mappedBy = "tour",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<TicketEntity> tickets;

    @JsonIgnore
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

    @PrePersist
    @PreRemove
    public void updateFks() {
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }

    public void addTicket(TicketEntity ticket) {
        if (Objects.isNull(this.tickets)) {
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
        this.tickets.forEach(tk -> tk.setTour(this));
    }

    public void removeTicket(UUID id) {
        this.tickets.forEach(ticket -> {
            if (ticket.getId().equals(id)) {
                ticket.setTour(null);
            }
        });
    }

    public void addReservation(ReservationEntity reservation) {
        if (Objects.isNull(this.reservations)) {
            this.reservations = new HashSet<>();
        }
        this.reservations.add(reservation);
        this.reservations.forEach(tk -> tk.setTour(this));
    }

    public void removeReservation(UUID id) {
        this.reservations.forEach(reservation -> {
            if (reservation.getId().equals(id)) {
                reservation.setTour(null);
            }
        });
    }
}
