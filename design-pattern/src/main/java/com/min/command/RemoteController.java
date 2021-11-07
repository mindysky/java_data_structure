package com.min.command;

public class RemoteController {
    Command[] onCommands;
    Command[] offCommands;

    //execute undo
    Command undoCommand;


    public RemoteController() {
        this.onCommands = new Command[5];
        this.offCommands = new Command[5];

        for (int i = 0; i < 5; i++) {
            onCommands[i] = new NoCommand();
            offCommands[i] = new NoCommand();
        }
    }

    public void setCommand(int no, Command onCommand, Command offCommand){
        onCommands[no] = onCommand;
        offCommands[no] = offCommand;
    }

    public void onButtonWasPushed(int no){
        onCommands[no].execute();
        undoCommand =  onCommands[no];
    }

    public void offButtonWasPushed(int no) {
        offCommands[no].execute();
        undoCommand = offCommands[no];
    }

    public void undoButtonWasPushed(int no) {
        undoCommand.undo();
    }
}
