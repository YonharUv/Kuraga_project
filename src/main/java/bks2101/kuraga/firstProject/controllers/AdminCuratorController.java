package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.dto.RequestAddCurators;
import bks2101.kuraga.firstProject.dto.RequestSupervisor;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
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
public class AdminCuratorController {

    @Autowired
    private final CuratorService curatorService;


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

//    TODO:
//    done set role
//    done ban
//    done delete user (увольнение)
//    done create user (регистрация)
//    done check users
//    - delete/create all content:
//            - Meetings(for all type of user)
//            - Students
}
