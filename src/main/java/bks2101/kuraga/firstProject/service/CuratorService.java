package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.models.Curator;
import bks2101.kuraga.firstProject.models.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuratorService {
    private final CuratorRepository curatorRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final SupervisorRepository supervisorRepository;
    public ResponseEntity getAllCurators() {
        return ResponseEntity.ok(curatorRepository.findAll());
    }
    public ResponseEntity createCurator(String authHeader, CuratorDto curatorDto) throws UserAlreadyExistsException {
        String jwtToken = authHeader.substring(7);
        String username = jwtTokenUtils.getUsername(jwtToken);
        Curator curator = new Curator();
        curator.setFirst_name(curatorDto.getFirst_name());
        curator.setLast_name(curatorDto.getLast_name());
        curator.setPersonal_data(curatorDto.getPersonal_data());
        curator.setUsername(username);
        Supervisor supervisor = supervisorRepository.findByUsername(curatorDto.getSupervisor_username());
        supervisor.addCurator(curator);
        curator.setSupervisor(supervisor);
        supervisorRepository.save(supervisor);
        curatorRepository.save(curator);
        return ResponseEntity.ok("Куратор успешно создан");
    }
    public Curator getCuratorByUsername(String username) {
        return curatorRepository.findByUsername(username);
    }
    public void saveCurator(Curator curator) {
        curatorRepository.save(curator);
    }
}
