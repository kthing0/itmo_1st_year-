package commands;



import server_utlis.CollectionManager;
import server_utlis.Server;
import utils.Request;


public class CountLessThan implements Command {
    private final CollectionManager collectionManager;

    public CountLessThan(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(Request req) {
        Server.printMsg(req.client_key,this.collectionManager.count_less_than_fuel_type(req.args[0]));
    }
}
