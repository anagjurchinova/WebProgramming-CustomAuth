package mk.finki.ukim.mk.lab.exceptions;

public class PasswordMismatchException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Your password does not match.";
    }
}
