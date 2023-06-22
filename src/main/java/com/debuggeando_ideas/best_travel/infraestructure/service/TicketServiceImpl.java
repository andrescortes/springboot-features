package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.response.TicketResponse;
import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITicketService;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketServiceImpl implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;


    @Override
    public TicketResponse create(TicketRequest request) {
        FlyEntity flyEntity = getFlyEntity(request);
        CustomerEntity customerEntity = getCustomerEntity(request);

        TicketEntity ticketEntity = TicketEntity
            .builder()
            .id(UUID.randomUUID())
            .price(flyEntity.getPrice()
                .add(flyEntity.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE)))
            .fly(flyEntity)
            .customer(customerEntity)
            .departureDate(BestTravelUtil.getRandomSoon())
            .arrivalDate(BestTravelUtil.getRandomLater())
            .purchaseDate(LocalDateTime.now())
            .build();
        TicketEntity ticketSaved = saveTicket(ticketEntity);
        log.info("Ticket saved with id: {}", ticketSaved.getId());

        return entityToResponse(ticketSaved);
    }

    @Override
    public TicketResponse read(UUID id) {
        TicketEntity ticket = getTicketEntity(id);
        return entityToResponse(ticket);

    }

    @Override
    public TicketResponse update(UUID id, TicketRequest request) {
        TicketEntity ticket = getTicketEntity(id);
        FlyEntity flyEntity = getFlyEntity(request);

        ticket.setFly(flyEntity);
        ticket.setPrice(flyEntity.getPrice()
            .add(flyEntity.getPrice().multiply(Constant.CHARGE_PRICE_PERCENTAGE)));
        ticket.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticket.setArrivalDate(BestTravelUtil.getRandomLater());
        TicketEntity saved = saveTicket(ticket);
        log.info("Ticket updated with id: {}", saved.getId());
        return entityToResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        TicketEntity ticket = getTicketEntity(id);
        ticketRepository.delete(ticket);
    }

    @Override
    public BigDecimal findPriceById(Long flyId) {
        FlyEntity flyEntity = getFlyById(flyId);
        return flyEntity.getPrice()
            .add(flyEntity
                .getPrice()
                .multiply(Constant.CHARGE_PRICE_PERCENTAGE)
            );
    }

    private CustomerEntity getCustomerEntity(TicketRequest request) {
        return customerRepository.findById(request.getIdClient())
            .orElseThrow(() -> new IllegalStateException(Constant.ERROR_CUSTOMER_NOT_FOUND));
    }

    private TicketEntity saveTicket(TicketEntity ticketEntity) {
        return ticketRepository.save(ticketEntity);
    }

    private FlyEntity getFlyEntity(TicketRequest request) {
        return flyRepository.findById(request.getIdFly())
            .orElseThrow(() -> new IllegalStateException(Constant.ERROR_FLY_NOT_FOUND));
    }

    private TicketEntity getTicketEntity(UUID id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException(Constant.ERROR_TICKET_NOT_FOUND));
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        ticketResponse.setFly(flyResponse);
        return ticketResponse;
    }

    private FlyEntity getFlyById(Long flyId) {
        return flyRepository.findById(flyId)
            .orElseThrow(() -> new IllegalStateException(Constant.ERROR_FLY_NOT_FOUND));
    }
}
