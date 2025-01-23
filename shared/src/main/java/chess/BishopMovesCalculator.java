package chess;

import java.util.ArrayList;

public class BishopMovesCalculator extends PieceMovesCalculator {
    private ArrayList<ChessMove> moves;

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> getMoves() {
        this.moves = new ArrayList<ChessMove>();
        // Search top left
        searchDirection(1, -1);

        // Search top right
        searchDirection(1, 1);

        // Search bottom left
        searchDirection(-1, -1);

        // Search bottom right
        searchDirection(-1, -1);

        return this.moves;
    }

    private void searchDirection(int rowChange, int columnChange) {
        ChessPosition startPosition = myPosition;
        ChessPosition searchPosition = myPosition;
        while (true) {
            searchPosition = new ChessPosition(searchPosition.getRow() + rowChange, searchPosition.getColumn() + columnChange);

            if (searchPosition.isOutOfBounds()) {
                break;
            }
            if (super.board.getPiece(searchPosition) != null &&
                    super.board.getPiece(searchPosition).getTeamColor() == super.teamColor) {
                break;
            }
            moves.add(new ChessMove(startPosition, searchPosition, null));
        }
    }
}
