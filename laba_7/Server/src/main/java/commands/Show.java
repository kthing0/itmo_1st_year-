package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;

import java.util.stream.Collectors;

public class Show implements Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager currentCollection) {
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(Request req) {
        String result = this.collectionManager.getCollection().keySet().stream()
                .map(key -> "[" + key + "] \n" + this.collectionManager.getCollection().get(key).toHumanString())
                .collect(Collectors.joining("\n\n"));

        Server.printMsg(req.client_key, result);
    }
}
