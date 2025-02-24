package com.chll.msa.webtoken.service;

import com.chll.msa.webtoken.model.PostUserRequest;
import com.chll.msa.webtoken.model.PostUserResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<PostUserResponse> postUser(PostUserRequest postUserRequest);
}
