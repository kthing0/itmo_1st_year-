package utils;

import java.io.Serializable;
import java.net.Socket;
import java.nio.channels.SelectionKey;

public class Request implements Serializable {
    private static final long serialVersionUID = -8468873788533377442L;
    public String command;
    public String[] args;
    public Serializable obj;
    public SelectionKey client_key;
    public String login;
    public String password;

    public Request(String command, String[] args, String login, String password, Serializable obj) {
        this.command = command;
        this.args = args;
        this.login = login;
        this.password = password;
        this.obj = obj;
    }

    public Request(String command, String[] args, String login, String password) {
        this.command = command;
        this.args = args;
        this.login = login;
        this.password = password;
    }

}
