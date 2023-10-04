package utils;

import java.io.Serializable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Client.connect();
        try{
            System.out.print("> Do u want register, otherwise login? [y/n] ");
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.nextLine();
            String res = "Error";
            String login = "", password ="";

            while (res.equals("Error")) {
                if (!ans.equals("y")) {
                    System.out.println("Authentication");
                }else System.out.println("Registration");
                System.out.print("Login: ");
                login = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();
                Client.sendCommand(ans.equals("y") ? "register" : "login", login, password);
                res = Client.receive().msg;
            }
            while(true) {
                System.out.print(login + "@lab7:~$ ");
                String line = scanner.nextLine();
                if(client.isConnected()) {

                    if(line.split(" ")[0].equals("execute_script")){
                        ExecuteScript executeScript = new ExecuteScript();
                        executeScript.execute(line, login, password);
                    }
                    Client.sendCommand(line, login, password);
                    Response response = Client.receive();
                    if (response.command != null) {
                        if (response.command.equals("send") && response.toInput != null) {
                            Serializable obj = (Serializable) response.toInput
                                    .getMethod("getData")
                                    .invoke(null);
                            Client.sendCommandObject(line, login, password, obj);

                            Response response2 = Client.receive();
                            System.out.print(response2.msg);
                        }
                    }
                    if(response.msg != null){
                            System.out.println(response.msg);
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
