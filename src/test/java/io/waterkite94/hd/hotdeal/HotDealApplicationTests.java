package io.waterkite94.hd.hotdeal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class HotDealApplicationTests {

	@Test
	void contextLoads() {
	}

}
