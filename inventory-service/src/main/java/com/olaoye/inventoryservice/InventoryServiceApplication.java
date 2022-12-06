package com.olaoye.inventoryservice;

import com.olaoye.inventoryservice.model.Inventory;
import com.olaoye.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			if (inventoryRepository.findAll().size() < 0) {
				Inventory inventory = new Inventory();
				inventory.setSkuCode("iphone_13");
				inventory.setQuantity(100);

				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("iphone_13_red");
				inventory1.setQuantity(0);
				inventoryRepository.save(inventory);
				inventoryRepository.save(inventory1);
			}

		};
	}
}
