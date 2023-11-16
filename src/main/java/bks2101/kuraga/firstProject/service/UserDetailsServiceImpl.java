package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.models.ApplicationUser;
import bks2101.kuraga.firstProject.models.Role;
import bks2101.kuraga.firstProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return UserDetailsImpl.fromUser(user);
    }
    public ApplicationUser createNewUser(RegistrationUserDto registrationUserDto) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
