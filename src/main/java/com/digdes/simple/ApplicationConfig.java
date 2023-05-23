package com.digdes.simple;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.digdes.simple")
@PropertySource("classpath:/application.properties")
@EntityScan(basePackages = "com.digdes.simple")
public class ApplicationConfig {
}
