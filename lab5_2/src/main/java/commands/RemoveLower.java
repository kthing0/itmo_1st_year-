package commands;

import collection.CollectionManager;
import utils.Printer;

public class RemoveLower implements Command{
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }
    @Override
    public void execute(String[] args) {
        try{
            this.collectionManager.remove_lower(args[0]);
        }catch (ArrayIndexOutOfBoundsException e){
            Printer.printErr("Использование команды:\n\t\"remove_lower [key]\" для удаления элементов меньше заданного");
        }
    }
}
