package sajo.study.common.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"sajo.study.common.core","sajo.study.common.web"})
public class WebApplication{
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
