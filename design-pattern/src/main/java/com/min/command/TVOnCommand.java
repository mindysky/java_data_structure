package com.min.command;

public class TVOnCommand implements Command {
    TVReceiver TV;

    public TVOnCommand(TVReceiver TV) {
        this.TV = TV;
    }

    @Override
    public void execute() {
        TV.on();
    }

    @Override
    public void undo() {
        TV.off();
    }
}
