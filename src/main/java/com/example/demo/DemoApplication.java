package com.example.demo;

import static com.example.demo.MSFileUtil.streamToString;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
            .sources(DemoApplication.class)
            .initializers(context ->
                context
                    .getEnvironment()
                    .getPropertySources()
                    .addLast(new MSPropertySource(
                        new MSYamlPropertyKeyBuilder(
                            context.getEnvironment().getProperty("environment"),
                            context.getEnvironment().getProperty("region"),
                            context.getEnvironment().getProperty("slot")
                        ),
                        new MSConfigYamlDataSource(
                            (new MSYamlFileLoader(context))
                            .load(
                                List.of(
                                    context.getEnvironment().getProperty("environment"),
                                    context.getEnvironment().getProperty("region"),
                                    context.getEnvironment().getProperty("slot")
                                )
                            )
                        )
                    )
                )
            )
            .run(args);
	}

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            System.out.println("message from application.properties " + environment.getProperty("my.var"));
            System.out.println("brett:brett.db.connection:"+environment.getProperty("brett.db.connection"));
            System.out.println("brett:brett.location:"+environment.getProperty("brett.location"));
            System.out.println("brett:machine:"+environment.getProperty("machine"));
            System.out.println("brett:environment:" +environment.getProperty("environment"));
            System.out.println("brett:region:" +environment.getProperty("region"));
            System.out.println("brett:slot:" +environment.getProperty("slot"));
        };
    }

}
