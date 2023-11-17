package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.RequestAddByUsername;
import bks2101.kuraga.firstProject.dto.SupervisorDto;
import bks2101.kuraga.firstProject.models.Curator;
import bks2101.kuraga.firstProject.models.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SupervisorService {
    private final SupervisorRepository supervisorRepository;
    private final JwtTokenUtils jwtTokenUtils;
    // private final CuratorService curatorService;
    private final CuratorRepository curatorRepository;
    public ResponseEntity getAllSupervisors() {
        return ResponseEntity.ok(supervisorRepository.findAll());
    }
    public ResponseEntity createSupervisor(String authHeader, SupervisorDto supervisorDto) {
        String jwtToken = authHeader.substring(7);
        String username = jwtTokenUtils.getUsername(jwtToken);
        Supervisor supervisor = new Supervisor();
        supervisor.setFirst_name(supervisorDto.getFirst_name());
        supervisor.setLast_name(supervisorDto.getLast_name());
        supervisor.setPersonal_data(supervisorDto.getPersonal_data());
        supervisor.setUsername(username);
        return ResponseEntity.ok(supervisorRepository.save(supervisor));
    }
    public ResponseEntity addCurators(String username, RequestAddByUsername curatorsName) {
        Supervisor supervisor = supervisorRepository.findByUsername(username);
        Set<Curator> curatorSet = supervisor.getCurators();
        Set<String> addCuratorsUsername = curatorsName.getCuratorsUsername();
        for (String curatorUsername : addCuratorsUsername) {
            Curator curator = curatorRepository.findByUsername(curatorUsername);
            curatorSet.add(curator);
            curator.setSupervisor(supervisor);
            curatorRepository.save(curator);
        }
        return ResponseEntity.ok(supervisorRepository.save(supervisor));
    }
    public Supervisor findSupervisorByUsername(String username) {
        return supervisorRepository.findByUsername(username);
    }
    public void saveSupervisor(Supervisor supervisor) {
        supervisorRepository.save(supervisor);
    }
}
