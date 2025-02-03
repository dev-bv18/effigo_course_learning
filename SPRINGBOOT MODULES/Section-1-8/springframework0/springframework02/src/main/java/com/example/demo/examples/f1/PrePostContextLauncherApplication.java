package com.example.demo.examples.f1;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import jakarta.annotation.PreDestroy;
import java.util.Arrays;

@Component
class SomeClass{
    private SomeDependency someDependency;
    public SomeClass(SomeDependency someDependency) {
        super();
        this.someDependency = someDependency;
        System.out.println("All dependencies are ready!");
    }
    @PostConstruct
    public void intitialize(){
        someDependency.getReady();
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleanup");
    }
}

@Component
class SomeDependency{
public void getReady(){
    System.out.println("Some logic using SomeDependency");
}
}
@Configuration
@ComponentScan
public class PrePostContextLauncherApplication {


    public static void main(String [] args)
    {
        try(var context=new AnnotationConfigApplicationContext(PrePostContextLauncherApplication.class)){

            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
           }

    }
}
