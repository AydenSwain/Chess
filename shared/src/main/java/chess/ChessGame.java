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
    private boolean isInPlay = true;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
        setBoard(board);
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

    public boolean isInPlay() {
        return isInPlay;
    }

    public void startGame() {
        board.resetBoard();
    }

    public void gameOver() {
        isInPlay = false;
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
        // Get information from the position that is passed in
        ChessPiece currentPiece = board.getPiece(startPosition);

        // If piece is null
        if (currentPiece == null) {
            return null;
        }

        Collection<ChessMove> collection = currentPiece.pieceMoves(board, startPosition);
        ArrayList<ChessMove> currentPieceMoves = new ArrayList<>(collection);
        TeamColor currentColor = currentPiece.getTeamColor();


        ArrayList<ChessMove> validPieceMoves = new ArrayList<>();
        // If white
        if (currentColor == TeamColor.WHITE) {
            whiteValidMoves(startPosition, currentPiece, currentPieceMoves, validPieceMoves);
        }

        // If black
        else {
            blackValidMoves(startPosition, currentPiece, currentPieceMoves, validPieceMoves);
        }

        return validPieceMoves;
    }

    private void whiteValidMoves(ChessPosition startPosition, ChessPiece currentPiece, ArrayList<ChessMove> currentPieceMoves,
                                 ArrayList<ChessMove> validPieceMoves) {

        // Loop through the moves of the current piece
        for (ChessMove move : currentPieceMoves) {
            // Prepare to make the move
            ChessPosition oldPosition = move.getStartPosition();
            ChessPosition newPosition = move.getEndPosition();
            ChessPiece movingPiece = board.getPiece(oldPosition);
            ChessPiece takenPiece = board.getPiece(newPosition);
            boolean isKing = movingPiece.getPieceType() == ChessPiece.PieceType.KING;

            // Reassign the old location
            board.addPiece(oldPosition, null);
            // Reassign the new location
            board.addPiece(newPosition, currentPiece);

            // If moving piece is a king update position
            if (isKing) {
                whiteKingPosition = newPosition;
            }

            // If move doesn't put their own king in check
            if (!isInCheck(TeamColor.WHITE)) {
                validPieceMoves.add(move);
            }

            // Revert move
            // Reassign the old location
            board.addPiece(oldPosition, movingPiece);
            // Reassign the new location
            board.addPiece(newPosition, takenPiece);

            // If moving piece is a king, revert position
            if (isKing) {
                whiteKingPosition = oldPosition;
            }
        }
    }

    private void blackValidMoves(ChessPosition startPosition, ChessPiece currentPiece, ArrayList<ChessMove> currentPieceMoves,
                                 ArrayList<ChessMove> validPieceMoves) {
        // Loop through the moves of the current piece
        for (ChessMove move : currentPieceMoves) {
            // Prepare to make the move
            ChessPosition oldPosition = move.getStartPosition();
            ChessPosition newPosition = move.getEndPosition();
            ChessPiece movingPiece = board.getPiece(oldPosition);
            ChessPiece takenPiece = board.getPiece(newPosition);
            boolean isKing = movingPiece.getPieceType() == ChessPiece.PieceType.KING;

            // Reassign the old location
            board.addPiece(oldPosition, null);
            // Reassign the new location
            board.addPiece(newPosition, currentPiece);

            // If moving piece is a king update position
            if (isKing) {
                blackKingPosition = newPosition;
            }

            // If move doesn't put their own king in check
            if (!isInCheck(TeamColor.BLACK)) {
                validPieceMoves.add(move);
            }

            // Revert move
            // Reassign the old location
            board.addPiece(oldPosition, movingPiece);
            // Reassign the new location
            board.addPiece(newPosition, takenPiece);

            // If moving piece is a king, revert position
            if (isKing) {
                blackKingPosition = oldPosition;
            }
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition oldPosition = move.getStartPosition();
        ChessPiece movingPiece = board.getPiece(oldPosition);

        // If the old position is null throw exception
        if (movingPiece == null) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        TeamColor myColor = movingPiece.getTeamColor();
        // If not your turn
        if (myColor != teamTurn) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        // If move is one of the valid moves is the current move, then make the move
        Collection<ChessMove> validMoves = validMoves(oldPosition);
        if (validMoves.contains(move)) {
            makeMove(move, movingPiece, myColor, oldPosition);
        }

        // If the move is invalid
        else {
            throw new InvalidMoveException("Invalid move: " + move);
        }
    }

    private void makeMove(ChessMove move, ChessPiece movingPiece, TeamColor myColor, ChessPosition oldPosition) {
        // Check for promotion, if so, then change the piece type
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        if (promotionPiece != null) {
            movingPiece.setPieceType(promotionPiece);
        }

        ChessPosition newPosition = move.getEndPosition();
        // If there is a piece in the new location
        ChessPiece takenPiece = board.getPiece(newPosition);
        if (takenPiece != null) {
            // Remove this piece from it's respective list
            // If my color is white
            if (myColor == TeamColor.WHITE) {
                blackPiecePositions.remove(newPosition);
            }
            else {
                whitePiecePositions.remove(newPosition);
            }
        }

        // Reassign the old location
        board.addPiece(oldPosition, null);
        // Reassign the new location
        board.addPiece(newPosition, movingPiece);

        // Update my piece's list
        // If my color is white
        if (myColor == TeamColor.WHITE) {
            // Remove old position
            whitePiecePositions.remove(oldPosition);

            // Add new position
            whitePiecePositions.add(newPosition);

            // If is a king, update king location
            boolean isKing = movingPiece.getPieceType() == ChessPiece.PieceType.KING;
            if (isKing) {
                whiteKingPosition = newPosition;
            }
        }

        // If my color is black
        else {
            // Remove old position
            blackPiecePositions.remove(oldPosition);

            // Add new position
            blackPiecePositions.add(newPosition);

            // If is a king, update king location
            boolean isKing = movingPiece.getPieceType() == ChessPiece.PieceType.KING;
            if (isKing) {
                blackKingPosition = newPosition;
            }
        }

        // Prepare to change turn
        TeamColor otherColor;

        // If color is white
        if (myColor == TeamColor.WHITE) {
            otherColor = TeamColor.BLACK;
        }
        else {
            otherColor = TeamColor.WHITE;
        }

        // Change team turn
        setTeamTurn(otherColor);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> currentPieceMoves;
        boolean isInCheck = false;
        // If white
        if (teamColor == TeamColor.WHITE) {
            // Loop through all black pieces
            for (ChessPosition currentPosition : blackPiecePositions) {
                ChessPiece currentPiece = board.getPiece(currentPosition);

                // If the current piece has been taken in the validMoves() method
                if (currentPiece == null) {
                    continue;
                }

                Collection<ChessMove> collection = currentPiece.pieceMoves(board, currentPosition);
                currentPieceMoves = new ArrayList<>(collection);
                // Loop through the moves of the current piece
                for (ChessMove move : currentPieceMoves) {
                    if (move.getEndPosition().equals(whiteKingPosition)) {
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

            // If the current piece has been taken in the validMoves() method
            if (currentPiece == null) {
                continue;
            }

            Collection<ChessMove> collection = currentPiece.pieceMoves(board, currentPosition);
            currentPieceMoves = new ArrayList<>(collection);
            // Loop through the moves of the current piece
            for (ChessMove move : currentPieceMoves) {
                if (move.getEndPosition().equals(blackKingPosition)) {
                    isInCheck =  true;
                }
            }
        }

        return isInCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // If my king isn't in check
        if (!isInCheck(teamColor)) {
            return false;
        }

        // Find if there are any valid moves
        boolean hasValidMoves = hasValidMoves(teamColor);
        if (hasValidMoves) {
            return false;
        }

        // If king is in check and there are no valid moves
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // If my king is in check
        if (isInCheck(teamColor)) {
            return false;
        }

        // Find if there are any valid moves
        boolean hasValidMoves = hasValidMoves(teamColor);
        if (hasValidMoves) {
            return false;
        }

        // If king is in check and there are valid moves
        return true;
    }

    private boolean hasValidMoves(TeamColor teamColor) {
        // Assign myTeamsPositions list
        ArrayList<ChessPosition> myTeamsPositions;
        if (teamColor == TeamColor.WHITE) {
            myTeamsPositions = whitePiecePositions;
        }
        else {
            myTeamsPositions = blackPiecePositions;
        }

        ArrayList<ChessMove> allValidMoves = new ArrayList<>();
        // For each of my team's positions
        for (ChessPosition position : myTeamsPositions) {
            // Append valid moves to the list
            Collection<ChessMove> theseValidMoves = validMoves(position);
            if (theseValidMoves != null) {
                allValidMoves.addAll(theseValidMoves);
            }
        }

        // If valid moves is empty
        if (allValidMoves.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
        whitePiecePositions.clear();
        blackPiecePositions.clear();
        whiteKingPosition = null;
        blackKingPosition = null;

        // Iterate through the pieces and add their info to the instance variables
        for (int r = 1; r <= 8; r++) {
            for (int c = 1; c <= 8; c++) {
                // If is a piece
                ChessPiece currentPiece;
                ChessPosition currentPosition = new ChessPosition(r, c);
                if ((currentPiece = board.getPiece(currentPosition)) != null) {
                    recordPieceByColor(currentPosition, currentPiece);
                }
            }
        }
    }

    private void recordPieceByColor(ChessPosition position, ChessPiece piece) {
        // If piece is white
        ArrayList<ChessPosition> myTeamsPositions;
        ChessPosition myKingPosition;

        if (piece.getTeamColor() == TeamColor.WHITE) {
            myTeamsPositions = whitePiecePositions;

        } else {
            myTeamsPositions = blackPiecePositions;
        }

        myTeamsPositions.add(position);
        // If piece is also a king
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            // If piece is white
            if(piece.getTeamColor() == TeamColor.WHITE) {
                whiteKingPosition = position;
            }
            else {
                blackKingPosition = position;
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
