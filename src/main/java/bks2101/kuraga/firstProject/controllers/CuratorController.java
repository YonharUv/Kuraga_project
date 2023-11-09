package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.models.Curator;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CuratorController {
    private CuratorRepository curatorRepository;
    @GetMapping("/curators")
    ResponseEntity AllCurators() {
        return ResponseEntity.ok(curatorRepository.findAll());
    }
    @PostMapping("/curators")
    ResponseEntity createCurator(@RequestBody Curator curator) {
        return ResponseEntity.ok(curatorRepository.save(curator));
    }
}
