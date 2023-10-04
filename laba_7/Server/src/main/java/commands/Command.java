package commands;

import utils.Request;

public interface Command {
    void execute(Request request);
}
