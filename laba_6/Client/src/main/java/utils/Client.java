package utils;

import server_utlis.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static final int PORT = 31337;
    private static final int BUF_SIZE = 1024*1024;

    private static Socket socket;

    private static InputStream inputStream;

    private static OutputStream outputStream;

    private static InetAddress address;

    private static Client client;

    public Client(){
        this.socket = new Socket();
    }


    public static Client getClient(){
        return Client.client;
    }

    public static boolean connect(){
        try {
            address = InetAddress.getLoopbackAddress();
            socket = new Socket(address, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            System.out.println("Подключение успешно");
            return true;
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
            return false;
        }
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public void close(){
        try{
            outputStream.close();
            inputStream.close();
            socket.close();
        }catch (IOException e){
            System.out.println("Не удалось отключиться");
        }
    }

    public static boolean sendCommandObject(String line, Serializable obj){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos))
        {
            String[] words = line.trim().split("\\s+");
            Request request = new Request(words[0], Arrays.copyOfRange(words, 1, words.length), obj);
            if (request.command.equals("exit")) {
                System.exit(0);
            }
            if (request.command.equals("save")) {
                request.command = "0__0"; // убираем команду с клиента
            }
            oos.writeObject(request);
            outputStream.write(baos.toByteArray());
            return true;
        } catch (IOException e) {
            System.out.println("Не удалось отправить запрос попытка переподключения...\n");
            return false;
        }
    }

    public static void sendCommand(String command){
        if(!Client.sendCommandObject(command, null)){
            connect();
            Client.sendCommandObject(command, null);
        }
    }

    public static Response receive(){
        Response response = new Response();
        final ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        try{
            inputStream.read(buffer.array());
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream input = new ObjectInputStream(bais);
            response = (Response) input.readObject();
        } catch (IOException e) {
            System.out.println("Ошибка при получении ответа");
        } catch (ClassNotFoundException e) {
            System.out.println("Некорректные данные с сервера");
        }
        return response;
    }


    public static int getId(){ // Костыль для получения айди T_T
        Client.sendCommand("get_id");
        Response resp = receive();
        return Integer.parseInt(resp.msg);
    }

}
