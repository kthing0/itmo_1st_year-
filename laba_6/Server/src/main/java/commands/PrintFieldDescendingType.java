package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;

public class PrintFieldDescendingType implements Command{
    private final CollectionManager collectionManager;
    public PrintFieldDescendingType(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(Request req) {
        Server.printMsg(req.client_key,collectionManager.print_field_descending_type());
    }
}
