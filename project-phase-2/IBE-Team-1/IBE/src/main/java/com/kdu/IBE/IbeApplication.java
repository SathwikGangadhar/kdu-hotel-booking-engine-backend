package com.kdu.IBE;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class 	IbeApplication {
	public static void main(String[] args) {
		SpringApplication.run(IbeApplication.class, args);
	}
}