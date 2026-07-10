package net.likelion.bebc25.spring.di.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringCoreApplication {
    void main() {

        // 1. Create spring container
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


        // 2. get driver bean in container
        Driver driver = context.getBean(Driver.class);

        // 3. execute business logic
        driver.driveCar();

        driver.setCar(new HybridCar());
        driver.driveCar();
    }
}
