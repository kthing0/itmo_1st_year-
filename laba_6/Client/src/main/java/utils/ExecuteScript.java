package utils;
import utils.Client;


import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SelectionKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExecuteScript{
    private final HashMap<String, Boolean> script_paths = new HashMap<>();

    public int script_length;

    public ExecuteScript(){};

    public void execute(String com) {
        try{
            List<String> commands = Files.readAllLines(Paths.get(com.split(" ")[1]));
            if (script_paths.containsKey(com.split(" ")[0])){
                throw new Exception("Внутри скрипта обнаружен его вызов, проверьте корректность данных");
            }

            for(String script : commands){
                if (commands.contains("execute_script " + com.split(" ")[0])) {
                    throw new Exception("Внутри скрипта обнаружена команда его вызова, проверьте корректность данных.");
                }
                script_paths.put(com.split(" ")[0], true);
                Client.sendCommand(script);
                Response response = Client.receive();
                if (response.command != null) {
                    if (response.command.equals("send") && response.toInput != null) {
                        Serializable obj = (Serializable) response.toInput
                                .getMethod("getData")
                                .invoke(null);
                        Client.sendCommandObject(script, obj);

                        Response response2 = Client.receive();
                        System.out.print(response2.msg);
                    }
                }
                if(response.msg != null){
                    System.out.println(response.msg);
                }
            }
            script_paths.clear();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}



