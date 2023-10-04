package commands;


import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;

public class Clear implements Command{
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(Request req) {
        collectionManager.clear();

        Server.printMsg(req.client_key,"Коллекция была успешно очищена.");
    }
}
