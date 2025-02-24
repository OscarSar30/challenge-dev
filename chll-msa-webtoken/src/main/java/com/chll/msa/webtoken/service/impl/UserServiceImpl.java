package com.chll.msa.webtoken.service.impl;

import com.chll.msa.webtoken.exception.UserExistsException;
import com.chll.msa.webtoken.model.PostUserRequest;
import com.chll.msa.webtoken.model.PostUserResponse;
import com.chll.msa.webtoken.repository.UserRepository;
import com.chll.msa.webtoken.service.UserService;
import com.chll.msa.webtoken.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<PostUserResponse> postUser(PostUserRequest request) {
        log.info("|-> Starts process of creating new user service");
        return userRepository.findByIdentification(request.getIdentification())
                .flatMap(existsUser -> {
                    if (Boolean.TRUE.equals(existsUser)) {
                        log.error("|-> Error. User exists in DB");
                        return Mono.error(new UserExistsException());
                    } else {
                        log.info("|-> User validated successfully. Initial process save user");
                        return userRepository.save(userMapper.postRequestClientToUserEntity(request))
                                .doOnError(e -> log.error("Error save user: {}", e.getMessage()))
                                .map(userEntity -> {
                                    log.info("|-> User created with id: {}", userEntity.getUserId());
                                    PostUserResponse response = new PostUserResponse();
                                    response.setUserId(String.valueOf(userEntity.getUserId()));
                                    return response;
                                });
                    }
                }).doOnError(throwable -> log.error(
                        "|-> Error create new user. Error detail {}", throwable.getMessage()
                ));
    }
}
