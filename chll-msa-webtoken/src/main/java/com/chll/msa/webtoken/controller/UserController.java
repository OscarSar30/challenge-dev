package com.chll.msa.webtoken.controller;

import com.chll.msa.webtoken.api.UsersApi;
import com.chll.msa.webtoken.model.PostUserRequest;
import com.chll.msa.webtoken.model.PostUserResponse;
import com.chll.msa.webtoken.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public Mono<ResponseEntity<PostUserResponse>> postUser(Mono<PostUserRequest> postUserRequest,
                                                           final ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the user creation method.");
        return postUserRequest
                .flatMap(userService::postUser)
                .map(postUserResponse ->
                        new ResponseEntity<>(postUserResponse, HttpStatus.CREATED));
    }

}
