import chess.*;
import server.*;

public class Main {
    public static void main(String[] args) {
        // ----- I have no idea why this is here \/
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess server.Server: " + piece);

        Server server = new Server();
        server.run(8080);
    }
}