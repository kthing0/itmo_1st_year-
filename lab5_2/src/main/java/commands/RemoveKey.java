package commands;

import collection.CollectionManager;
import utils.Printer;

public class RemoveKey implements Command{
    private final CollectionManager collectionManager;

    public RemoveKey(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(String[] args) {
        try{
            this.collectionManager.remove_key(args[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            Printer.printErr("Использование команды:\n\t\"remove_key [key]\" для удаления элемента по заданному ключу");
        }
    }
}
