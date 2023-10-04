package utils;

import java.io.Serializable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Client.connect();
        try{
            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.print("guest@lab6:~$ ");
                String line = sc.nextLine();
                if(client.isConnected()) {

                    if(line.split(" ")[0].equals("execute_script")){
                        ExecuteScript executeScript = new ExecuteScript();
                        executeScript.execute(line);
                    }
                    Client.sendCommand(line);
                    Response response = Client.receive();
                    if (response.command != null) {
                        if (response.command.equals("send") && response.toInput != null) {
                            Serializable obj = (Serializable) response.toInput
                                    .getMethod("getData")
                                    .invoke(null);
                            Client.sendCommandObject(line, obj);

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
