package com.warehousebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WarehousebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousebackendApplication.class, args);
	}

}
