package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;
import data.Vehicle;

public class Insert implements Command {
    private final CollectionManager collectionManager;

    public Insert(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(Request req) {

            if (req.obj == null) {
                Server.reqObj(req.client_key, Vehicle.class);
                return;
            }
            Vehicle vehicle = (Vehicle) req.obj;
                Server.printMsg(req.client_key, collectionManager.insert(req.args[0], vehicle, req.login) ? "Элемент успешно добавлен в коллекцию\n" : "Не вышло добавить");


    }
}
