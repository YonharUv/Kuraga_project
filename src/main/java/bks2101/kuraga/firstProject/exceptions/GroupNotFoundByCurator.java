package bks2101.kuraga.firstProject.exceptions;

import static java.lang.String.format;

public class GroupNotFoundByCurator extends Exception {
    public GroupNotFoundByCurator(String curatorEmail, String groupName) {
        super(format("У куратора %s нет группы %s", curatorEmail, groupName));
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
