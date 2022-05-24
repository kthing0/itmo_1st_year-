package commands;

import collection.CollectionManager;

public class Show implements Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager currentCollection) {
        this.collectionManager = currentCollection;
    }

    @Override
    public void execute(String[] args) {
        collectionManager.show();
    }
}
