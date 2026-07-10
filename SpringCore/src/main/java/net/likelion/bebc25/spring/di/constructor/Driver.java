package net.likelion.bebc25.spring.di.constructor;

public class Driver {
    private Car car;

    Driver(Car car) {
        this.car = car;
    }

    public void driveCar() {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}
