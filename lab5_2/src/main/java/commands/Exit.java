package commands;

import utils.Printer;

public class Exit implements Command{

    @Override
    public void execute(String[] args) {
        Printer.print("Завершение работы...");
        System.exit(0);

    }
}
