package net.likelion.bebc25.spring.aop.springaop;

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
