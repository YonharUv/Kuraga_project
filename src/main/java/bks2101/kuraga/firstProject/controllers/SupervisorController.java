package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.RequestAddByUsername;
import bks2101.kuraga.firstProject.dto.SupervisorDto;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.models.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorService supervisorService;
    @GetMapping("/supervisor")
    ResponseEntity getAllSupervisor() {
        return supervisorService.getAllSupervisors();
    }
    @PostMapping("/supervisor")
    public ResponseEntity createSupervisor(@RequestHeader("Authorization") String authHeader, @RequestBody SupervisorDto supervisorDto)  {
        return supervisorService.createSupervisor(authHeader, supervisorDto);
    }
    @PostMapping("/supervisor/{username}/curators")
    public ResponseEntity addCurators(@PathVariable String username, RequestAddByUsername curatorsName) {
        return ResponseEntity.ok("");
    }
}

