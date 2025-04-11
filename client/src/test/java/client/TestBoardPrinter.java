package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import org.junit.jupiter.api.*;
import ui.BoardPrinter;

import java.util.Collection;
import java.util.HashSet;

public class TestBoardPrinter {
    @Test
    public void printBoard() {
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        new BoardPrinter(board).print(ChessGame.TeamColor.WHITE, null);
        new BoardPrinter(board).print(ChessGame.TeamColor.BLACK, null);

        ChessGame game = new ChessGame();
        game.getBoard().resetBoard();

        ChessPosition position = new ChessPosition(2, 4);
        Collection<ChessMove> moves = game.validMoves(position);

        HashSet<ChessPosition> positions = new HashSet<>();
        for (ChessMove move : moves) {
            positions.add(move.getEndPosition());
        }

        new BoardPrinter(board).print(ChessGame.TeamColor.WHITE, positions);
    }
}
