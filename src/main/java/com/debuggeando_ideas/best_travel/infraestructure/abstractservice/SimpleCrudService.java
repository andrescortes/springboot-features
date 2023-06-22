package com.debuggeando_ideas.best_travel.infraestructure.abstractservice;

/**
 * @param <I> inbound entity
 * @param <O> outbound entity
 * @param <K> entity type key
 */
public interface SimpleCrudService<I, O, K> {

    O create(I request);

    O read(K id);

    void delete(K id);
}
