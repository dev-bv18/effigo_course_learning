package com.example.demo;

import com.example.demo.game.GameRunner;
import com.example.demo.game.PacMan;

public class App01GamingBasicJava {
    public static void main(String [] args)
    {
        //var game= new MarioGame();
        //var game=new SuperContra();
        var game=new PacMan();
        var gameRunner= new GameRunner(game);
        gameRunner.run();

    }
}
