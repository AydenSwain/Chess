package chess;

import java.util.ArrayList;

public class PawnMovesCalculator extends PieceMovesCalculator {
    private ArrayList<ChessMove> moves;

    public PawnMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        super(board, myPosition, teamColor, pieceType);
    }

    public ArrayList<ChessMove> calculateMoves() {
        moves = new ArrayList<ChessMove>();

        // If color is white
        if (teamColor == ChessGame.TeamColor.WHITE) {
            // If in starting position
            if (myPosition.getRow() == 2) {
                pawnAdvanceFromStart(1);
            }

            // Check advancing up
            else {
                pawnAdvance(1);
            }

            // Check attacking top left
            pawnAttack(1, -1);

            // Check attacking top right
            pawnAttack(1, 1);
        }

        // If color is black
        else {
            // If in starting position
            if (myPosition.getRow() == 7) {
                pawnAdvanceFromStart(-1);
            }

            // Check advancing up
            else {
                pawnAdvance(-1);
            }

            // Check attacking top left
            pawnAttack(-1, -1);

            // Check attacking top right
            pawnAttack(-1, 1);
        }

        // Check to see if there was a promotion and create the possibilities
        checkPromotion();

        return moves;
    }

    // Searches the two spaces in front of the pawn (starting position)
    private void pawnAdvanceFromStart(int rowChange) {
        ChessPosition searchPosition = myPosition;
        // Iterate twice
        for (int i = 0; i < 2; i++) {
            // Move the search position
            searchPosition = new ChessPosition(searchPosition.getRow() + rowChange, searchPosition.getColumn());

            // If the space is out of bounds
            if (searchPosition.isOutOfBounds()) {
                break;
            }

            // If there is a piece in the space
            if (board.getPiece(searchPosition) != null) {
                    break;
            }

            // If the space is clear
            moves.add(new ChessMove(myPosition, searchPosition));
        }
    }

    // Searches the space in front of the pawn
    private void pawnAdvance(int rowChange) {
        // Move the search position
        ChessPosition searchPosition = new ChessPosition(myPosition.getRow() + rowChange, myPosition.getColumn());

        // If the space is out of bounds
        if (searchPosition.isOutOfBounds()) {
            return;
        }

        // If there is a piece in the space
        if (board.getPiece(searchPosition) != null) {
            return;
        }

        moves.add(new ChessMove(myPosition, searchPosition));
    }

    // Searches the spaces to the diagonals of the pawn
    private void pawnAttack(int rowChange, int columnChange) {
        // Move the search position
        ChessPosition searchPosition = new ChessPosition(myPosition.getRow() + rowChange, myPosition.getColumn() + columnChange);

        // If the space is out of bounds
        if (searchPosition.isOutOfBounds()) {
            return;
        }

        // If there is a piece in the space
        if (board.getPiece(searchPosition) != null) {
            // If that piece is on the opposing team
            if(board.getPiece(searchPosition).getTeamColor() != teamColor) {
                moves.add(new ChessMove(myPosition, searchPosition));
            }
        }
    }

    // Check to see if there was a promotion and create the possibilities
    private void checkPromotion(){
        // If there are no valid moves
        if (moves.isEmpty()) {
            return;
        }

        // If pawn will be promoted
        int newRow = moves.getFirst().getEndPosition().getRow();
        if (newRow == 8 || newRow == 1) {
            ArrayList<ChessMove> promotionMoves = new ArrayList<ChessMove>();
            for (ChessMove move : moves) {
                promotionMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.BISHOP));
                promotionMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.QUEEN));
                promotionMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.KNIGHT));
                promotionMoves.add(new ChessMove(myPosition, move.getEndPosition(), ChessPiece.PieceType.ROOK));
            }
            moves = promotionMoves;
        }
    }
}
