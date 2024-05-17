package com.example.springboot;

import java.util.Arrays;

import com.example.springboot.socket.WebSocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@EnableScheduling
public class Application {
	public static final Logger staticLogger = LogManager.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		//解决WebSocket不能注入的问题
		WebSocketServer.setApplicationContext(configurableApplicationContext);
	}

}