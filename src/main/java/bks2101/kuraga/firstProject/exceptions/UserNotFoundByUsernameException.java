package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class UserNotFoundByUsernameException extends Exception{
    public UserNotFoundByUsernameException(String name, String email) {
        super(format("%s %s не существует", name, email));
    }
    public UserNotFoundByUsernameException(String name, long id) {
        super(format("%s id=%d не существует", name, id));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
