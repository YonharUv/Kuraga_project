package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.ApplicationUser;
import bks2101.kuraga.firstProject.models.Role;
import bks2101.kuraga.firstProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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
    public ResponseEntity getUserByUsername(String username) throws UserNotFoundByUsernameException {
        if (!userRepository.existsByUsername(username)){
            throw new UserNotFoundByUsernameException("Пользователя", username);
        }
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public ResponseEntity<Optional<ApplicationUser>> getUserByID(Long id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("Пользователя",id);
        }
        return ResponseEntity.ok(userRepository.findById(id));
    }
    public ResponseEntity<List<ApplicationUser>> getAllUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    public ResponseEntity<String> deleteUserByID(long id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("Пользователя",id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(format("Пользователь с id = %d успешно удален", id));
    }
    public ResponseEntity<String> deleteUserByEmail(String email) throws UserNotFoundByUsernameException {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Пользователя",email);
        }
        userRepository.delete(userRepository.findByEmail(email));
        return ResponseEntity.ok(format("Пользователь %s успешно удален", email));
    }
    public ResponseEntity exchangeUser(ApplicationUser newUser, long id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("Пользователя", id);
        }
        return ResponseEntity.ok(userRepository.findById(id).map(applicationUser -> {
            applicationUser.setUsername(newUser.getUsername());
            applicationUser.setEmail(newUser.getEmail());
            applicationUser.setPassword(newUser.getPassword());
            applicationUser.setRole(newUser.getRole());
            return userRepository.save(applicationUser);
        }));
    }
}
