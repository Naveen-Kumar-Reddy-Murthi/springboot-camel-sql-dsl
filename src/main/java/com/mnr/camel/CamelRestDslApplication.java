package com.mnr.camel;

import com.mnr.camel.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
public class CamelRestDslApplication implements CommandLineRunner {

	private final OrderRepoistory orderRepoistory;

	public static void main(String[] args) {
		SpringApplication.run(CamelRestDslApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Order> list = new ArrayList<>();
		list.add(new Order(1, "Mac", 120000));
		list.add(new Order(2, "Tesla", 8000000));
		list.add(new Order(3, "Falcon 9", 2000000));
		list.add(new Order(4, "Not a Flame Thrower", 125000));
		orderRepoistory.saveAll(list);
	}
}
