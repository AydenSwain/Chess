package chess;

import java.util.ArrayList;

public class RookMovesCalculator extends PieceMovesCalculator {
    public RookMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> calculateMoves() {
        clearMoves();

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
