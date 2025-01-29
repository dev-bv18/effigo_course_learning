package com.example.demo;

import com.example.demo.game.GameRunner;
import com.example.demo.game.GamingConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.demo.game")
public class GamingAppSpringBeansApplication {


    public static void main(String [] args)
    {
        try(var context=new AnnotationConfigApplicationContext(GamingAppSpringBeansApplication.class)){
            context.getBean(GamingConsole.class).up();
            context.getBean(GameRunner.class).run();
        }

    }
}
