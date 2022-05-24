package commands;

import collection.CollectionManager;
import utils.Printer;

public class ReplaceIfGreater implements Command{
    private final CollectionManager collectionManager;
    public ReplaceIfGreater(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args) {
        try{
            collectionManager.replace_if_greater(args[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            Printer.printErr("Использование команды:\n\t\"replace_if_greater [key]\" для замены значения элемента, если оно меньше заданного");
        }
    }
}
