package com.bjornmagnusson.springbootlearning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.bjornmagnusson.springbootlearning.model.Cart;
import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TestApplication.class, properties = { "testdata.enabled=false" })
@AutoConfigureMockMvc
class ApplicationTests {
	private static Logger LOGGER = LoggerFactory.getLogger(ApplicationTests.class);

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

	@Test
	@DisplayName("Cart REST API")
	void cartRestApi() throws Exception {
		var json = """
				{
					"name":"name",
					"description": "description"
				}""";
		LOGGER.info("Creating product");
		var resultProductCreate = mockMvc.perform(post("/api/products").content(json).header("Content-Type", "application/json"))
			.andExpect(status().isCreated()).andReturn();
		var location = resultProductCreate.getResponse().getHeader("Location");
		var resultProduct = mockMvc.perform(get(location)).andExpect(status().isOk()).andReturn();
		var productJson = resultProduct.getResponse().getContentAsString();
		var product = objectMapper.readValue(productJson, Product.class);
		mockMvc.perform(post("/api/cart/products").content(productJson).header("Content-Type", "application/json"))
			.andExpect(status().isNoContent()).andReturn();
		mockMvc.perform(post("/api/cart/products").content(productJson).header("Content-Type", "application/json"))
			.andExpect(status().isNoContent()).andReturn();
		LOGGER.info("Recieved product, {}", productJson);

		LOGGER.info("Adding product {} to cart", productJson);
		var resultGetAll = mockMvc.perform(get("/api/cart"))
			.andExpect(status().isOk()).andReturn();			
		var contentAsString = resultGetAll.getResponse().getContentAsString();
		var cart = objectMapper.readValue(contentAsString, Cart.class);
		Assertions.assertEquals(1, cart.getProducts().size());
		Assertions.assertEquals(2, cart.getProducts().iterator().next().getNumber());
		
		LOGGER.info("Removing product {} from cart", product);
		mockMvc.perform(delete("/api/cart/products/" + product.getId()).content(productJson).header("Content-Type", "application/json"))
			.andExpect(status().is(204));
		var resultDeletedAll = mockMvc.perform(get("/api/cart"))
			.andExpect(status().isOk()).andReturn();			
		var contentAsStringAfterDelete = resultDeletedAll.getResponse().getContentAsString();
		var cartAfterDelete = objectMapper.readValue(contentAsStringAfterDelete, Cart.class);
		Assertions.assertEquals(1, cartAfterDelete.getProducts().size());
		Assertions.assertEquals(1, cartAfterDelete.getProducts().iterator().next().getNumber());
		mockMvc.perform(delete("/api/cart/products/" + product.getId()).content(productJson).header("Content-Type", "application/json"))
			.andExpect(status().is(204));
		var resultDeletedAll2 = mockMvc.perform(get("/api/cart"))
			.andExpect(status().isOk()).andReturn();			
		var contentAsStringAfterDelete2 = resultDeletedAll2.getResponse().getContentAsString();
		var cartAfterDelete2 = objectMapper.readValue(contentAsStringAfterDelete2, Cart.class);
		Assertions.assertTrue(cartAfterDelete2.getProducts().isEmpty());
		mockMvc.perform(delete(location))
			.andExpect(status().isNoContent());
	}
}
