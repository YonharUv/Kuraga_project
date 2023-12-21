package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(NotFoundByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserNotFoundByIdException(NotFoundByIdException e) {
        log.error("Handling NotFoundByIdException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(UserNotFoundByUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserNotFoundByUsernameException(UserNotFoundByUsernameException e) {
        log.error("Handling UserNotFoundByUsernameException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException e) {
        log.error("UserAlreadyExistsException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(GroupNotFoundByCurator.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleGroupNotFoundByCurator(UserAlreadyExistsException e) {
        log.error("GroupNotFoundByCurator: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
