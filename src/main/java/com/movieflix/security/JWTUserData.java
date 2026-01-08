package com.movieflix.security;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long id,
        String name,
        String email
) {
}
