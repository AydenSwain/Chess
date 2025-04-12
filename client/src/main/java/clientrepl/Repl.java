package clientrepl;

import httpfacade.ServerFacade;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    public static Client client;
    public static AuthData clientAuthData;
    public static ArrayList<GameData> games;

    private static final String CLIENT_COLOR = SET_TEXT_COLOR_MAGENTA;
    public static final String HELP_MESSAGE = CLIENT_COLOR + "Type \"help\" for help!" + RESET_TEXT_COLOR;

    private ServerFacade facade;

    public Repl(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        client = new PreLoginClient(facade);
    }

    public void run() {
        System.out.print(CLIENT_COLOR);
        System.out.println("Welcome to Chess!!!");
        System.out.println("Type \"help\" for help!");
        System.out.print(RESET_TEXT_COLOR);

        String result = "result";
        while (!result.equals("")) {
            String response = getResponse();

            try {
                result = client.eval(response);

                if (result.startsWith("Error: ")) {
                    printError(result);

                } else if (result.startsWith("Expected: ")) {
                    printClientMessage(result);

                } else {
                    printResponse(result);
                }

            } catch (Throwable e) {
                String message = e.toString();
                printError("UNEXPECTED ERROR:\n" + message);
            }
        }

        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println("Bye!");
        System.out.print(RESET_TEXT_COLOR);
    }

    private void printError(String response) {
        System.out.println(SET_TEXT_COLOR_RED + response + RESET_TEXT_COLOR);
    }

    public static void printClientMessage(String response) {
        System.out.println(CLIENT_COLOR + response + RESET_TEXT_COLOR);
    }

    private void printResponse(String response) {
        System.out.println(SET_TEXT_COLOR_BLUE + response + RESET_TEXT_COLOR);
    }

    public static String getResponse() {
        System.out.print(">>> " + SET_TEXT_COLOR_GREEN);
        String response = new Scanner(System.in).nextLine();
        System.out.print(RESET_TEXT_COLOR);
        return response;
    }
}
