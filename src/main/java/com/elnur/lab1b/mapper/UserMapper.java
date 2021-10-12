package com.elnur.lab1b.mapper;

import com.elnur.lab1b.dao.entity.UserEntity;
import com.elnur.lab1b.model.CreateUserRequest;
import com.elnur.lab1b.model.UpdateUserRequest;
import com.elnur.lab1b.model.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserEntity createUserRequestToEntity(CreateUserRequest request);

    @BeanMapping(ignoreByDefault = true,nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(target = "entity.password", source = "request.password")
    @Mapping(target = "entity.name", source = "request.name")
    @Mapping(target = "entity.surname", source = "request.surname")
    @Mapping(target = "entity.dateOfBirth", source = "request.dateOfBirth")
    void updateUser(@MappingTarget UserEntity entity, UpdateUserRequest request);
    
    UserDTO entityToUserDto(UserEntity entity);
    
    List<UserDTO> entitiesToListOfDtos(List<UserEntity> entities); 
}
