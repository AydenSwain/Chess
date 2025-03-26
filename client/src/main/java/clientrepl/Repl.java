package clientrepl;

import clienttoserver.ServerFacade;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    public static Client client;
    public static AuthData clientAuthData;
    public static ArrayList<GameData> games;

    private ServerFacade facade;
    private Scanner scanner = new Scanner(System.in);

    public Repl(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        client = new PreLoginClient(facade);
    }

    public void run() {
        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println("Welcome to Chess!!!");
        System.out.println("Type \"help\" for help!");
        System.out.print(RESET_TEXT_COLOR);

        String result = "";
        while (!result.equals("quit")) {
            String response = getResponse();

            try {
                result = client.eval(response);
                System.out.println(SET_TEXT_COLOR_BLUE + result + RESET_TEXT_COLOR);

            } catch (Throwable e) {
                String message = e.toString();
                System.out.println(SET_TEXT_COLOR_RED + message + RESET_TEXT_COLOR);
            }
        }

        System.out.print(SET_TEXT_COLOR_MAGENTA);
        System.out.println("Bye!");
        System.out.print(RESET_TEXT_COLOR);
    }

    private String getResponse() {
        System.out.print(">>> " + SET_TEXT_COLOR_GREEN);
        String response = scanner.nextLine();
        System.out.print(RESET_TEXT_COLOR);
        return response;
    }
}
