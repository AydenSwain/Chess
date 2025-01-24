package chess;

import java.util.ArrayList;

public class KnightMovesCalculator extends PieceMovesCalculator {
    public KnightMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> calculateMoves() {
        clearMoves();

        // Search 1 o-clock
        searchSpace(2, 1);

        // Search 2 o-clock
        searchSpace(1, 2);

        // Search 4 o-clock
        searchSpace(-1, 2);

        // Search 5 o-clock
        searchSpace(-2, 1);

        // Search 7 o-clock
        searchSpace(-2, -1);

        // Search 8 o-clock
        searchSpace(-1, -2);

        // Search 10 o-clock
        searchSpace(1, -2);

        // Search 11 o-clock
        searchSpace(2, -1);

        return getMoves();
    }
}
