package chess;

import java.util.Collection;
import java.util.ArrayList;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;
    private ArrayList<ChessPosition> whitePiecePositions = new ArrayList<>();
    private ChessPosition whiteKingPosition;
    private ArrayList<ChessPosition> blackPiecePositions = new ArrayList<>();
    private ChessPosition blackKingPosition;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> currentPieceMoves;
        // If white
        if (teamColor == TeamColor.WHITE) {
            // Loop through all black pieces
            for (ChessPosition currentPosition : blackPiecePositions) {
                ChessPiece currentPiece = board.getPiece(currentPosition);
                Collection<ChessMove> collection = currentPiece.pieceMoves(board, currentPosition);
                currentPieceMoves = new ArrayList<>(collection);
                // Loop through the moves of the current piece
                for (ChessMove move : currentPieceMoves) {
                    if (move.getEndPosition() == whiteKingPosition) {
                        return true;
                    }
                }
            }
            return false;
        }

        // If black
        // Loop through all white pieces
        for (ChessPosition currentPosition : whitePiecePositions) {
            ChessPiece currentPiece = board.getPiece(currentPosition);
            Collection<ChessMove> collection = currentPiece.pieceMoves(board, currentPosition);
            currentPieceMoves = new ArrayList<>(collection);
            // Loop through the moves of the current piece
            for (ChessMove move : currentPieceMoves) {
                if (move.getEndPosition() == blackKingPosition) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;

        // Iterate through the pieces and add their info to the instance variables
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                // If is a piece
                ChessPiece currentPiece;
                ChessPosition currentPosition = new ChessPosition(r, c);
                if ((currentPiece = board.getPiece(currentPosition)) != null) {
                    // If piece is white
                    if (currentPiece.getTeamColor() == TeamColor.WHITE) {
                        whitePiecePositions.add(currentPosition);
                        // If piece is also a king
                        if (currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            whiteKingPosition = currentPosition;
                        }
                    }
                    // If piece is black
                    blackPiecePositions.add(currentPosition);
                    // If piece is also a king
                    if (currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        blackKingPosition = currentPosition;
                    }
                }
                // If not a piece continue
            }
        }
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
