package com.min.command;

public class TVOffCommand implements Command{
    TVReceiver TV;

    public TVOffCommand(TVReceiver TV) {
        this.TV = TV;
    }

    @Override
    public void execute() {
        TV.off();
    }

    @Override
    public void undo() {
        TV.on();
    }
}
