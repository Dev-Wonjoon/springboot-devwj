package net.springbootintellij;

import org.springframework.stereotype.Component;

@Component
public class Driver {
    private Car car;

    Driver(Car car) {
        this.car = car;
    }

    public void driveCar(int maxSpeed) {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}
