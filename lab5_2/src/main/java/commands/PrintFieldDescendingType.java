package commands;

import collection.CollectionManager;

public class PrintFieldDescendingType implements Command{
    private final CollectionManager collectionManager;
    public PrintFieldDescendingType(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.print_field_descending_type();
    }
}
