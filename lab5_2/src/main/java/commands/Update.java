package commands;

import collection.CollectionManager;
import collection.Vehicle;
import utils.Printer;

public class Update implements Command{
    private final CollectionManager collectionManager;
    public Update(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            Vehicle vehicle = Vehicle.addVehicle(collectionManager);
            collectionManager.update(id, vehicle);
        }catch (ArrayIndexOutOfBoundsException e) {
            Printer.printErr("Использование команды:\n\t\"update [id] {element}\" для обновления элемента по его id");
        }
    }
}
