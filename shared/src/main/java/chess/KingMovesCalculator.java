package chess;

import java.util.ArrayList;

public class KingMovesCalculator extends PieceMovesCalculator {
    public KingMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> calculateMoves() {
        clearMoves();

        // Search up
        searchSpace(1, 0);

        // Search down
        searchSpace(-1, 0);

        // Search left
        searchSpace(0, -1);

        // Search right
        searchSpace(0, 1);

        return getMoves();
    }
}
