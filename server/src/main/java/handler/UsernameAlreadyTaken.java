package handler;

public class UsernameAlreadyTaken extends RuntimeException {
    public UsernameAlreadyTaken() {
    }

    public UsernameAlreadyTaken(String message) {
        super(message);
    }
}
