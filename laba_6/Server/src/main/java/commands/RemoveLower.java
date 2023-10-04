package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;
public class RemoveLower implements Command{
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }
    @Override
    public void execute(Request req) {
        try{
            collectionManager.getCollection().entrySet().removeIf(entry -> entry.getKey().length() < req.args[0].length());
            Server.printMsg(req.client_key, "Удаление прошло успешно");
        }catch (ArrayIndexOutOfBoundsException e){
            Server.printMsg(req.client_key,"Использование команды:\n\t\"remove_lower [key]\" для удаления элементов меньше заданного");
        }
    }
}
