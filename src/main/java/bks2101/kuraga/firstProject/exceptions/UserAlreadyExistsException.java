package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String email, String username) {
        super(format("Пользователь с email = %s или с username = %s уже существует", email, username));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
