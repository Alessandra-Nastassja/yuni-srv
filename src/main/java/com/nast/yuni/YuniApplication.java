package com.nast.yuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration + @EnableAutoConfiguration + @ComponentScan = @SpringBootApplication
@SpringBootApplication
public class YuniApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuniApplication.class, args);
	}

}
