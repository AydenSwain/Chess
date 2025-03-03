package handler;

public class UsernameAlreadyTaken extends RuntimeException {
    public UsernameAlreadyTaken(String message) {
        super(message);
    }
}
