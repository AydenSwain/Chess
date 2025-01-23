package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {
    protected final ChessBoard board;
    protected final  ChessPosition myPosition;
    protected ChessGame.TeamColor teamColor;
    protected final ChessPiece.PieceType pieceType;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType) {
        this.board = board;
        this.myPosition = myPosition;
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }
}
