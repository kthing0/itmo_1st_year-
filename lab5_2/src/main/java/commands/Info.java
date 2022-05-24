package commands;

import collection.CollectionManager;

public class Info implements Command{
    private final CollectionManager collectionManager;

    public Info(CollectionManager currentCollection){
        this.collectionManager = currentCollection;
    }
    @Override
    public void execute(String[] args) {
        collectionManager.info();
    }
}
