package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.response.TicketResponse;
import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;


    @Override
    public TicketResponse create(TicketRequest request) {
        FlyEntity flyEntity = flyRepository.findById(request.getIdFly())
            .orElseThrow(() -> new IllegalStateException("Fly not found"));
        CustomerEntity customerEntity = customerRepository.findById(request.getIdClient())
            .orElseThrow(() -> new IllegalStateException("Client not found"));

        TicketEntity ticketEntity = TicketEntity
            .builder()
            .id(UUID.randomUUID())
            .price(flyEntity.getPrice().multiply(BigDecimal.valueOf(0.25)))
            .fly(flyEntity)
            .customer(customerEntity)
            .departureDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .purchaseDate(LocalDateTime.now())
            .build();
        TicketEntity ticketSaved = ticketRepository.save(ticketEntity);
        log.info("Ticket saved with id: {}", ticketSaved.getId());

        return entityToResponse(ticketSaved);
    }

    @Override
    public TicketResponse read(UUID id) {
        TicketEntity ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Ticket not found"));
        return entityToResponse(ticket);

    }

    @Override
    public TicketResponse update(UUID id, TicketRequest request) {
        TicketEntity ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Ticket not found"));
        FlyEntity flyEntity = flyRepository.findById(request.getIdFly())
            .orElseThrow(() -> new IllegalStateException("Fly not found"));

        ticket.setFly(flyEntity);
        ticket.setPrice(BigDecimal.valueOf(0.25));
        ticket.setDepartureDate(LocalDateTime.now());
        ticket.setArrivalDate(LocalDateTime.now());
        TicketEntity saved = ticketRepository.save(ticket);
        log.info("Ticket updated with id: {}", saved.getId());
        return entityToResponse(saved);
    }

    @Override
    public void delete(UUID uuid) {

    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        ticketResponse.setFly(flyResponse);
        return ticketResponse;
    }
}
