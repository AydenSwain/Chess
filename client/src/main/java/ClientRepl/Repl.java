package ClientRepl;

import ClientToServer.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private ServerFacade facade;
    private Client client;
    private Scanner scanner = new Scanner(System.in);

    public Repl(String serverUrl) {
        facade = new ServerFacade(serverUrl);
        client = new PreLoginClient(facade);
    }

    public void run() {
        System.out.println("Welcome to Chess!!!");
        System.out.println("Type \"help\" for help, or \"quit\" to exit the program.");

        String result = "";
        while (!result.equals("quit")) {
            String command = getCommand();

            try {
                result = client.eval(command);
                System.out.print(SET_TEXT_COLOR_BLUE + result + RESET_TEXT_COLOR);

            } catch (Throwable e) {
                String msg = e.toString();
                System.out.print(SET_TEXT_COLOR_RED + msg);
            }
        }
        System.out.println();
    }

    private String getCommand() {
        System.out.print("\n" + ">>> " + SET_TEXT_COLOR_GREEN);
        String command = scanner.nextLine();
        System.out.print(RESET_TEXT_COLOR);
        return command;
    }

}
