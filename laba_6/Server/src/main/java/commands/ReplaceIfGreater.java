package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;

public class ReplaceIfGreater implements Command{
    private final CollectionManager collectionManager;
    public ReplaceIfGreater(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(Request req) {
        try{
            if(collectionManager.getCollection().get(req.args[0]).getEnginePower() <= Long.parseLong(req.args[1])){
                collectionManager.getCollection().get(req.args[0]).setEnginePower(Long.parseLong(req.args[1]));
                Server.printMsg(req.client_key, "Значение успешно обновлено");
            }else{
                Server.printMsg(req.client_key, "Значение элемента больше переданного");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            Server.printMsg(req.client_key,"Использование команды:\n\t\"replace_if_greater [key]\" для замены значения элемента, если оно меньше заданного");
        }
    }
}
