package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;
import data.Vehicle;

public class Update implements Command{
    private final CollectionManager collectionManager;
    public Update(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(Request req) {
        try {
            if (req.obj == null) {
                Server.reqObj(req.client_key, Vehicle.class);
                return;
            }
            int id = Integer.parseInt(req.args[0]);
            Vehicle vehicle = (Vehicle) req.obj;
            if(this.collectionManager.update(id, vehicle, req.login)){
                Server.printMsg(req.client_key, "Элемент успешно обновлен");
            }else{
                Server.printMsg(req.client_key, "Элемент с данным id не найден");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            Server.printMsg(req.client_key,"Использование команды:\n\t\"update [id] {element}\" для обновления элемента по его id");
        }catch (NumberFormatException e){
            Server.printMsg(req.client_key,"ID должен быть числом");
        }
    }
}
