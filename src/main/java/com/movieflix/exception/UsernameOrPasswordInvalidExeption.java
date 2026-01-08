package com.movieflix.exception;

public class UsernameOrPasswordInvalidExeption extends RuntimeException {
    public UsernameOrPasswordInvalidExeption(String message) {
        super(message);
    }
}
