package bks2101.kuraga.firstProject.config;

import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.entitys.Supervisor;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final UserDetailsServiceImpl userService;
    private final UserRepository userRepository;
    private final SupervisorRepository supervisorRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    @Bean
    public void initializeDatabase() {
        String adminUsername = "admin";
        if (!userService.existsByUsername(adminUsername)) {
            ApplicationUser admin = new ApplicationUser();
            admin.setUsername(adminUsername);
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminPassword"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
        String visor0 = "vis0";
        if (!userRepository.existsByUsername(visor0)) {
            ApplicationUser vis = new ApplicationUser();
            vis.setUsername(visor0);
            vis.setEmail("vis0@example.com");
            vis.setPassword(passwordEncoder.encode("vis12345"));
            vis.setRole(Role.SUPERVISOR);
            userRepository.save(vis);
            Supervisor visor = new Supervisor();
            visor.setEmail("vis0@example.com");
            visor.setFirst_name("Not real supervisor");
            visor.setLast_name("Not real supervisor");
            visor.setUsername("vis0");
            supervisorRepository.save(visor);
        }
    }
}
