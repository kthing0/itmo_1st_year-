package commands;

import collection.CollectionManager;
import utils.Printer;
import validation.Validator;

public class Save implements Command{
    private final CollectionManager collectionManager;
    public Save(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            Validator.checkPermissions(args[0]);
            collectionManager.save(args[0]);
            Printer.printSuccess("Коллекция была соханена в -> " + args[0]);
        }catch (ArrayIndexOutOfBoundsException e){
            collectionManager.save(collectionManager.getFileName());
            Printer.printSuccess("Коллекция была обновлена в исходном файле");
        }
    }
}
