package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final UserRepository userRepository;


    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.getByEmail(registrationUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с такой почтой уже существует"), HttpStatus.BAD_REQUEST);
        }
        ApplicationUser user = userService.createNewUser(registrationUserDto);

        return userService.sendHelloMessage(user);
    }
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
    public ResponseEntity banUser(@PathVariable Long id) throws NotFoundByIdException {
        userService.banUser(userService.findUserByID(id));
        return ResponseEntity.ok("Пользователь успешно забанен");
    }

    @PostMapping("/users/{id}/setSupervisor")
    public ResponseEntity setSupervisorUser(@PathVariable Long id) throws NotFoundByIdException {
        userService.setRole(userService.findUserByID(id), Role.SUPERVISOR);
        return ResponseEntity.ok("Привелегии пользователя успешно изменены");
    }
    @PostMapping("/users/{id}/setUser")
    public ResponseEntity setUser(@PathVariable Long id) throws NotFoundByIdException {
        userService.setRole(userService.findUserByID(id), Role.USER);
        return ResponseEntity.ok("Пользователь успешно разбанен");
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) throws NotFoundByIdException {
        return userService.deleteUserByID(id);
    }
}
