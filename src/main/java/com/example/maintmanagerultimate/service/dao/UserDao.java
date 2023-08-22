package com.example.maintmanagerultimate.service.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {

    //TODO such thing should be called from DB using service and repository classes
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User(
                    "admin@gmail.com",
                    "pass",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User(
                    "user@gmail.com",
                    "pass",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            ));

    public UserDetails loadUserByEmail(String userEmail) {
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(userEmail))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User %s was not found".formatted(userEmail)));
    }
}
