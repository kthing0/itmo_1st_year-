package utils;

public class Printer {
    private Printer(){}
    public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[91m";
    public static final String GREEN  = "\u001B[92m";

    public static void printErr(Object msg){
        System.out.println(RED + "Ошибка:\n" + msg + RESET);
    }
    public static void print(Object msg){
        System.out.println(msg);
    }

    public static void printSuccess(Object msg){
        System.out.println(GREEN + msg + RESET);
    }
}

