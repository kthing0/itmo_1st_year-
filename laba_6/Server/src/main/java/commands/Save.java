package commands;


import server_utlis.CollectionManager;
import utils.Request;

public class Save implements Command{
    private final CollectionManager collectionManager;
    public Save(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(Request req) {
        try {
            collectionManager.save(req.args[0]);
            System.out.println("Коллекция была соханена в -> " + req.args[0]);
        }catch (ArrayIndexOutOfBoundsException e){
            collectionManager.save(collectionManager.getFileName());
            System.out.println("Коллекция была обновлена в исходном файле");
        }
    }
}
