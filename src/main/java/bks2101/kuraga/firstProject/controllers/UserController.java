package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.ApplicationUser;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserDetailsServiceImpl userService;
    @GetMapping("/users")
    ResponseEntity getAllUsers() {
        return userService.getAllUser();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<ApplicationUser>> getUserById(@PathVariable Long id) throws NotFoundByIdException {
        return userService.getUserByID(id);
    }
    @GetMapping("/users/find/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) throws UserNotFoundByUsernameException {
        return userService.getUserByUsername(username);
    }
    @PostMapping("/users/{id}")
    public ResponseEntity exchangeUser(@RequestBody ApplicationUser newApplicationUser, @PathVariable Long id) throws NotFoundByIdException {
        return userService.exchangeUser(newApplicationUser, id);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws NotFoundByIdException {
        return userService.deleteUserByID(id);
    }
}
