package com.example.demo.helloworld;

import com.example.demo.game.GameRunner;
import com.example.demo.game.GameRunnerConfiguration;
import com.example.demo.game.GamingConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App03GamingSpringBeans {
    public static void main(String [] args)
    {
        var context=new AnnotationConfigApplicationContext(GameRunnerConfiguration.class);
        context.getBean(GamingConsole.class).up();
        context.getBean(GameRunner.class).run();
        }
         }

