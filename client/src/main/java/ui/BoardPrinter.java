package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.ChessPieceText.*;
import static ui.EscapeSequences.*;

public class BoardPrinter {
    private static final String SET_BOARDER_BG_COLOR = SET_BG_COLOR_BLUE;
    private static final String SET_BOARDER_TEXT_COLOR = SET_TEXT_COLOR_BLACK;

    private static final String SET_LIGHT_SQUARE_BG_COLOR = SET_BG_COLOR_GREEN;
    private static final String SET_DARK_SQUARE_BG_COLOR = SET_BG_COLOR_DARK_GREEN;

    private static final String SET_LIGHT_HIGHLIGHT_BG_COLOR = SET_BG_COLOR_LIGHT_GREY;
    private static final String SET_DARK_HIGHLIGHT_BG_COLOR = SET_BG_COLOR_DARK_GREY;

    private static final String PADDING = " ";

    private ChessPiece[][] pieces;
    private ChessGame.TeamColor teamColor;
    private Collection<ChessPosition> positions;

    private PrintStream out;

    public BoardPrinter(ChessBoard board) {
        this.pieces = board.getSquares();
    }

    public void print(ChessGame.TeamColor teamColor, Collection<ChessPosition> positions) {
        this.teamColor = teamColor;
        this.positions = positions;

        out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        List<String> rowLabels = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));;
        List<String> colLabels = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));

        if (teamColor == ChessGame.TeamColor.BLACK) {
            Collections.reverse(colLabels);
        } else {
            Collections.reverse(rowLabels);
        }

        printColLabels(colLabels);

        printSquares(rowLabels);

        printColLabels(colLabels);
    }

    private void printColLabels(List<String> colLabels) {
        printBoarderText(PADDING);

        for (String label : colLabels) {
            printBoarderText(label);
        }

        printBoarderText(PADDING);
        printEndline();
    }

    private void printBoarderText(String text) {
        out.print(SET_BOARDER_BG_COLOR);
        out.print(SET_BOARDER_TEXT_COLOR);

        out.print(PADDING);
        out.print(text);
        out.print(EMPTY);
    }

    private void printEndline() {
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);

        out.print("\n");
    }

    private void printSquares(List<String> rowLabels) {
        for (int row = 0; row < 8; row++) {
            printBoarderText(rowLabels.get(row));

            for (int col = 0; col < 8; col++) {
                ChessPiece chessPiece = getChessPiece(row, col);
                String chessPieceText = getChessPieceText(chessPiece);
                ChessGame.TeamColor pieceTeamColor = getTeamColor(chessPiece);

                printSquare(row, col, chessPieceText, pieceTeamColor);
            }

            printBoarderText(rowLabels.get(row));
            printEndline();
        }
    }

    private ChessPiece getChessPiece(int row, int col) {
        ArrayList<Integer> newCords = changeCordsByColor(row, col);
        return pieces[newCords.get(0)][newCords.get(1)];
    }

    private ChessGame.TeamColor getTeamColor(ChessPiece chessPiece) {
        if (chessPiece != null) {
            return chessPiece.getTeamColor();
        }

        return  null;
    }

    private String getChessPieceText(ChessPiece chessPiece) {
        ChessGame.TeamColor pieceTeamColor = getTeamColor(chessPiece);

        if (chessPiece == null) {
            return EMPTY;
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.KING) {
            return getKingText(pieceTeamColor);
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            return getQueenText(pieceTeamColor);
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            return getBishopText(pieceTeamColor);
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            return getKnightText(pieceTeamColor);
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return getRookText(pieceTeamColor);
        }

        if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
            return getPawnText(pieceTeamColor);
        }

        return null;
    }

    private ArrayList<Integer> changeCordsByColor(int row, int col) {
        ArrayList<Integer> out = new ArrayList<>();

        if (teamColor == ChessGame.TeamColor.BLACK) {
            row = 7 - row;
            col = 7 - col;
        }

        out.add(row);
        out.add(col);
        return out;
    }

    private void printSquare(int row, int col, String text, ChessGame.TeamColor pieceTeamColor) {
        String setBackgroundColor;
        String setTextColor;
        ChessPosition position = new ChessPosition(8 - row, col + 1);

        if (isLightSquare(row, col)) {
            setBackgroundColor = SET_LIGHT_SQUARE_BG_COLOR;

            if (positions != null && positions.contains(position)) {
                setBackgroundColor = SET_LIGHT_HIGHLIGHT_BG_COLOR;
            }

        } else {
            setBackgroundColor = SET_DARK_SQUARE_BG_COLOR;

            if (positions != null && positions.contains(position)) {
                setBackgroundColor = SET_DARK_HIGHLIGHT_BG_COLOR;
            }
        }

        if (pieceTeamColor == ChessGame.TeamColor.WHITE) {
            setTextColor = SET_TEXT_COLOR_WHITE;
        } else {
            setTextColor = SET_TEXT_COLOR_BLACK;
        }

        out.print(setBackgroundColor);
        out.print(setTextColor);

        out.print(PADDING);
        out.print(text);
        out.print(PADDING);
    }

    private boolean isLightSquare(int row, int col) {
        int sum = row + col;
        return (sum == 0) || (sum % 2 == 0);
    }
}
