package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String name, String email, String username) {
        super(format("%s с email = %s или с username = %s уже существует",name, email, username));
    }
    public UserAlreadyExistsException(String name, String username) {
        super(format("%s с username = %s уже существует",name, username));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
