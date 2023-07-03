package com.debuggeando_ideas.best_travel.infraestructure.service;

import com.debuggeando_ideas.best_travel.domain.app.Constant;
import com.debuggeando_ideas.best_travel.domain.entities.documents.AppUserDocument;
import com.debuggeando_ideas.best_travel.domain.repositories.mongo.AppUserRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IAppUserService;
import com.debuggeando_ideas.best_travel.util.exceptions.UsernameDocumentNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements IAppUserService, UserDetailsService {

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
            .orElseThrow(() -> new UsernameDocumentNotFoundException(Constant.USERNAME_NOT_FOUND));
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

    @Override
    public List<AppUserDocument> getUsers() {
        return appUserRepository.findAll();
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search may possibly
     * be case sensitive, or case insensitive depending on how the implementation instance is
     * configured. In this case, the <code>UserDetails</code> object that comes back may have a
     * username that is of a different case than what was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDocument appUserDocument = getByUsername(username);
        return mapUserToUserDetails(appUserDocument);
    }

    private UserDetails mapUserToUserDetails(AppUserDocument appUserDocument) {
        Set<SimpleGrantedAuthority> grantedAuthorities = appUserDocument.getRole()
            .getGrantedAuthorities()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());

        return new User(
            appUserDocument.getUsername(),
            appUserDocument.getPassword(),
            appUserDocument.isEnabled(),
            true,
            true,
            true,
            grantedAuthorities
        );
    }
}
