package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.Group;
import bks2101.kuraga.firstProject.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;
    @GetMapping("/groups")
    ResponseEntity getAllGroups() {
        return ResponseEntity.ok(groupRepository.findAll());
    }
    @PostMapping("/groups")
    public ResponseEntity createGroup(@RequestBody Group group) {
        if (groupRepository.existsByName(group.getName())) {
            ResponseEntity.badRequest().body("Группа " + group.getName() + " уже существует");
        }
        try {
            groupRepository.save(group);
            return ResponseEntity.ok("Группа " + group.getName() + " успешна сохранена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при сохранении");
        }
    }
    @GetMapping("/groups/{name}")
    public ResponseEntity getUserByUsername(@PathVariable String name) throws UserNotFoundByUsernameException {
        if (!groupRepository.existsByName(name)){
            ResponseEntity.badRequest().body("Группа " + name + " не существует");
        }
        return ResponseEntity.ok(groupRepository.findByName(name));
    }
//    @PutMapping("/users/{name}")
//    public ResponseEntity<Optional<Group>> exchangeGroup(@RequestBody Group newGroup, @PathVariable String name) {
//        if (!groupRepository.existsByName(name)) {
//            ResponseEntity.badRequest().body("Группа " + name + " не существует");
//        }
//        return ResponseEntity.ok(groupRepository.findByName(name).map(group -> {
//            group.setName(newGroup.getName());
//            group.setCurator(newGroup.getCurator());
//            group.setMeetings(newGroup.getMeetings());
//            return groupRepository.save(group);
//        }));
//    }
    @DeleteMapping("/users/{name}")
    public ResponseEntity deleteGroup(@PathVariable String name) {
        if (!groupRepository.existsByName(name)) {
            ResponseEntity.badRequest().body("Группа " + name + " не существует");
        }
        groupRepository.deleteByName(name);
        return ResponseEntity.ok("Группа " + name + " удалена");
    }
}
