package net.likelion.bebc25.spring.di.setter;

public class Driver {
    private Car car;

    public void setCar(Car car) {
        System.out.println("세터 인젝션");
        this.car = car;
    }

    Driver() {
    }

    public void driveCar() {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}
