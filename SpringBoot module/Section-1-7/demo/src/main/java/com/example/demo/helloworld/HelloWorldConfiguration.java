package com.example.demo.helloworld;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

record Person(String name,int age,Address address){};
record Address(String firstLine,String city){};

@Configuration
public class HelloWorldConfiguration {
@Bean
public String name(){
    return "Baibhab";
}
@Bean
public int age(){
    return 21;
}
@Bean

    public Person person(){
    return new Person("Baibhab",22,new Address("Main Street","London"));
}
@Bean
@Primary
public Person person2MethodCall(){
    return new Person(name(),age(),address());
}
@Bean
public Person person3Parameters(String name,int age,@Qualifier("address3qualifier") Address address3){
    return new Person(name,age,address3);
}
@Bean(name="address2")
    public Address address(){
    return new Address("C/123","Rourkela");
}
    @Bean(name="address3")
    @Qualifier("address3qualifier")
    public Address address3(){
        return new Address("C/100","Rourkela");
    }
}
