package commands;

import collection.CollectionManager;
import collection.Vehicle;
import sun.misc.IOUtils;
import utils.CommandManager;
import utils.Printer;
import validation.Validator;

import javax.validation.Validation;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ExecuteScript implements Command{
    public final CollectionManager collectionManager;
    private final HashMap<String, Boolean> script_paths = new HashMap<>();


    public ExecuteScript(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }



    @Override
    public void execute(String[] arg) {
        try{
            Validator.checkPermissions(arg[0]);
            List<String> commands = Files.readAllLines(Paths.get(arg[0]));
            if (script_paths.containsKey(arg[0])){
                throw new Exception("Внутри скрипта обнаружен его вызов, проверьте корректность данных");
            }
            for(String script : commands){
                if (commands.contains("execute_script " + arg[0])) {
                    throw new Exception("Внутри скрипта обнаружена команда его вызова, проверьте корректность данных.");
                }
                String[] splittedCommand = script.trim().split(" ");
                String command = splittedCommand[0];
                String[] args = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
               // Printer.print(command);
                script_paths.put(arg[0], true);

                CommandManager.commands.get(command).execute(args);
            }
            script_paths.clear();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            Printer.printErr(e.getMessage());
        }

    }
}
