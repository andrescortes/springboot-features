package com.debuggeando_ideas.best_travel.domain.repositories.jpa;

import com.debuggeando_ideas.best_travel.domain.entities.jpa.FlyEntity;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {

    @Query("SELECT f FROM fly f WHERE f.price < :priceFly")
    Set<FlyEntity> selectLessPrice(@Param(value = "priceFly") BigDecimal price);

    @Query("SELECT f FROM fly f WHERE f.price BETWEEN :minPrice AND :maxPrice")
    Set<FlyEntity> selectBetweenPrice(
        @Param(value = "minPrice") BigDecimal min,
        @Param(value = "maxPrice") BigDecimal max);

    @Query("SELECT f FROM fly f WHERE f.originName = :origin AND f.destinyName = :destiny")
    Set<FlyEntity> selectOriginAndDestiny(String origin, String destiny);


    @Query("SELECT f FROM fly f join fetch f.tickets t where t.id = :ticketId")
    Optional<FlyEntity> findByTicketId(UUID ticketId);
}
