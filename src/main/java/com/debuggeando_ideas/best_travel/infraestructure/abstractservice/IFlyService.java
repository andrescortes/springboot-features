package com.debuggeando_ideas.best_travel.infraestructure.abstractservice;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {

    Set<FlyResponse> readByOriginAndDestination(String origin, String destination);
}
