package com.hanghea.clonecarrotbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class CloneCarrotBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloneCarrotBeApplication.class, args);
    }
    @PostConstruct
    public void started(){ TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); }

}
