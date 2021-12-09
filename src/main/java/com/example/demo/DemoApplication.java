package com.example.demo;

import static com.example.demo.MSFileUtil.streamToString;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
            .sources(DemoApplication.class)
            .initializers(context -> {
                try {
                    context
                        .getEnvironment()
                        .getPropertySources()
                        .addLast(new MSPropertySource(
                            new MSPropertyBasicKeyBuilder(context.getEnvironment()),
                            new MSConfigJsonDataSource(
                                streamToString(context.getResource("classpath:hierarchy-config.json").getInputStream())
                             )
                         ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            })
            .run(args);
	}

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            System.out.println("message from application.properties " + environment.getProperty("my.var"));
            System.out.println("brett:brett.db.connection:"+environment.getProperty("brett.db.connection"));
            System.out.println("brett:brett.domain:"+environment.getProperty("brett.domain"));
            System.out.println("brett:brett.location:"+environment.getProperty("brett.location"));
            System.out.println("brett:brett.machine.name:"+environment.getProperty("brett.machine.name"));
        };
    }

}
