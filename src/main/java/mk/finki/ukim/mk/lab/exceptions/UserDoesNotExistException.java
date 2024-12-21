package mk.finki.ukim.mk.lab.exceptions;

public class UserDoesNotExistException extends RuntimeException{
    String username;

    public UserDoesNotExistException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format("User with username %s does not exist.", username);
    }
}
