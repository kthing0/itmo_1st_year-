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
        try {
            if (req.obj == null) {
                Server.reqObj(req.client_key, Vehicle.class);
                return;
            }
            Vehicle vehicle = (Vehicle) req.obj;
            if(collectionManager.insert(req.args[0], vehicle)) {
                Server.printMsg(req.client_key, "Элемент успешно добавлен в коллекцию\n");
            }else Server.printMsg(req.client_key, "Элемент с таким ключом уже существует");
        }catch (ArrayIndexOutOfBoundsException e){
            Server.printMsg(req.client_key,"Использование команды:\n\t\"insert null {element}\" для добавления нового элемента\n");
        }

    }
}
