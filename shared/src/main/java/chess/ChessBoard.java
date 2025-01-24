package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares;

    public ChessBoard() {
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[][]{
                {new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK),      new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),     new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),     new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN),      new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),     new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),     new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK)},
                {new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),      new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN)},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),      new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN)},
                {new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),      new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),     new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),     new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN),      new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING),       new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),     new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),     new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK)}
        };
    }

    @Override
    public String toString() {
        String boardString = "";
        boardString += Arrays.deepToString(squares);
        boardString = boardString.replace("], [", "],\n[");
        boardString = boardString.replace("null", "--");
        boardString = "\n" + boardString.substring(1, boardString.length() - 1);
        return boardString;
    }
}
