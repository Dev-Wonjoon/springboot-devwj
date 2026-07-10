package net.likelion.bebc25.spring.componentscan.aop.springaop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Driver {

    @Autowired
    private Car car;

//    Driver() {
//        System.out.println("Driver constructor");
//    }
//    @Autowired
//    Driver(@Qualifier("gasolineCar") Car car) {
//        this.car = car;
//    }

//    @Autowired
//    public void setCar(Car car) {
//        System.out.println("called setter injection");
//        this.car = car;
//    }

    public void driveCar(int maxSpeed) {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}
