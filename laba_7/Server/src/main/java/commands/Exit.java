package commands;

import utils.Request;

public class Exit implements Command{

    @Override
    public void execute(Request req) {
        System.out.println("Завершение работы...");
        System.exit(0);
    }
}
