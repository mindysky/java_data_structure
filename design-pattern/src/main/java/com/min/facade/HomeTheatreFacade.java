package com.min.facade;

public class HomeTheatreFacade {
    //定义子系统的对象
    private TheatreLight theatreLight;
    private Popcorn popcorn;
    private Projector projector;
    private Stereo stereo;
    private Screen screen;
    private DVDPlayer dvdPlayer;

    public HomeTheatreFacade() {
        super();
        this.theatreLight = TheatreLight.getInstance();
        this.popcorn = Popcorn.getInstance();
        this.projector = Projector.getInstance();
        this.stereo = Stereo.getInstance();
        this.screen = Screen.getInstance();
        this.dvdPlayer = DVDPlayer.getInstance();
    }

    public void ready() {
        popcorn.on();
        popcorn.pop();
        screen.down();
        projector.on();
        stereo.on();
        dvdPlayer.on();
        theatreLight.dim();
    }

    public void play(){
        dvdPlayer.play();
    }

    public void pause(){
        dvdPlayer.pause();
    }

    public void end(){
        popcorn.off();
        theatreLight.bright();
        screen.up();
        projector.off();
        stereo.off();
        dvdPlayer.off();
    }
}
