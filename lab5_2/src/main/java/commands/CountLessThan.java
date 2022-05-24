package commands;

import collection.CollectionManager;
import utils.Printer;

public class CountLessThan implements Command {
    private final CollectionManager collectionManager;

    public CountLessThan(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            collectionManager.count_less_than_fuel_type(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Printer.printErr("Использование команды:\n\t\"count_less_than_fuel_type [fuelType]\" вывода количества элементов меньше заданного");
        }
    }
}
