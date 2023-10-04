package server_utlis;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Request;
import utils.Response;

public class Server {

    private static final int PORT = 31337;

    private static final int BUF_SIZE = 1024 * 1024;

    private Selector selector;

    private InputStream inputStream;

    private ByteBuffer buffer;

    private ServerSocketChannel serverSocketChannel;

    private static final Logger logger = LogManager.getLogger();


    public Server(){
        try{
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //
            logger.info("Сервер был запущен");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при инициализации сервера");
        }
    }

    public void register(){
        try{
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            logger.info("Канал успешно зарегестрирован");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Request readRequest(SelectionKey key){
        Request request = null;
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        try{
            socketChannel.read(buffer);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            request = (Request) ois.readObject();
            logger.info("Получен запрос от " + socketChannel.getRemoteAddress() + ": " + request.command);
        } catch (IOException e) {
            key.cancel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    public static void send(SelectionKey key, Response response){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));
            logger.info("Отправлен ответ на " + socketChannel.getRemoteAddress());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Selector getSelector(){return selector;}

    public static void printMsg(SelectionKey key, String msg){
        if(key == null) {
            System.out.println(msg);
            return;
        }
        Response response = new Response();
        response.msg = msg;
        Server.send(key, response);
    }


    public static void reqObj(SelectionKey key, Class<? extends Serializable> clazz){
        Response response = new Response();
        response.toInput = clazz;
        response.command = "send";
        Server.send(key, response);
    }
}
