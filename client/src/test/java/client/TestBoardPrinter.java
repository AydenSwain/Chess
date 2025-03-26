package client;

import chess.ChessBoard;
import chess.ChessGame;
import org.junit.jupiter.api.*;
import ui.BoardPrinter;

public class TestBoardPrinter {
    @Test
    public void printBoard() {
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        new BoardPrinter(board).print(ChessGame.TeamColor.WHITE);
        new BoardPrinter(board).print(ChessGame.TeamColor.BLACK);
    }
}
