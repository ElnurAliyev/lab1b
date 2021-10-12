package com.elnur.lab1b.controller;

import com.elnur.lab1b.model.CreateUserRequest;
import com.elnur.lab1b.model.UpdateUserRequest;
import com.elnur.lab1b.model.UserDTO;
import com.elnur.lab1b.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Log4j2
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody @Valid CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{username}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("username") String username,
                                              @RequestBody @Valid UpdateUserRequest request) {
        UserDTO user = userService.updateUser(username, request);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
        UserDTO user = userService.getUser(username);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
    
}
