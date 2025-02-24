package com.chll.msa.webtoken.service.impl;

import com.chll.msa.webtoken.domain.TokenEntity;
import com.chll.msa.webtoken.exception.TokenNotFoundException;
import com.chll.msa.webtoken.exception.UserException;
import com.chll.msa.webtoken.model.GetGenerateTokenResponse;
import com.chll.msa.webtoken.model.GetUserTokensResponseInner;
import com.chll.msa.webtoken.repository.TokenRepository;
import com.chll.msa.webtoken.repository.UserRepository;
import com.chll.msa.webtoken.service.TokenService;
import com.chll.msa.webtoken.service.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.chll.msa.webtoken.util.Utils.generateToken;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    @Override
    public Mono<GetGenerateTokenResponse> getGenerateToken(String userId) {
        log.info("|-> Starts process of creating new token service");
        return userRepository.findByUserId(UUID.fromString(userId))
                .switchIfEmpty(Mono.error(new UserException(userId)))
                .flatMap(userEntity ->
                        tokenRepository.save(toTokenEntity(userId))
                                .flatMap(tokenEntity -> {
                                    log.info("|-> Token created with id: {}", tokenEntity.getTokenId());
                                    return Mono.just(tokenMapper.toGenerateTokenResponse(tokenEntity));
                                })
                                .doOnError(throwable -> log.error(
                                        "|-> Error creating token. Error detail {}", throwable.getMessage())
                                ))
                .doOnError(throwable -> log.error(
                        "|-> Error obtaining user information. Error detail {}", throwable.getMessage()
                ));
    }

    @Override
    public Mono<Void> postToken(String userId, String token) {
        log.info("|-> Starts process of validate token service");
        return userRepository.findByUserId(UUID.fromString(userId))
                .switchIfEmpty(Mono.error(new UserException(userId)))
                .flatMap(userEntity ->
                        tokenRepository.findByToken(token)
                                .switchIfEmpty(Mono.error(new TokenNotFoundException(token)))
                                .flatMap(tokenEntity -> {
                                    tokenEntity.setUsed(true);
                                    tokenEntity.setUserId(userEntity.getUserId());
                                    return tokenRepository.save(tokenEntity)
                                            .doOnError(throwable -> log.error("|-> Error updating token. Error detail {}", throwable.getMessage()))
                                            .then(Mono.empty());
                                })
                )
                .doOnError(throwable ->
                        log.error("|-> Error validating token. Error detail {}", throwable.getMessage()))
                .doOnTerminate(() ->
                        log.info("|-> Token validation process completed"))
                .then();
    }

    @Override
    public Mono<Flux<GetUserTokensResponseInner>> getUserTokens(String userId) {
        log.info("|-> Starts process of data tokens service");

        return userRepository.findByUserId(UUID.fromString(userId))
                .switchIfEmpty(Mono.error(new UserException(userId)))
                .flatMap(userEntity ->
                        Mono.just(tokenRepository.findAll()
                                .map(tokenEntity -> tokenMapper.getResponseAll(userEntity, tokenEntity))
                                .doOnError(throwable ->
                                        log.error("|-> Error obtaining tokens. Error detail {}", throwable.getMessage()))
                        )
                )
                .doOnError(throwable ->
                        log.error("|-> Error obtaining user information. Error detail {}", throwable.getMessage()));
    }


    private TokenEntity toTokenEntity (String userId){
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime expirationTime = currentTime.plusSeconds(60);
            return TokenEntity.builder()
                    .token(generateToken())
                    .generationDate(currentTime)
                    .expirationDate(expirationTime)
                    .isUsed(false)
                    .userId(UUID.fromString(userId))
                    .build();
        }
    }
