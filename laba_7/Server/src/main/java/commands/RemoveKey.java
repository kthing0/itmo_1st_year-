package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;
public class RemoveKey implements Command{
    private final CollectionManager collectionManager;

    public RemoveKey(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(Request req) {
        try{
            if(collectionManager.getCollection().containsKey(req.args[0])){
                Server.printMsg(req.client_key, this.collectionManager.removeKey(req.args[0], req.login) ? "Элемент успешно удален" : "Не удалось удалить");
            }
            else {
                Server.printMsg(req.client_key, "Элемент не найден");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            Server.printMsg(req.client_key,"Использование команды:\n\t\"remove_key [key]\" для удаления элемента по заданному ключу");
        }
    }
}
