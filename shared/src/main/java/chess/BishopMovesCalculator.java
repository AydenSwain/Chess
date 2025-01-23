package chess;

import java.util.ArrayList;

public class BishopMovesCalculator extends PieceMovesCalculator {
    private ArrayList<ChessMove> moves;

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> getMoves() {
        this.moves = new ArrayList<ChessMove>();
        topLeftSearch();

        return this.moves;
    }

    private void topLeftSearch() {
        ChessPosition startPosition = myPosition;
        ChessPosition searchPosition = myPosition;
        while (true) {
            searchPosition = new ChessPosition(searchPosition.getRow() + 1, searchPosition.getColumn() - 1);
//            searchPosition.setRow(startPosition.getRow() + 1);
//            searchPosition.setColumn(startPosition.getColumn() - 1);
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
