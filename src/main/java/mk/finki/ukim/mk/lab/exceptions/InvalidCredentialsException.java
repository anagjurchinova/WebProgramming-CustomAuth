package mk.finki.ukim.mk.lab.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Invalid credentials.";
    }
}
