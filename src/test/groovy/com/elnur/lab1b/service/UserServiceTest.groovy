package com.elnur.lab1b.service

import com.elnur.lab1b.dao.entity.UserEntity
import com.elnur.lab1b.dao.repository.UserRepository
import com.elnur.lab1b.exception.BadRequestException
import com.elnur.lab1b.model.CreateUserRequest
import com.elnur.lab1b.model.UpdateUserRequest
import com.elnur.lab1b.model.UserDTO
import spock.lang.Specification

import java.time.LocalDate

class UserServiceTest extends Specification {
    
    UserService userService
    UserRepository userRepository
    

    void setup() {
        userRepository = Mock()
        userService = new UserService(userRepository)
    }
    
    def "CreateUser"() {
        given:
        CreateUserRequest request = CreateUserRequest.builder()
        .username("12345")
        .password("abcd")
        .build();
        
        when:
        userService.createUser(request)

        then:
        1 * userRepository.findByUsername(request.getUsername())>>Optional.empty()
        1 * userRepository.save(!null as UserEntity)
    }

    def "fail CreateUser"() {
        given:
        CreateUserRequest request = CreateUserRequest.builder()
                .username("12345")
                .password("abcd")
                .build();

        when:
        userService.createUser(request)

        then:
        1 * userRepository.findByUsername(request.getUsername())>>Optional.of(new UserEntity())
        thrown(BadRequestException.class)
    }

    def "UpdateUser"() {
        given:
        String username = "elnur123"
        UpdateUserRequest request  = UpdateUserRequest.builder()
        .name("elnur")
        .surname("aliyev")
        .dateOfBirth(LocalDate.of(1997, 01, 27)).build()
        
        UserEntity entity = UserEntity.builder().username("elnur123").password("lolol").build();
        
        when:
        UserDTO result = userService.updateUser(username, request)
        
        then:
        1* userRepository.findByUsername(username)>>Optional.of(entity)
        1 * userRepository.save(!null as UserEntity) >> {entity.setName(request.getName()); return entity}
        
        assert result.getName() == request.getName()
    }

    def "GetUser"() {
        given:
        String username = "elnur123"

        UserEntity entity = UserEntity.builder().username("elnur123")
                .password("lolol")
                .name("elnur").build();

        when:
        UserDTO result = userService.getUser(username)


        then:
        1 * userRepository.findByUsername(username)>>Optional.of(entity)
        assert result.getName() == entity.getName()
        assert result.getUsername() == entity.getUsername()
    }

    def "DeleteUser"() {
        given:
        String username = "elnur123"
        
        UserEntity entity = UserEntity.builder().username("elnur123").build()
        
        when:
        userService.deleteUser(username)
        
        then:
        1 * userRepository.findByUsername(username)>>Optional.of(entity)
        1 * userRepository.delete(entity)
        
    }

    def "getUsers"() {
        given:

        List<UserEntity> entities = new ArrayList<>()
        entities.add(UserEntity.builder().username("elnur123").build())
        entities.add(UserEntity.builder().username("elnur567").build())

        when:
        List<UserDTO> result = userService.getUsers()

        then:
        1 * userRepository.findAll()>>entities
        assert result.size()==2

    }
}
