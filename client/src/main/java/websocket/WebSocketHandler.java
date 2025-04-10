package websocket;

import chess.ChessGame;
import ui.BoardPrinter;
import websocket.messages.ServerMessage;

import static websocket.messages.ServerMessage.*;

public class WebSocketHandler {
    private final ChessGame.TeamColor color;
    private ChessGame chessGame;
    private ServerMessage serverMessage;

    public WebSocketHandler(ChessGame game, ChessGame.TeamColor color) {
        this.chessGame = game;
        this.color = color;
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
        chessGame = serverMessage.getGame();

        new BoardPrinter(chessGame.getBoard()).print(color);
    }

    private void notification() {
        System.out.println(serverMessage.getMessage());
    }

    private void error() {
        System.out.println(serverMessage.getErrorMessage());
    }
}
