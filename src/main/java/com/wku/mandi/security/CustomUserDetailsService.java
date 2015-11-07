package com.wku.mandi.security;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.User;
import com.wku.mandi.db.Vault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Vault vault = userDao.getUserForLogin(username);
        if (vault == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return new UserRepositoryUserDetails(vault);
    }

    private final static class UserRepositoryUserDetails extends Vault implements UserDetails {

        private static final long serialVersionUID = 1L;

        private Vault vault;
        private UserRepositoryUserDetails(Vault vault) {
            this.vault = vault;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return vault.getRoles();
        }

        @Override
        public String getUsername() {
            return vault.getUserId();
        }

        @Override
        public String getPassword() {
            return vault.getPassword();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

    }

}