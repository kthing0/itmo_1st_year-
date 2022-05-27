import collection.CollectionManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import utils.CommandManager;
import utils.Printer;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CollectionManager cm = new CollectionManager();
        CommandManager cc = new CommandManager(cm);
        int attempts = 3;
        boolean done = false;

        while(!done || attempts == 0) {
            try {
                if(attempts == 0){Printer.print("У вас закончились попытки ввода.\nЗавершение работы..."); return;}
                if (attempts <= 2){Printer.print("У вас еще " + (attempts==2 ? attempts + " попытки импортировать коллекцию" : attempts + " попытка импортировать коллекцию"));}
                attempts--;

                cm.parseJson();
                done = true;
                Printer.print(Printer.GREEN + "Импорт коллекции прошел успешно." + Printer.RESET);

            } catch (NoSuchElementException e) {
                Printer.printErr("Файл коллекции пуст");
            } catch (NoSuchFileException e) {
                Printer.printErr("Файла с таким названием не существует");
            } catch (FileNotFoundException e) {
                Printer.printErr("Файл не найден, попробуйте снова.");
            } catch (InvalidFormatException e){
                Printer.printErr("Не удалось импортировать коллекцию. Проверьте корректность данных в файле и попробуйте снова.");
            }catch (InvalidPathException e){
                Printer.printErr("Путь указан некорректно");
            }catch (JsonParseException e){
                Printer.printErr("Не удалось прочитать коллекцию. Проверьте корректность данных в файле и попробуйте снова.");
            }catch (MismatchedInputException e){
                Printer.printErr("Неверный тип данных для импорта коллекции.");
            }
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                //Printer.print("Введите команду:");
                System.out.print(cm.getFileName() + ">");
                String line = sc.nextLine();
                cc.execute(line);

            } catch (NoSuchElementException e) {
                Printer.printErr("Недопустимый символ.\nПроверьте корректность ввода и повторите попытку.");
                System.exit(0);
            }
        }









    }
}
