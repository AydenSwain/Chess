package ui;

import chess.ChessGame;

import static ui.EscapeSequences.*;

public class ChessPieceText {
    public static String getKingText(ChessGame.TeamColor teamColor) {
        return BLACK_KING;
    }

    public static String getQueenText(ChessGame.TeamColor teamColor) {
        return BLACK_QUEEN;
    }

    public static String getBishopText(ChessGame.TeamColor teamColor) {
        return BLACK_BISHOP;
    }

    public static String getKnightText(ChessGame.TeamColor teamColor) {
        return BLACK_KNIGHT;
    }

    public static String getRookText(ChessGame.TeamColor teamColor) {
        return BLACK_ROOK;
    }

    public static String getPawnText(ChessGame.TeamColor teamColor) {
        return BLACK_PAWN;
    }
}
