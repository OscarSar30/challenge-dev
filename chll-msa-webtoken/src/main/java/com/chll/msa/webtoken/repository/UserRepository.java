package com.chll.msa.webtoken.repository;

import com.chll.msa.webtoken.domain.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository <UserEntity, UUID> {

    @Query("SELECT COUNT(USR.IDENTIFICATION) > 0 FROM USERS USR " +
            "WHERE USR.IDENTIFICATION = :identification")
    Mono<Boolean> findByIdentification(@NotNull String identification);

    Mono<UserEntity> findByUserId(UUID userId);
}
