package com.min.command;

public class Client {
    public static void main(String[] args) {
        LightReceiver lightReceiver = new LightReceiver();
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);

        RemoteController remoteController = new RemoteController();
        remoteController.setCommand(0,lightOnCommand,lightOffCommand);

        remoteController.offButtonWasPushed(0);
        remoteController.undoButtonWasPushed(0);


        TVReceiver tvReceiver = new TVReceiver();
        TVOnCommand tvOnCommand = new TVOnCommand(tvReceiver);
        TVOffCommand tvOffCommand = new TVOffCommand(tvReceiver);

        remoteController.setCommand(1,tvOnCommand,tvOffCommand);

        remoteController.offButtonWasPushed(1);
        remoteController.onButtonWasPushed(1);

    }
}
