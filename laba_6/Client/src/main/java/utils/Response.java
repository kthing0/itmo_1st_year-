package utils;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -8922377290647029870L;
    public String msg;
    public Class<? extends Serializable> toInput;
    public String command;
}
