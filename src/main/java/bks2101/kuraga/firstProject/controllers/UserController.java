package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.UserNotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    @GetMapping("/users")
    ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
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
