package com.chll.msa.webtoken.service.mapper;

import com.chll.msa.webtoken.domain.TokenEntity;
import com.chll.msa.webtoken.domain.UserEntity;
import com.chll.msa.webtoken.model.GetGenerateTokenResponse;
import com.chll.msa.webtoken.model.GetUserTokensResponseInner;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TokenMapper {

    @Mapping(source = "generationDate", target = "generationDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(source = "expirationDate", target = "expirationDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    GetGenerateTokenResponse toGenerateTokenResponse(TokenEntity tokenEntity);

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    @Mapping(target = "user.identification", source = "userEntity.identification")
    @Mapping(target = "user.fullName", source = "userEntity.fullName")
    @Mapping(target = "user.status", source = "userEntity.status")
    @Mapping(target = "user.email", source = "userEntity.email")
    @Mapping(source = "tokenEntity.generationDate", target = "generationDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(source = "tokenEntity.expirationDate", target = "expirationDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    GetUserTokensResponseInner getResponseAll(UserEntity userEntity,
                                              TokenEntity tokenEntity);
}
