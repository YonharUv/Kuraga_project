package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
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
public class AdminUsersController {
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
    @PostMapping("/users/{id}/ban")
    public ResponseEntity exchangeUser(@PathVariable Long id) throws NotFoundByIdException {
        userService.banUser(userService.findUserByID(id));
        return ResponseEntity.ok("Пользователь успешно забанен");
    }

//    @PostMapping("/users/{id}/setSupervisor")
//    public ResponseEntity exchangeUser(@RequestBody ApplicationUser newApplicationUser, @PathVariable Long id) throws NotFoundByIdException {
//        return userService.exchangeUser(newApplicationUser, id);
//    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws NotFoundByIdException {
        return userService.deleteUserByID(id);
    }
}
