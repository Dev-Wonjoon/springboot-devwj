package net.likelion.bebc25.spring.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringLifeCycleApplication {
    void main() {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            TempFileSupport2 support = context.getBean(TempFileSupport2.class);
            support.writeLog("사용자가 로그인 함.");
            context.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
