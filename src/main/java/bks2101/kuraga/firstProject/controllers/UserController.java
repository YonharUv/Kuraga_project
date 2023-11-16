package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.ApplicationUser;
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
//    @PostMapping("/users")
//    public ResponseEntity createUser(@RequestBody ApplicationUser applicationUser) throws UserAlreadyExistsException {
//        if (userRepository.existsByUsername(applicationUser.getUsername()) || userRepository.existsByEmail(applicationUser.getEmail())) {
//            throw new UserAlreadyExistsException(applicationUser.getEmail(), applicationUser.getUsername());
//        }
//        try {
//            applicationUser.setRoles(Set<Role>);
//            userRepository.save(applicationUser);
//            return ResponseEntity.ok("Пользователь успешно сохранен");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
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
//    @PutMapping("/users/{id}")
//    public ResponseEntity<Optional<ApplicationUser>> exchangeUser(@RequestBody ApplicationUser newApplicationUser, @PathVariable Long id) throws UserNotFoundByIdException {
//        if (!userRepository.existsById(id)) {
//            throw new UserNotFoundByIdException(id);
//        }
//        return ResponseEntity.ok(userRepository.findById(id).map(applicationUser -> {
//            applicationUser.setUsername(newApplicationUser.getUsername());
//            applicationUser.setEmail(newApplicationUser.getEmail());
//            applicationUser.setPassword(newApplicationUser.getPassword());
//            applicationUser.setRole(UserRole.USER);
//            return userRepository.save(applicationUser);
//        }));
//    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws UserNotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(format("Пользователь с id = %d успешно удален", id));
    }
}
