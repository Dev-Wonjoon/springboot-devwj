package net.likelion.bebc25.spring.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TempFileSupport2 implements InitializingBean, DisposableBean {
    @Value("resources/temp.log")
    private String filepath;
    TempFileSupport2() {
        System.out.println("called constructor " + filepath);
        System.out.println(filepath + " 경로의 FileOutputStream 생성");
    }

    public void writeLog(String msg) {
        System.out.println("saved log to: " + filepath + " message: " + msg);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(filepath + " 경로의 FileOutputStream 생성");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(filepath + " 경로의 FileOutputStream 닫기.");
    }
}
