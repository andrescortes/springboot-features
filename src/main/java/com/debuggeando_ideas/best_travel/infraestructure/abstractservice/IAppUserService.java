package com.debuggeando_ideas.best_travel.infraestructure.abstractservice;

import com.debuggeando_ideas.best_travel.domain.entities.documents.AppUserDocument;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAppUserService {

    Map<String, Boolean> enabled(String username);

    Map<String, Set<String>> addRole(String username, String role);

    Map<String, Set<String>> removeRole(String username, String role);

    List<AppUserDocument> getUsers();
}
