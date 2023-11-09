package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.SupervisorDto;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByIdException;
import bks2101.kuraga.firstProject.models.Curator;
import bks2101.kuraga.firstProject.models.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@RestController
public class SupervisorController {
    @Autowired
    private SupervisorRepository supervisorRepository;
    private CuratorRepository curatorRepository;
    @GetMapping("/supervisor")
    ResponseEntity getAllSupervisor() {
        return ResponseEntity.ok(supervisorRepository.findAll());
    }
    @PostMapping("/supervisor")
    public ResponseEntity createSupervisor(@RequestBody SupervisorDto supervisorDto)  {
        var supervisor = new Supervisor();
        var curatorsId = supervisorDto.getCuratorsId();
        for (long i : curatorsId) {
            supervisor.addCurator(curatorRepository.getById(i));
        }
        supervisor.setFirst_name(supervisorDto.getFirst_name());
        supervisor.setLast_name(supervisorDto.getLast_name());
        supervisor.setPersonal_data(supervisorDto.getPersonal_data());
        supervisorRepository.save(supervisor);
        return ResponseEntity.ok("Пользователь успешно сохранен");
    }
    @GetMapping("/supervisor/{id}")
    public ResponseEntity getSupervisorById(@PathVariable Long id) throws UserNotFoundByIdException {
        if (!supervisorRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        return ResponseEntity.ok(supervisorRepository.findById(id));
    }

    @PutMapping("/supervisor/{id}")
    public ResponseEntity<Optional<Supervisor>> exchangeSuperVisor(@RequestBody Supervisor newSup, @PathVariable Long id) throws UserNotFoundByIdException {
        if (!supervisorRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        return ResponseEntity.ok(supervisorRepository.findById(id).map(supervisor -> {
            supervisor.setFirst_name(newSup.getFirst_name());
            supervisor.setLast_name(newSup.getLast_name());
            supervisor.setPersonal_data(newSup.getPersonal_data());
            supervisor.setCurators(newSup.getCurators());
            return supervisorRepository.save(supervisor);
        }));
    }
    @DeleteMapping("/supervisor/{id}")
    public ResponseEntity deleteSupervisor(@PathVariable Long id) throws UserNotFoundByIdException {
        if (!supervisorRepository.existsById(id)) {
            throw new UserNotFoundByIdException(id);
        }
        supervisorRepository.deleteById(id);
        return ResponseEntity.ok(format("Пользователь с id = %d успешно удален", id));
    }
}

