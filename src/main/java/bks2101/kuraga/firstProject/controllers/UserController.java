package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.User;
import bks2101.kuraga.firstProject.models.UserRole;
import bks2101.kuraga.firstProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/users")
    ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail(), user.getUsername());
        }
        try {
            user.setRole(UserRole.USER);
            userRepository.save(user);
            return ResponseEntity.ok("Пользователь успешно сохранен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) throws UserNotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        return ResponseEntity.ok(userRepository.findById(id));
    }
    @GetMapping("/users/find/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) throws UserNotFoundByUsernameException {
        if (!userRepository.existsByUsername(username)){
            throw new UserNotFoundByUsernameException(username);
        }
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<Optional<User>> exchangeUser(@RequestBody User newUser, @PathVariable Long id) throws UserNotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        return ResponseEntity.ok(userRepository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.setRole(UserRole.USER);
            return userRepository.save(user);
        }));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws UserNotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(format("Пользователь с id = %d успешно удален", id));
    }
}
