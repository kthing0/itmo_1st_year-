package server_utlis;

import commands.*;
import utils.Request;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CommandManager {
    public static final HashMap<String, Command> commands = new HashMap<>();
    public CommandManager(CollectionManager collectionManager){
        commands.put("help", new Help());

        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("exit", new Exit());
       // commands.put("get_id", new GetId(collectionManager));
        commands.put("print_field_descending_type", new PrintFieldDescendingType(collectionManager));
        commands.put("remove_key", new RemoveKey(collectionManager));
        commands.put("remove_lower", new RemoveLower(collectionManager));
     //   commands.put("save", new Save(collectionManager));
        commands.put("count_less_than_fuel_type", new CountLessThan(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithName(collectionManager));
      //  commands.put("execute_script", new ExecuteScript(collectionManager));
        commands.put("insert", new Insert(collectionManager));
        commands.put("replace_if_greater", new ReplaceIfGreater(collectionManager));
        commands.put("replace_if_lowe", new ReplaceIfLower(collectionManager));
        commands.put("update", new Update(collectionManager));
    }

    public void execute(Request request) {
        ThreadPoolExecutor fixedPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        if(fixedPool.getActiveCount() < 10) {
            fixedPool.execute(() -> {
                request.command = request.command.toLowerCase(Locale.ROOT);
                try {
                    if(request.command.equals("register")){
                        boolean result = AuthManager.register(request.login, request.password);
                        Server.printMsg(request.client_key, result ? "Вы успешно зарегестрировались" : "Error");
                        return;
                    }
                    if(request.command.equals("login")){
                        boolean result = AuthManager.checkLogin(request.login, request.password);
                        Server.printMsg(request.client_key, result ? "Вы успешно аутентифицировались" : "Error");
                        return;
                    }
                    if (!AuthManager.checkLogin(request.login, request.password)  && request.client_key != null) {
                        Server.printMsg(request.client_key, "Требуется аутенфикация");
                        return;
                    }

                    if (commands.containsKey(request.command)) {
                        commands.get(request.command).execute(request);
                    } else if (!request.command.equals("execute_script")) {
                        Server.printMsg(request.client_key, "Команда введена неправильно или не существует");
                    } else {
                        Server.printMsg(request.client_key, " ");
                    }
                } catch (NullPointerException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}

