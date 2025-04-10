package WebSocket;

public interface Handler {
    <T> void handle(T obj);
}
