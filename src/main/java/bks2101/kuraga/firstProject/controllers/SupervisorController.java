package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.RequestAddCurators;
import bks2101.kuraga.firstProject.dto.RequestSupervisor;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorService supervisorService;

    @GetMapping("/supervisor/{email}")
    public ResponseEntity getSupervisorByEmail(@PathVariable String email) throws UserNotFoundByUsernameException {
        return supervisorService.getSupervisorByEmail(email);
    }
    @PostMapping("/supervisor/{email}")
    public ResponseEntity updateSupervisor(@PathVariable String email, @RequestBody RequestSupervisor supervisor) throws UserNotFoundByUsernameException, UserAlreadyExistsException {
        return supervisorService.updateSupervisor(email, supervisor);
    }
    @PostMapping("/supervisor/{email}/curators")
    public ResponseEntity addCurators(@PathVariable String email, @RequestBody RequestAddCurators curators_emails) {
        return supervisorService.addCurators(email, curators_emails);
    }

}

