package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.dto.RequestSupervisor;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.ApplicationUser;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.service.CuratorService;
import bks2101.kuraga.firstProject.service.SupervisorService;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final UserDetailsServiceImpl userService;
    private final SupervisorService supervisorService;
    private final CuratorService curatorService;

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
        return ResponseEntity.ok(user);
    }

    @PostMapping("/curator/create")
    ResponseEntity<String> createCuratorAdmin(@RequestHeader("Authorization") String authHeader, @RequestBody CuratorDto curator) throws UserAlreadyExistsException {
        return curatorService.createCuratorAdmin(curator);
    }

    @GetMapping("/curators")
    ResponseEntity<List<CuratorDto>> AllCurators() {
        return curatorService.getAllCurators();
    }

    @DeleteMapping("/curator/delete/{email}")
    public ResponseEntity deleteCurator(@PathVariable String email) throws UserNotFoundByUsernameException {
        return curatorService.deleteCurator(email);
    }

//    @DeleteMapping("/curator/delete/{id}")
//    public ResponseEntity deleteUser(@PathVariable Long id) throws NotFoundByIdException {
//        return userService.deleteUserByID(id);
//    }



    @PostMapping("/supervisor/create")
    public ResponseEntity createSupervisorAdmin(@RequestBody RequestSupervisor supervisor) throws UserAlreadyExistsException {
        return supervisorService.createSupervisorAdmin(supervisor);
    }

    @GetMapping("/supervisors")
    ResponseEntity AllSupervisors() {
        return supervisorService.getAllSupervisors();
    }

    @DeleteMapping("/supervisor/delete/{email}")
    public ResponseEntity deleteSupervisor(@PathVariable String email) throws UserNotFoundByUsernameException {
        return supervisorService.deleteSupervisor(email);
    }

//    @GetMapping("/curators/{id}")
//    public ResponseEntity<Optional<ApplicationUser>> getUserById(@PathVariable Long id) throws NotFoundByIdException {
//        return userService.getUserByID(id);
//    }
//    @GetMapping("/curators/find/{username}")
//    public ResponseEntity getUserByUsername(@PathVariable String username) throws UserNotFoundByUsernameException {
//        return userService.getUserByUsername(username);
//    }
//    @PostMapping("/curators/{id}")
//    public ResponseEntity exchangeUser(@RequestBody ApplicationUser newApplicationUser, @PathVariable Long id) throws NotFoundByIdException {
//        return userService.exchangeUser(newApplicationUser, id);
//    }
//    TODO:
//    - set role
//    - ban
//    - delete user (увольнение)
//    - create user (регистрация)
//    - delete/create all content:
//            - Group
//            - Meetings(for all type of user)
//            - Students
}
