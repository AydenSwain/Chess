package Server;

public class ResponseException extends RuntimeException {
    private final int responseCode;

    public ResponseException(int responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
