package server_utlis;

import utils.Request;

import java.io.IOException;
import java.nio.channels.SelectionKey;

import java.util.Scanner;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(collectionManager);
        try {
            collectionManager.parseJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Server server = new Server();
        System.out.println("Server is now running... ");
        System.out.print("> ");

        while(true){

            if(System.in.available() > 0){
                try{
                    String serverSideInput[] = new Scanner(System.in).nextLine().split(" ");
                    commandManager.execute(new Request(serverSideInput[0], Arrays.copyOfRange(serverSideInput,1, serverSideInput.length)));
                    System.out.print("> ");
                }catch (NullPointerException e){
                    System.out.println(e.toString());
                }
            }

            server.getSelector().select(3000);
            Set<SelectionKey> keys = server.getSelector().selectedKeys();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    server.register();
//                    System.out.println(key);
                }else if(key.isReadable()){
                    Request request = server.readRequest(key);
                    if(request != null){
                        request.client_key = key;
                        commandManager.execute(request);
                    }
                }
            }
        }
    }

}
