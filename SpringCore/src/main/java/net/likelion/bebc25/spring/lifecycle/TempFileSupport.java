package net.likelion.bebc25.spring.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class TempFileSupport {
    @Value("resources/temp.log")
    private String filepath;
    TempFileSupport() {
        System.out.println("called constructor " + filepath);
        System.out.println(filepath + " 경로의 FileOutputStream 생성");
    }

    @PostConstruct
    public void init() {
        System.out.println(filepath + " 경로의 FileOutputStream 생성");
    }

    public void writeLog(String msg) {
        System.out.println("saved log to: " + filepath + " message: " + msg);
    }

    @PreDestroy
    public void close() {
        System.out.println(filepath + " 경로의 FIleOutputStream 닫기.");
    }
}
