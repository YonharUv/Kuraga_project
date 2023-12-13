package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.RegistrationUserDto;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.entitys.Role;
import bks2101.kuraga.firstProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailSender;
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
        user.setActivationCode(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public ResponseEntity<?> sendHelloMessage(ApplicationUser user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Kuraga. Please, visit next link: http://localhost:1000/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        mailSender.send(user.getEmail(), "Activation code", message);
        return ResponseEntity.ok("Пользователь успешно создан");
    }

    public boolean sendResetMessage(ApplicationUser user) {
        try{
            String message = String.format(
                    "Hello, %s! \n" +
                            "To reset password you should visit next link: http://localhost:1000/forgotPass/%s/resetPass",
                    user.getUsername(),
                    user.getResetToken()
            );

            mailSender.send(user.getEmail(), "Reset token", message);
            return true;
        } catch (BadCredentialsException e){
            return false;
        }

    }

    public ResponseEntity setRole(ApplicationUser user, Role role) {
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("Роль пользователя успешно изменена");
    }

    public ApplicationUser banUser(ApplicationUser user) {
        user.setRole(Role.BANNED);
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
    public ApplicationUser findUserByID(Long id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("Пользователя",id);
        }
        return userRepository.getById(id);
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

    public boolean activateUser(String code) {
        ApplicationUser user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }
    public boolean forgotPass(String email) {
        ApplicationUser user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);
        sendResetMessage(user);

        return true;
    }

    public boolean resetPass(String token, String password) {
        ApplicationUser user = userRepository.findByResetToken(token);
        if (user == null) {
            return false;
        }
        user.setResetToken(null);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return true;
    }
}
