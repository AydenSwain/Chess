package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

public record Connection(String name, Session session) {
}
