package commands;

import collection.CollectionManager;
import utils.Printer;

public class FilterStartsWithName implements Command{
    private final CollectionManager collectionManager;

    public FilterStartsWithName(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(String[] args) {
        try {
            collectionManager.filter_starts_with_name(args[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            Printer.printErr("Использование команды:\n\t\"filter_starts_with_name [name]\" для поиска элементов по заданной подстроке");
        }
    }
}
