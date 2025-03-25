//package ui;
//
//import chess.ChessBoard;
//import chess.ChessGame;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class BoardPrinter {
//    private ChessBoard board;
//
//    BoardPrinter(ChessBoard board) {
//        this.board = board;
//    }
//
//    public void print(ChessGame.TeamColor teamColor) {
//        List<String> rowLabels = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));;
//        List<String> colLabels = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
//
//        if (teamColor == ChessGame.TeamColor.BLACK) {
//            Collections.reverse(rowLabels);
//            Collections.reverse(colLabels);
//        }
//
//        printColLabels(colLabels);
//
//        printBoard(rowLabels);
//
//        printColLabels(colLabels);
//    }
//
//    private void printColLabels(List<String> colLabels) {
//
//    }
//
//    private void printBoard(List<String> rowLabels) {
//        for (int line) {
//
//        }
//    }
//}
