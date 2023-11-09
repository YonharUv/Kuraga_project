package bks2101.kuraga.firstProject.exceptions;

public class UserNotFoundByUsernameException extends Exception{
    public UserNotFoundByUsernameException(String username) {
        super("Пользователя " + username + " не существует");
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
