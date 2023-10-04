package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;
public class Info implements Command{
    private final CollectionManager collectionManager;

    public Info(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }
    @Override
    public void execute(Request req) {
        Server.printMsg(req.client_key, this.collectionManager.info());
    }
}
