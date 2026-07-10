package net.likelion.bebc25.spring.componentscan.aop.springaop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class SpringComponentScanApplication {
    void main() {

        try {
            // 1. Create spring container
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

            // 2. get driver bean in container
            Driver driver = context.getBean(Driver.class);

            System.out.println("Driver is " + driver.toString());

            // 3. execute business logic
            driver.driveCar(110);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
