package chess;

import java.util.ArrayList;

public class BishopMovesCalculator extends PieceMovesCalculator {
    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
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

        return getMoves();
    }
}
