package io.waterkite94.hd.hotdeal;

import org.springframework.boot.SpringApplication;

public class TestHotDealApplication {

	public static void main(String[] args) {
		SpringApplication.from(HotDealApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
