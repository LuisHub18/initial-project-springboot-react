package com.backend.backend.controller;

import com.backend.backend.model.User;
import com.backend.backend.services.UsersServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    private final UsersServices usersServices;

    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User userRequest) {
       usersServices.addUser(userRequest);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = usersServices.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/:id")
    public ResponseEntity<User> getUser(Long id) {
        Optional<User> userOptional = usersServices.getOneUserById(id);

        return userOptional.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/:id")
    public ResponseEntity<?> updateUser(Long id, @RequestBody User userRequest) {
        Optional<User> userOptional = usersServices.getOneUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            usersServices.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/:id")
    public ResponseEntity<?> deleteUser(Long id) {
        Optional<User> userOptional = usersServices.getOneUserById(id);

        if (userOptional.isPresent()) {
            usersServices.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
