//package com.min.mediator;
//
//import com.sun.glass.ui.delegate.MenuItemDelegate;
//
//public class Alarm extends Colleague{
//
//    public Alarm(Mediator mediator,String name) {
//        super(mediator,name);
//        mediator.Register(name,this);
//    }
//
//    public void SendAlarm(int stateChange){
//        sendMessage(stateChange);
//    }
//
//    @Override
//    public void sendMessage(int stateChange) {
//        this.GetMediator().GetMessage(stateChange,this.name);
//    }
//}
