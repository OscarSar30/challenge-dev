package com.chll.msa.webtoken.repository;

import com.chll.msa.webtoken.domain.TokenEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TokenRepository extends ReactiveCrudRepository <TokenEntity, UUID> {
    Mono<TokenEntity> findByToken(String token);

    Flux<TokenEntity> findByUserId(UUID userId);
}
