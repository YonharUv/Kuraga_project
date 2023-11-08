package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.UserNotFoundException;
import bks2101.kuraga.firstProject.models.User;
import bks2101.kuraga.firstProject.models.UserRole;
import bks2101.kuraga.firstProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;
    @GetMapping("/users")
    ResponseEntity all() {
        return ResponseEntity.ok(repository.findAll());
    }
    @PostMapping("/users")
    public ResponseEntity newUser(@Validated @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Произошла ошибка: неверные данные");
        } else {
            try {
                user.setRole(UserRole.USER);
                repository.save(user);
                return ResponseEntity.ok("Пользователь успешно сохранен");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Произошла ошибка");
            }
        }
    }
    @GetMapping("/users/{id}")
    public ResponseEntity one(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity DeleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok(format("Пользователь с id = %d успешно удален", id));
    }
}
