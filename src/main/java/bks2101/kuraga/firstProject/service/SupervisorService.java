package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.RequestAddCurators;
import bks2101.kuraga.firstProject.dto.RequestSupervisor;
import bks2101.kuraga.firstProject.dto.SupervisorDto;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.entitys.Curator;
import bks2101.kuraga.firstProject.entitys.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import bks2101.kuraga.firstProject.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final CuratorRepository curatorRepository;
    private final UserRepository userRepository;

    public ResponseEntity getAllSupervisors() {
        var listSupervisors = supervisorRepository.findAll();
        var supervisorDtoList = listSupervisors.stream()
                .map(MappingUtil::mapToSupervisorDto)
                .toList();
        return ResponseEntity.ok(supervisorDtoList);
    }
//    public ResponseEntity createSupervisor(String authHeader, RequestSupervisor reqSupervisor) {
//        String jwtToken = authHeader.substring(7);
//        String email = jwtTokenUtils.getUsername(jwtToken);
//        Supervisor supervisor = new Supervisor();
//        supervisor.setFirst_name(reqSupervisor.getFirst_name());
//        supervisor.setLast_name(reqSupervisor.getLast_name());
//        supervisor.setPersonal_data(reqSupervisor.getPersonal_data());
//        supervisor.setEmail(email);
//        var curatorsEmailSet = reqSupervisor.getCurators_email();
//        var curatorsSet = curatorsEmailSet.stream()
//                .filter(curatorRepository::existsByEmail)
//                .map(curatorRepository::getByEmail)
//                .collect(Collectors.toSet());
//        supervisor.setCurators(curatorsSet);
//        return ResponseEntity.ok(supervisorRepository.save(supervisor));
//    }

    public ResponseEntity createSupervisorAdmin(RequestSupervisor reqSupervisor) throws UserAlreadyExistsException {
        Supervisor supervisor = new Supervisor();
        supervisor.setFirst_name(reqSupervisor.getFirst_name());
        supervisor.setLast_name(reqSupervisor.getLast_name());
        supervisor.setPersonal_data(reqSupervisor.getPersonal_data());
        supervisor.setEmail(reqSupervisor.getEmail());
        ApplicationUser supervisorUser = userRepository.findByEmail(reqSupervisor.getEmail());
        supervisor.setUsername(supervisorUser.getUsername());

        return ResponseEntity.ok(supervisorRepository.save(supervisor));
    }
    public ResponseEntity addCurators(String email, RequestAddCurators curators_email) {
        Supervisor newSupervisor = supervisorRepository.findByEmail(email);
        List<String> curatorsEmail = curators_email.getCurators_email();
        List<Curator> curators = curatorsEmail.stream()
                .filter(curatorRepository::existsByEmail)
                .map(curatorRepository::getByEmail)
                .collect(Collectors.toList());
        for (Curator curator: curators) {
            Supervisor oldSupervisor = curator.getSupervisor();
            oldSupervisor.deleteCurator(curator);
            supervisorRepository.save(oldSupervisor);
            newSupervisor.addCurator(curator);
            curator.setSupervisor(newSupervisor);
            curatorRepository.save(curator);
        }
        supervisorRepository.save(newSupervisor);
        return ResponseEntity.ok("Success");
    }

    public ResponseEntity<SupervisorDto> getSupervisorByEmail(String email) throws UserNotFoundByUsernameException {
        if (!supervisorRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Руководитель кураторов", email);
        }
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        SupervisorDto supervisorDto = MappingUtil.mapToSupervisorDto(supervisor);
        return ResponseEntity.ok(supervisorDto);
    }

    public ResponseEntity updateSupervisor(String email, RequestSupervisor supervisor) throws UserNotFoundByUsernameException, UserAlreadyExistsException {
        if (!supervisorRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Руководитель кураторов", email);
        }
        Supervisor oldSupervisor = supervisorRepository.findByEmail(email);
        if (supervisorRepository.existsByEmail(supervisor.getEmail()) && !Objects.equals(supervisor.getEmail(), email)) {
            throw new UserAlreadyExistsException("Руководитель кураторов", email);
        }
        if (supervisor.getFirst_name() != null) {
            oldSupervisor.setFirst_name(supervisor.getFirst_name());
        }
        if (supervisor.getLast_name() != null) {
            oldSupervisor.setFirst_name(supervisor.getLast_name());
        }
        if (supervisor.getPersonal_data() != null) {
            oldSupervisor.setFirst_name(supervisor.getPersonal_data());
        }
        if (supervisor.getCurators_email() != null) {
            var curatorsEmailSet = supervisor.getCurators_email();
            var curatorsSet = curatorsEmailSet.stream()
                    .filter(curatorRepository::existsByEmail)
                    .map(curatorRepository::getByEmail)
                    .collect(Collectors.toSet());
            oldSupervisor.setCurators(curatorsSet);
        }
        return ResponseEntity.ok(supervisorRepository.save(oldSupervisor));
    }

    public ResponseEntity<String> deleteSupervisor(String email) throws UserNotFoundByUsernameException {
        if (!supervisorRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Руководитель кураторов", email);
        }
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        Supervisor fictionalSupervisor = supervisorRepository.findByEmail("vis0@example.com");
        var curators = supervisor.getCurators();
        for(Curator curator : curators) {
            curator.setSupervisor(fictionalSupervisor);
            curatorRepository.save(curator);
            fictionalSupervisor.addCurator(curator);
            supervisorRepository.save(fictionalSupervisor);
        }
        supervisorRepository.delete(supervisor);
        return ResponseEntity.ok(format("Руководитель кураторов %s успешно удален", email));
    }
}
