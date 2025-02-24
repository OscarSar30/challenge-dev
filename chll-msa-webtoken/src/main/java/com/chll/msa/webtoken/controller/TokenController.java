package com.chll.msa.webtoken.controller;

import com.chll.msa.webtoken.api.TokensApi;
import com.chll.msa.webtoken.model.GetGenerateTokenResponse;
import com.chll.msa.webtoken.model.GetUserTokensResponseInner;
import com.chll.msa.webtoken.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class TokenController implements TokensApi {

    private final TokenService tokenService;

    @Override
    public Mono<ResponseEntity<GetGenerateTokenResponse>> getGenerateToken(String userId,
                                                                           final ServerWebExchange exchange) {

        return tokenService.getGenerateToken(userId)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Void>> postToken(String userId,
                                                String token,
                                                final ServerWebExchange exchange) {
        return tokenService.postToken(userId, token)
                .map(unused -> new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetUserTokensResponseInner>>> getUserTokens(String userId,
                                                                                final ServerWebExchange exchange) {
            return tokenService.getUserTokens(userId)
                    .map(responseFlux ->
                            new ResponseEntity<>(responseFlux, HttpStatus.OK));
    }
}
