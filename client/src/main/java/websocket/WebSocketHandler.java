package websocket;

import chess.ChessGame;
import clientrepl.InGameClient;
import ui.BoardPrinter;
import websocket.messages.ServerMessage;

import static websocket.messages.ServerMessage.*;

public class WebSocketHandler {
    private final InGameClient gameClient;
    private ChessGame chessGame;
    private ServerMessage serverMessage;

    public WebSocketHandler(InGameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void handleMessage(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;

        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME -> loadGame();
            case NOTIFICATION -> notification();
            case ERROR -> error();
        }
    }

    private void loadGame() {
        ChessGame game = serverMessage.getGame();
        gameClient.setGame(game);

        new BoardPrinter(game.getBoard()).print(gameClient.getColor());
    }

    private void notification() {
        System.out.println(serverMessage.getMessage());
    }

    private void error() {
        System.out.println(serverMessage.getErrorMessage());
    }
}
