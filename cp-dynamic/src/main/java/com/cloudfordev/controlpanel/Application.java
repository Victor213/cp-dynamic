package com.cloudfordev.controlpanel;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * DDD layers:
 * 
 * Application ( MVC Controllers, Exceptions )
 * Data Transfer 
 * Domain ( Entity models )
 *  
 * @author Lynn Owens
 *
 */

@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:spring-config.xml")
public class Application {
    public static void main(String[] args) {
    	// Test 2
    	Configuration.loadConfig();
        SpringApplication.run(Application.class, args);
    }
}
