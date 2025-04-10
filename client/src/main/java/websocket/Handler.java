package websocket;

public interface Handler {
    <T> void handle(T obj);
}
