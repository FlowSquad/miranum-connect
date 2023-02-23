package io.miragon.miranum.integrations.example.c7;

import io.miragon.miranum.integrations.example.ExampleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ExampleConfiguration.class)
public class ExampleC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(ExampleC7Application.class, args);
    }

}
