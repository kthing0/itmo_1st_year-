package utils;

import collection.CollectionManager;
import commands.*;

import java.util.*;

public class CommandManager {
    public static final HashMap<String, Command> commands = new HashMap<>();
    public CommandManager(CollectionManager collectionManager){
        commands.put("help", new Help());
        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("exit", new Exit());
        commands.put("print_field_descending_type", new PrintFieldDescendingType(collectionManager));
        commands.put("remove_key", new RemoveKey(collectionManager));
        commands.put("remove_lower", new RemoveLower(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("count_less_than_fuel_type", new CountLessThan(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithName(collectionManager));
        commands.put("execute_script", new ExecuteScript(collectionManager));
        commands.put("insert", new Insert(collectionManager));
        commands.put("replace_if_greater", new ReplaceIfGreater(collectionManager));
        commands.put("replace_if_lowe", new ReplaceIfLower(collectionManager));
        commands.put("update", new Update(collectionManager));
    }

    public void execute(String command) {
        String[] splittedCommand = command.trim().split(" ");
        String currentCommand = splittedCommand[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
        if(commands.containsKey(currentCommand)){
            commands.get(currentCommand).execute(args);
        }
        else if(currentCommand.equals("")){
            return;
        }
        else{
            Printer.printErr("Команда не найдена.\nДля справки воспользуйтесь \"help\"");
        }
    }
}
