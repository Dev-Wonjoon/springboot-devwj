package net.likelion.bebc25.spring.aop.springaop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public Car car() {
        return new GasolineCar();
    }


    @Bean
    public Driver driver(Car car) {
        return new Driver(car);
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
