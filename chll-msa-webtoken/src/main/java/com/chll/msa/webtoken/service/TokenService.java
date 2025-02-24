package com.chll.msa.webtoken.service;

import com.chll.msa.webtoken.model.GetGenerateTokenResponse;
import com.chll.msa.webtoken.model.GetUserTokensResponseInner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TokenService {
    Mono<GetGenerateTokenResponse> getGenerateToken(String userId);

    Mono<Void> postToken(String userId, String token);

    Mono<Flux<GetUserTokensResponseInner>> getUserTokens(String userId);
}
