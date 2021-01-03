package com.andistoev.onionarchservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class OnionArchServiceApplication {

    public static void main(String[] args) {
        Environment env = SpringApplication.run(OnionArchServiceApplication.class, args).getEnvironment();
        log.info("Start SwaggerUI to use the Shopping List's APIs: "
                + "http://localhost:{}/swagger-ui/index.html#/shopping-list-controller", env.getProperty("server.port"));
    }
}
