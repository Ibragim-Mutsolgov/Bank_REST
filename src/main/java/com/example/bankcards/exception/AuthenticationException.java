package com.example.bankcards.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationException extends UsernameNotFoundException {

    public AuthenticationException(String msg) {
        super(msg);
    }
}
