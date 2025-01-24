package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {
    protected final ChessBoard board;
    protected final  ChessPosition myPosition;
    protected final ChessGame.TeamColor teamColor;
    protected final ChessPiece.PieceType pieceType;
    protected ArrayList<ChessMove> moves;

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
            // Move the search position
            searchPosition = new ChessPosition(searchPosition.getRow() + rowChange, searchPosition.getColumn() + columnChange);

            // If the space is out of bounds
            if (searchPosition.isOutOfBounds()) {
                break;
            }

            // If there is a piece in the space
            if (board.getPiece(searchPosition) != null) {
                // If that piece is on the same team
                if(board.getPiece(searchPosition).getTeamColor() == teamColor) {
                    break;
                }
                // If that piece is on the opposing team
                moves.add(new ChessMove(myPosition, searchPosition));
                break;
            }

            moves.add(new ChessMove(myPosition, searchPosition));
        }
    }

    // Searches a space specified by the change in row and column parameters
    public void searchSpace(int rowChange, int columnChange) {
        // Move the search position
        ChessPosition searchPosition = new ChessPosition(myPosition.getRow() + rowChange, myPosition.getColumn() + columnChange);

        // If the space is out of bounds
        if (searchPosition.isOutOfBounds()) {
            return;
        }

        // If there is a piece in the space
        if (board.getPiece(searchPosition) != null) {
            // If that piece is on the same team
            if(board.getPiece(searchPosition).getTeamColor() == teamColor) {
                return;
            }
            // If that piece is on the opposing team
            moves.add(new ChessMove(myPosition, searchPosition));
            return;
        }

        moves.add(new ChessMove(myPosition, searchPosition));
    }

    public ArrayList<ChessMove> getMoves() {
        return moves;
    }

    public void clearMoves() {
        moves = new ArrayList<ChessMove>();
    }
}
