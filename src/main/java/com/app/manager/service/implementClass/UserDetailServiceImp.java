package com.app.manager.service.implementClass;

import com.app.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.app.manager.entity.User user = userRepository.findByUsername(s);
        if ( user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnable(), !user.isAccountExpired(),
                    !user.isCredentialExpired(), !user.isLocked(),
                    getAuthorities(user));
        }
        throw new UsernameNotFoundException(s);
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(com.app.manager.entity.User user) {
        String[] userRoles = user.getUserRoles().stream().map((role) -> role.getRole().getName()).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
