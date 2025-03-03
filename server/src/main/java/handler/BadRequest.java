package handler;

public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}
