package commands;

import collection.CollectionManager;
import collection.Vehicle;
import utils.CommandManager;
import utils.Printer;

import java.util.Scanner;

public class Insert implements Command {
    private final CollectionManager collectionManager;

    public Insert(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            Vehicle vehicle = Vehicle.addVehicle(collectionManager);
            collectionManager.insert(args[0], vehicle);
        }catch (ArrayIndexOutOfBoundsException e){
        Printer.printErr("Использование команды:\n\t\"insert null {element}\" для добавления нового элемента");
    }
    }
}
