package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.dto.RequestAddCurators;
import bks2101.kuraga.firstProject.dto.RequestSupervisor;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.service.CuratorService;
import bks2101.kuraga.firstProject.service.SupervisorService;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminSupervisorController {

    @Autowired
    private final UserDetailsServiceImpl userService;
    private final SupervisorService supervisorService;
    private final UserRepository userRepository;
    private final SupervisorRepository supervisorRepository;

    @PostMapping("/supervisor/create")
    public ResponseEntity createSupervisorAdmin(@RequestBody RequestSupervisor supervisor) throws UserAlreadyExistsException {
        if (supervisorRepository.findByEmail(supervisor.getEmail()) != null) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Супервизор с такой почтой уже существует"), HttpStatus.BAD_REQUEST);
        }
        userService.setRole(userRepository.findByEmail(supervisor.getEmail()), Role.SUPERVISOR);
        return supervisorService.createSupervisorAdmin(supervisor);
    }
    @GetMapping("/supervisors")
    ResponseEntity AllSupervisors() {
        return supervisorService.getAllSupervisors();
    }

    @GetMapping("/supervisor/{email}")
    public ResponseEntity getSupervisorByEmail(@PathVariable String email) throws UserNotFoundByUsernameException {
        return supervisorService.getSupervisorByEmail(email);
    }
    @PostMapping("/supervisor/{email}/curators")
    public ResponseEntity addCuratorsByEmail(@PathVariable String email, @RequestBody RequestAddCurators curators_emails) {
        return supervisorService.addCurators(email, curators_emails);
    }
    @PostMapping("/supervisor/update/{email}")
    public ResponseEntity updateSupervisor(@PathVariable String email, @RequestBody RequestSupervisor supervisor) throws UserNotFoundByUsernameException, UserAlreadyExistsException {
        return supervisorService.updateSupervisor(email, supervisor);
    }

    @DeleteMapping("/supervisor/delete/{email}")
    public ResponseEntity deleteSupervisor(@PathVariable String email) throws UserNotFoundByUsernameException {
        return supervisorService.deleteSupervisor(email);
    }

}
