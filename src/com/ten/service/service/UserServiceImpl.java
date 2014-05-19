package com.ten.service.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ten.service.dao.UserDAOMongo;
import com.ten.service.model.User;
import com.ten.service.utils.SecurityUtils;

public class UserServiceImpl implements UserDetailsService {

    private final UserDAOMongo dao;

    public UserServiceImpl(UserDAOMongo dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = dao.get(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName + " not found");
        }

        final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        final boolean credentialsNonExpired =
                (System.currentTimeMillis() - user.getLastLoginTime()) <= SecurityUtils.EXPIRATION_TIME;

        final String password = user.getCredential();

        // TODO: implement lock base on account status
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(userName, password, true,
                        true, credentialsNonExpired, true, authorities);

        return userDetails;
    }

}
