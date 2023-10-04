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

    public Request(String command, String[] args, Serializable obj) {
        this.command = command;
        this.args = args;
        this.obj = obj;
    }

    public Request(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

}
