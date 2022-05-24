package commands;

import collection.CollectionManager;
import utils.Printer;

public class ReplaceIfLower implements Command{
    private final CollectionManager collectionManager;
    public ReplaceIfLower(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args) {
        try {
            collectionManager.replace_if_lowe(args[0]);
        }catch (ArrayIndexOutOfBoundsException e){
            Printer.printErr("Использование команды:\n\t\"replace_if_lowe [key]\" для замены значения элемента, если оно больше заданного");
        }
    }
}
