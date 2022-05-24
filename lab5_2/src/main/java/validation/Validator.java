package validation;

import collection.CollectionManager;
import collection.Vehicle;
import utils.Printer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {
    private static boolean isReadble(Path path){
        return Files.isReadable(path);
    }
    private static boolean isWritable(Path path) {
        return Files.isWritable(path);
    }
    private static boolean isExecutable(Path path) {
        return Files.isExecutable(path);
    }
    public static boolean checkPermissions(String fileName){

        if(!isReadble(new File(fileName).toPath())){
            Printer.printErr("Не хватает прав для чтения файла");
            return false;
        }else if(!isWritable(new File(fileName).toPath())){
            Printer.printErr("Не хватает прав для записи в файл");
            return false;
        }else if(!isExecutable(new File(fileName).toPath())) {
            Printer.printErr("Не хватает прав для выполнения файла");
            return false;
        }
        return true;
    }
    public static boolean validateFields(Vehicle collectionManager){
        try{
        for(String key : collectionManager.keySet()) {
            if (
                    collectionManager.get(key).getName().isEmpty() ||
                    collectionManager.get(key).getCoordinates().toString().isEmpty() ||
                    collectionManager.get(key).getCoordinates().getX() == null ||
                    collectionManager.get(key).getCoordinates().getY() < -61 ||
                    collectionManager.get(key).getCoordinates().getY() == null ||
                    collectionManager.get(key).getEnginePower() < 0 ||
                    collectionManager.get(key).getEnginePower() == null)
            {
                throw new Exception("Не удалось импортировать коллекцию. Проверьте корректность данных в файле и попробуйте снова.");
            }
        }
        }catch (Exception e){
            Printer.printErr(e.getMessage());
            System.exit(0);
        }
        return true;
    }
}