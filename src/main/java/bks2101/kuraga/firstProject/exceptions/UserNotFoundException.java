package bks2101.kuraga.firstProject.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Long id) {
        super("Нет пользователя с id = " + id);
    }
}
