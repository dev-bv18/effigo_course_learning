package com.example.demo.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameRunnerConfiguration {
    @Bean
    public GamingConsole game(){
        var game=new MarioGame();
        return game;
    }
    @Bean
    public GameRunner gameRunner(GamingConsole game){
        var gameRunner= new GameRunner(game);
        return gameRunner;
    }
}
