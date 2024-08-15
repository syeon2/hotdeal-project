package io.waterkite94.hd.hotdeal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	protected ObjectMapper objectMapper = new ObjectMapper();
}
