package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

import static websocket.commands.UserGameCommand.CommandType.*;

public class WebSocketFacade extends Endpoint {
    private Session session;
    private final WebSocketHandler handler;

    public WebSocketFacade(String url, WebSocketHandler handler) {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.handler = handler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxSessionIdleTimeout(30 * 60 * 1000);
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    handler.handleMessage(serverMessage);
                }
            });

        } catch (Exception e) {
            throw new WebSocketException("Error: " + e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connect(String authToken, int gameID) {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        } catch (Exception e) {
            throw new WebSocketException("Error: Could not connect");
        }
    }

    public void makeMove(String authToken, int gameID, ChessMove move) {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(MAKE_MOVE, authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        } catch (Exception e) {
            throw new WebSocketException("Error: Could not make move");
        }
    }

    public void leave(String authToken, int gameID) {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(LEAVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
            this.session.close();
        } catch (Exception e) {
            throw new WebSocketException("Error: Could not leave");
        }
    }

    public void resign(String authToken, int gameID) {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(RESIGN, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        } catch (Exception e) {
            throw new WebSocketException("Error: Could not resign");
        }
    }
}