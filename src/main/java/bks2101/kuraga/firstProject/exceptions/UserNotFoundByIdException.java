package bks2101.kuraga.firstProject.exceptions;

public class UserNotFoundByIdException extends Exception{
    public UserNotFoundByIdException(Long id) {
        super("Нет пользователя с id = " + id);
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
