package com.isaac.hydrantmap.common;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
public class CommonConfiguration {

}
