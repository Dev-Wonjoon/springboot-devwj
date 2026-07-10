package net.likelion.bebc25.spring.di.constructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public Car car() {
        return new GasolineCar();
    }

//    @Bean
//    public Car car2() {
//        return new HybridCar();
//    }


    @Bean
    public Driver driver(Car car) {
        return new Driver(car);
    }
}
