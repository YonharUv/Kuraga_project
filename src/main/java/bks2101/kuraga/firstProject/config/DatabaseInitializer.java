package bks2101.kuraga.firstProject.config;

import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.entitys.Role;
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
    }
}
