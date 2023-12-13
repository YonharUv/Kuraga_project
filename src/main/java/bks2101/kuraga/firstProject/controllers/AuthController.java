package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.JwtRequest;
import bks2101.kuraga.firstProject.dto.JwtResponse;
import bks2101.kuraga.firstProject.dto.ResetPassRequest;
import bks2101.kuraga.firstProject.entitys.ApplicationUser;
import bks2101.kuraga.firstProject.exceptions.AppError;
import bks2101.kuraga.firstProject.repository.UserRepository;
import bks2101.kuraga.firstProject.service.UserDetailsServiceImpl;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl userService;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        String username = authRequest.getUsername();
        try {
            ApplicationUser user =  userRepository.getByUsername(username);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Аккаунт пользователя ещё не активирован"), HttpStatus.BAD_REQUEST);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Не корректный код активации"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Пользователь успешно активирован");
    }

    @PostMapping("/forgotPass/{email}")
    public ResponseEntity<?> forgotPass(@PathVariable String email) {
        boolean addToken = userService.forgotPass(email);

        if (!addToken) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким email не найден"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Письмо для сброса пароля выслано на указанную почту");
    }

    @PostMapping("/forgotPass/{token}/resetPass")
    public ResponseEntity<?> resetPass(@PathVariable String token, @RequestBody ResetPassRequest resetRequest) {
        boolean reset = userService.resetPass(token, resetRequest.getPassword());

        if (!reset) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Не корректный токен сброса пароля"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Пароль был успешно изменён");
    }
}

