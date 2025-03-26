package clientrepl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import clienttoserver.ResponseException;
import clienttoserver.ServerFacade;
import chess.ChessBoard;
import chess.ChessGame;
import model.GameData;
import model.PlayerData;
import ui.BoardPrinter;

public class PostLoginClient implements Client{
    private final ServerFacade facade;
    private static final int INDEX_MODIFIER = 1;

    public PostLoginClient(ServerFacade facade) {
        this.facade = facade;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            String command = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (command) {
                case "logout" -> logout();
                case "create_game" -> createGame(params);
                case "list_games" -> listGames();
                case "play_game" -> playGame(params);
                case "observe_game" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String logout() {
        facade.logout(Repl.clientAuthData);

        Repl.client = new PreLoginClient(facade);

        String username = Repl.clientAuthData.username();
        return "Logged out from \"" + username + "\"";
    }

    public String createGame(String[] params) {
        if (params.length == 1) {
            String gameName = params[0];

            GameData gameData = new GameData(0, null, null, gameName, null);
            GameData newGameData = facade.createGame(Repl.clientAuthData, gameData);

            return "Created game: \"" + gameName + "\"";
        }
        throw new ResponseException(400, "Expected: <game_name>");
    }

    public String listGames() {
        Collection<GameData> games = facade.listGames(Repl.clientAuthData);

        updateGames(games);

        String out = "Current games:";
        for (int i = 0; i < Repl.games.size(); i++) {
            out += "\n" + gameString(i, Repl.games.get(i));
        }

        return out;
    }

    private void updateGames(Collection<GameData> games) {
        Repl.games = new ArrayList<>();
        Repl.games.addAll(games);
    }

    private String gameString(int i, GameData gameData) {
        String whiteUsername = (gameData.whiteUsername() == null) ? "<none>" : gameData.whiteUsername();
        String blackUsername = (gameData.blackUsername() == null) ? "<none>" : gameData.blackUsername();

        return String.format("Game number:\"%d\" Game name:\"%s\", White username:\"%s\", Black username:\"%s\"",
                             i + INDEX_MODIFIER, gameData.gameName(), whiteUsername, blackUsername);
    }

    public String playGame(String[] params) {
        if (params.length == 2 && isValidGameNumber(params[0]) && isValidColor(params[1])) {
            int gameNumber = Integer.parseInt(params[0]);
            int gameIndex = gameNumber - INDEX_MODIFIER;
            ChessGame.TeamColor color = (params[1].equals("white")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

            GameData gameData = Repl.games.get(gameIndex);

            PlayerData playerData = new PlayerData(color, gameData.gameID());

            facade.joinGame(Repl.clientAuthData, playerData);

            doublePrintBoard();
            return "Joined game number: \"" + gameNumber + "\"";
        }
        throw new ResponseException(400, "Expected: <game_number> <white/black>");
    }

    private void doublePrintBoard() {
        ChessBoard board = new ChessBoard();
        board.resetBoard();

        BoardPrinter bp = new BoardPrinter(board);
        bp.print(ChessGame.TeamColor.WHITE);
        bp.print(ChessGame.TeamColor.BLACK);
    }

    private boolean isValidColor(String color) {
        return color.equals("white") || color.equals("black");
    }

    private boolean isValidGameNumber(String gameNumber) {
        int number = Integer.parseInt(gameNumber);
        return 0 < number && number < Repl.games.size() + INDEX_MODIFIER;
    }

    public String observeGame(String[] params) {
        if (params.length == 1 && isValidGameNumber(params[0])) {
            int gameNumber = Integer.parseInt(params[0]);

            doublePrintBoard();
            return "Observing game number: \"" + gameNumber + "\"";
        }
        throw new ResponseException(400, "Expected: <game_number>");
    }

    public String help() {
        return """
            "logout" <- To logout
            "create_game <game_name>" <- To create a game
            "list_games" <- To list games
            "play_game <game_number> <white/black>" <- To join a game
            "observe_game <game_number>" <- To observe a game
            "quit" <- To quit""";
    }
}