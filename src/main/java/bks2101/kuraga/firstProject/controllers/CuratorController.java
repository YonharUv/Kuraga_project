package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.service.CuratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CuratorController {
    private final CuratorService curatorService;
    @GetMapping("/curators")
    ResponseEntity AllCurators() {
        return curatorService.getAllCurators();
    }
    @PostMapping("/curators")
    ResponseEntity createCurator(@RequestHeader("Authorization") String authHeader, @RequestBody CuratorDto curator) throws UserAlreadyExistsException {
        return curatorService.createCurator(authHeader, curator);
    }
}
