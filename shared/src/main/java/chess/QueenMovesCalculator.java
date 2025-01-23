package chess;

import java.util.ArrayList;

public class QueenMovesCalculator {
    public QueenMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> calculateMoves() {
        clearMoves();

        // Search top left
        searchDirection(1, -1);

        // Search top right
        searchDirection(1, 1);

        // Search bottom left
        searchDirection(-1, -1);

        // Search bottom right
        searchDirection(-1, 1);

        // Search up
        searchDirection(1, 0);

        // Search down
        searchDirection(-1, 0);

        // Search left
        searchDirection(0, -1);

        // Search right
        searchDirection(0, 1);

        return getMoves();
    }
}

