package ui;

import chess.ChessGame;

import static ui.EscapeSequences.*;

public class ChessPieceText {
    public static String getKingText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_KING;
        }

        return BLACK_KING;
    }

    public static String getQueenText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_QUEEN;
        }

        return BLACK_QUEEN;
    }

    public static String getBishopText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_BISHOP;
        }

        return BLACK_BISHOP;
    }

    public static String getKnightText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_KNIGHT;
        }

        return BLACK_KNIGHT;
    }

    public static String getRookText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_ROOK;
        }

        return BLACK_ROOK;
    }

    public static String getPawnText(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            return WHITE_PAWN;
        }

        return BLACK_PAWN;
    }
}
