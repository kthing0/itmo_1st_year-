package commands;

import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;

public class FilterStartsWithName implements Command{
    private final CollectionManager collectionManager;

    public FilterStartsWithName(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(Request req) {
        try {
            StringBuilder res = new StringBuilder();
            collectionManager.getCollection().entrySet().stream()
                    .filter(entry -> entry.getValue().getName().startsWith(req.args[0]))
                    .peek(entry -> res.append(entry.getValue()).append("\n"))
                    .count();
            Server.printMsg(req.client_key, String.valueOf(res));
        }catch (ArrayIndexOutOfBoundsException e) {
            Server.printMsg(req.client_key, "Использование команды:\n\t\"filter_starts_with_name [name]\" для поиска элементов по заданной подстроке");
        }
    }
}
