package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class NotFoundByIdException extends Exception{
    public NotFoundByIdException(String name, Long id) {
        super(format("%s с id = %d не существует", name, id));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
