package com.debuggeando_ideas.best_travel.domain.entities;

import com.debuggeando_ideas.best_travel.util.enums.AeroLineType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;
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
@Entity(name = "fly")
public class FlyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLng;
    private Double destinyLat;
    @Column(length = 20)
    private String originName;
    @Column(length = 20)
    private String destinyName;
    @Enumerated(EnumType.STRING)
    private AeroLineType aeroLine;
    private BigDecimal price;
    @JsonIgnore
    @OneToMany(
        mappedBy = "fly",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER, // load every representation of the ticket, not only the fly
        orphanRemoval = true //if the fly is deleted, remove all the tickets
    )
    private Set<TicketEntity> tickets;
}
