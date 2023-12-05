package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class UserNotFoundByUsernameException extends Exception{
    public UserNotFoundByUsernameException(String name, String email) {
        super(format("%s %s не существует", name, email));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
