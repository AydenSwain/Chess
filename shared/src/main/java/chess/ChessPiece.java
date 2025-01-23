package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor teamColor;
    private PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        teamColor = pieceColor;
        pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // If the piece is a bishop
        if (pieceType == PieceType.BISHOP) {
            BishopMovesCalculator movesCalculator = new BishopMovesCalculator(board, myPosition, teamColor, pieceType);
            return movesCalculator.calculateMoves();
        }

        // If the piece is a queen
        if (pieceType == PieceType.QUEEN) {
            QueenMovesCalculator movesCalculator = new QueenMovesCalculator(board, myPosition, teamColor, pieceType);
            return movesCalculator.calculateMoves();
        }

        // If the piece is a king
        if (pieceType == PieceType.KING) {
            KingMovesCalculator movesCalculator = new KingMovesCalculator(board, myPosition, teamColor, pieceType);
            return movesCalculator.calculateMoves();
        }

//        // If the piece is a knight
//        if (pieceType == PieceType.KNIGHT) {
//            KnightMovesCalculator movesCalculator = new KnightMovesCalculator(board, myPosition, teamColor, pieceType);
//            return movesCalculator.calculateMoves();
//        }

        // If the piece is a rook
        if (pieceType == PieceType.ROOK) {
            RookMovesCalculator movesCalculator = new RookMovesCalculator(board, myPosition, teamColor, pieceType);
            return movesCalculator.calculateMoves();
        }

//        // If the piece is a pawn
//        if (pieceType == PieceType.PAWN) {
//            PawnMovesCalculator movesCalculator = new PawnMovesCalculator(board, myPosition, teamColor, pieceType);
//            return movesCalculator.calculateMoves();
//        }

        return null;
    }
}
