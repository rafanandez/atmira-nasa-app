package com.atmira.nasa.fernandez.rafa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "com.atmira.nasa.fernandez.rafa", excludeFilters = @Filter({Controller.class, Configuration.class}))
@ComponentScan(basePackages = "com.atmira.nasa.fernandez.rafa.controller")
public class AtmiraNasaConfig {

}
