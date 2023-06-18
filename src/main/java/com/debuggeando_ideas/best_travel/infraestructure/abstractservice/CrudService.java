package com.debuggeando_ideas.best_travel.infraestructure.abstractservice;

/**
 * @param <I> entity request
 * @param <O> entity response
 * @param <K> entity type id
 */
public interface CrudService<I, O, K> {

    O create(I request);

    O read(K id);

    O update(K id, I request);

    void delete(K id);
}
