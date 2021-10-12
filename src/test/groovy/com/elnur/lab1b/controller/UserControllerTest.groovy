package com.elnur.lab1b.controller


import com.elnur.lab1b.exception.ErrorHandler
import com.elnur.lab1b.model.CreateUserRequest
import com.elnur.lab1b.model.UpdateUserRequest
import com.elnur.lab1b.model.UserDTO
import com.elnur.lab1b.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.text.SimpleDateFormat
import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class UserControllerTest extends Specification {
    UserService userService
    UserController userController
    MockMvc mockMvc
    ObjectMapper mapper
    def setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(mapper)

        userService = Mock(UserService)
        userController = new UserController(userService)
        
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ErrorHandler())
                .setMessageConverters(mappingJackson2HttpMessageConverter).build()

    }
    
    def "createUser"() {

        given:
        CreateUserRequest request = CreateUserRequest.builder()
                .username("12345")
                .password("abcd")
                .build();
        String requestInJson = mapper.writeValueAsString(request)
        println requestInJson

        when:
        def response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestInJson)).andReturn().response

        then:
        1 * userService.createUser(!null as CreateUserRequest)
        assert response.status.intValue()==200
    }

    def "UpdateUser"() {
        String username = "elnur123"
        UpdateUserRequest request  = UpdateUserRequest.builder()
                .name("elnur")
                .surname("aliyev")
                .dateOfBirth(LocalDate.of(1997, 01, 27)).build()
        String requestInJson = mapper.writeValueAsString(request)

        when:
        def response = mockMvc.perform(patch("/user/{username}", username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestInJson)).andReturn().response

        then:
        1 * userService.updateUser(username, !null as UpdateUserRequest)
        assert response.status.intValue()==200
    }

    def "GetUser"() {
        given:
        String username = "elnur123"
        UserDTO result = UserDTO.builder()
        .username("elnur123")
        .name("elnur")
        .surname("aliyev").build()

        when:
        def response = mockMvc.perform(get("/user/{username}", username)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * userService.getUser(username)>> result
        assert response.status.intValue()==200
    }

    def "DeleteUser"() {
        given:
        String username = "elnur123"
        
        when:
        def response = mockMvc.perform(delete("/user/{username}", username)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * userService.deleteUser(username,)
        assert response.status.intValue()==200
    }

    def "getUsers"() {
        given:
        List<UserDTO> result = new ArrayList<>()
        result.add(UserDTO.builder().username("elnur123").build())
        result.add(UserDTO.builder().username("elnur567").build())
        
        when:
        def response = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * userService.getUsers()
        assert response.status.intValue()==200 
    }
    
    
}
