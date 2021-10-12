package com.elnur.lab1b.service;

import com.elnur.lab1b.dao.entity.UserEntity;
import com.elnur.lab1b.dao.repository.UserRepository;
import com.elnur.lab1b.exception.BadRequestException;
import com.elnur.lab1b.exception.NotFoundException;
import com.elnur.lab1b.mapper.UserMapper;
import com.elnur.lab1b.model.CreateUserRequest;
import com.elnur.lab1b.model.UpdateUserRequest;
import com.elnur.lab1b.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public void createUser(CreateUserRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(e-> {
            throw new BadRequestException("error.already-exist", "username already exist");});
        UserEntity entity = UserMapper.INSTANCE.createUserRequestToEntity(request);
        userRepository.save(entity);
    }
    
    public UserDTO updateUser(String username, UpdateUserRequest request) {
        UserEntity entity = checkIfUserExistAndGetEntity(username);
        UserMapper.INSTANCE.updateUser(entity, request);
        userRepository.save(entity);
        return UserMapper.INSTANCE.entityToUserDto(entity);
    }
    
    public UserDTO getUser(String username) {
        UserEntity entity = checkIfUserExistAndGetEntity(username);
        return UserMapper.INSTANCE.entityToUserDto(entity);
    }
    
    public void deleteUser(String username) {
        UserEntity entity = checkIfUserExistAndGetEntity(username);
        userRepository.delete(entity);
    }
    
    private UserEntity checkIfUserExistAndGetEntity(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("error.not-found", "user not found"));
    }


    public List<UserDTO> getUsers() {
        return UserMapper.INSTANCE.entitiesToListOfDtos(userRepository.findAll());
    }
}
