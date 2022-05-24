package commands;

import collection.CollectionManager;
import utils.Printer;

public class Clear implements Command{
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.clear();
        Printer.printSuccess("Коллекция была успешно очищена.");
    }
}
