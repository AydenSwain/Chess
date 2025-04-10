package clientrepl;

import HTTPFacade.ServerFacade;
import websocket.*;

public class InGameClient implements Client {
    private final ServerFacade facade;

    private WebSocketFacade loadGameFacade = new WebSocketFacade(new LoadGameHandler());
    private WebSocketFacade notificationFacade = new WebSocketFacade(new NotificationHandler());
    private WebSocketFacade errorFacade = new WebSocketFacade(new ErrorHandler());

    public InGameClient(ServerFacade facade) {
        this.facade = facade;
        String url = facade.getServerUrl();

        loadGameFacade = new WebSocketFacade(url, new LoadGameHandler());
        notificationFacade = new WebSocketFacade(url, new NotificationHandler());
        errorFacade = new WebSocketFacade(url, new ErrorHandler());
    }

    @Override
    public String eval(String input) {
        return "";
    }
}
