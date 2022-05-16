package com.company.common;

import java.io.Serializable;

public class Request implements Serializable {
    private CommandList command;
    private Pack argument;
    public Request(CommandList command, Pack argument){
        this.command = command;
        this.argument = argument;
    }
    public CommandList getCommand(){
        return command;
    }

    public Pack getArgument() {
        return argument;
    }
}
