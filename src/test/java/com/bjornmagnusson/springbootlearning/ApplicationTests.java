package com.bjornmagnusson.springbootlearning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	@DisplayName("Application should start")
	void contextLoads() {
	}

	@Test
	@DisplayName("Create and delete product")
	void createFindDeleteProducts() {
		var product = new Product("name", "description");
		var productCreated = productRepository.save(product);
		var productFind = productRepository.findById(productCreated.getId());
		Assertions.assertTrue(productFind.isPresent(), "Created product should be findable by id");
		productRepository.deleteById(productFind.get().getId());
		var productDeleted = productRepository.findById(productCreated.getId());
		Assertions.assertTrue(productDeleted.isEmpty(), "Deleted product should not be findable by id");
	}

	@Test
	@DisplayName("Product REST API")
	void productRestApi() throws Exception {
		var json = """
				{
					"name":"name",
					"description": "description"
				}""";
		var resultPost = mockMvc.perform(post("/api/products").content(json).header("Content-Type", "application/json"))
			.andExpect(status().isCreated()).andReturn();
		var location = resultPost.getResponse().getHeader("Location");
		
		var resultGetAll = mockMvc.perform(get("/api/products"))
			.andExpect(status().isOk()).andReturn();			
		var contentAsString = resultGetAll.getResponse().getContentAsString();
		var response = objectMapper.readValue(contentAsString, List.class);
		Assertions.assertEquals(response.size(), 1);
		
		var resultGet = mockMvc.perform(get(location))
			.andExpect(status().isOk()).andReturn();			
		var contentAsStringAll = resultGet.getResponse().getContentAsString();
		var responseAll = objectMapper.readValue(contentAsStringAll, Product.class);
		Assertions.assertEquals(responseAll.getName(), "name");
		Assertions.assertEquals(responseAll.getDescription(), "description");
		mockMvc.perform(delete(location))
			.andExpect(status().isNoContent());
		mockMvc.perform(get(location))
			.andExpect(status().isNotFound()).andReturn();
	}
}
