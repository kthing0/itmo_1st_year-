package commands;

import server_utlis.CollectionManager;
import server_utlis.CommandManager;
import server_utlis.Server;
import utils.Request;
import utils.Response;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
DEPRECATED
 */

public class ExecuteScript implements Command{
    public final CollectionManager collectionManager;
    private final HashMap<String, Boolean> script_paths = new HashMap<>();

    public int script_length;

    public ExecuteScript(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }



    @Override
    public void execute(Request req) {
        try{
            List<String> commands = Files.readAllLines(Paths.get(req.args[0]));
            if (script_paths.containsKey(req.args[0])){
                throw new Exception("Внутри скрипта обнаружен его вызов, проверьте корректность данных");
            }

            for(String script : commands){
                if (commands.contains("execute_script " + req.args[0])) {
                    throw new Exception("Внутри скрипта обнаружена команда его вызова, проверьте корректность данных.");
                }
                String[] splittedCommand = script.trim().split(" ");
                String command = splittedCommand[0];
                String[] args = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
                script_paths.put(req.args[0], true);
                Request temp = new Request(command, args);
                CommandManager.commands.get(command).execute(temp);
            }
            script_paths.clear();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            Server.printMsg(req.client_key, e.getMessage());
        }finally {
            Server.printMsg(req.client_key, "Скрипт был успешно выполнен на сервере");
        }
    }
}



