package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.documents.AppUserDocument;
import com.debuggeando_ideas.best_travel.domain.repositories.mongo.AppUserRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IModifyUserService;
import com.debuggeando_ideas.best_travel.util.exceptions.UsernameNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ModifyUserServiceImpl implements IModifyUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enabled(String username) {
        AppUserDocument appUserDocument = getByUsername(username);
        appUserDocument.setEnabled(!appUserDocument.isEnabled());
        AppUserDocument userDocument = toSaveDocument(appUserDocument);
        return Collections.singletonMap(userDocument.getUsername(), appUserDocument.isEnabled());
    }

    private AppUserDocument toSaveDocument(AppUserDocument appUserDocument) {
        return appUserRepository.save(appUserDocument);
    }

    private AppUserDocument getByUsername(String username) {
        return appUserRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(Constant.USERNAME_NOT_FOUND));
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        AppUserDocument appUserDocument = getByUsername(username);
        appUserDocument.getRole().getGrantedAuthorities().add(role);
        AppUserDocument saveDocument = toSaveDocument(appUserDocument);
        Set<String> grantedAuthorities = saveDocument.getRole().getGrantedAuthorities();
        log.info("Granted authorities: {}", grantedAuthorities);
        return Collections.singletonMap(saveDocument.getUsername(), grantedAuthorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        AppUserDocument appUserDocument = getByUsername(username);
        appUserDocument.getRole().getGrantedAuthorities().remove(role);
        AppUserDocument saveDocument = toSaveDocument(appUserDocument);
        Set<String> grantedAuthorities = saveDocument.getRole().getGrantedAuthorities();
        log.info("Granted authorities: {}", grantedAuthorities);
        return Collections.singletonMap(saveDocument.getUsername(), grantedAuthorities);
    }
    @Transactional(readOnly = true)
    public void loadUserByUsername(String username){
        AppUserDocument appUserDocument = getByUsername(username);
    }

    @Override
    public List<AppUserDocument> getUsers() {
        return appUserRepository.findAll();
    }
}
