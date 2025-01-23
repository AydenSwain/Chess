package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {
    private final ChessBoard board;
    private final  ChessPosition myPosition;
    private final ChessGame.TeamColor teamColor;
    private final ChessPiece.PieceType pieceType;
    private ArrayList<ChessMove> moves;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        this.board = board;
        this.myPosition = myPosition;
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    // Searches in the direction specified by the change in row and column parameters
    public void searchDirection(int rowChange, int columnChange) {
        ChessPosition searchPosition = myPosition;
        while (true) {
            searchPosition = new ChessPosition(searchPosition.getRow() + rowChange, searchPosition.getColumn() + columnChange);
            if (searchPosition.isOutOfBounds()) {
                break;
            }
            if (board.getPiece(searchPosition) != null &&
                    board.getPiece(searchPosition).getTeamColor() == teamColor) {
                break;
            }
            moves.add(new ChessMove(myPosition, searchPosition, null));
        }
    }

    public ArrayList<ChessMove> getMoves() {
        return moves;
    }

    public void clearMoves() {
        moves = new ArrayList<ChessMove>();
    }
}
