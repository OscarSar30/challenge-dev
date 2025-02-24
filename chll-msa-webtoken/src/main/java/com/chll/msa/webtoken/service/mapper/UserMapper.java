package com.chll.msa.webtoken.service.mapper;

import com.chll.msa.webtoken.domain.UserEntity;
import com.chll.msa.webtoken.model.PostUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserEntity postRequestClientToUserEntity(PostUserRequest request);
}
