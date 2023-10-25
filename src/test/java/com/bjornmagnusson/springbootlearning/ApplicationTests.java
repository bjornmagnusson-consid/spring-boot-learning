package com.bjornmagnusson.springbootlearning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.model.Product;
import com.bjornmagnusson.springbootlearning.repository.PostRepository;
import com.bjornmagnusson.springbootlearning.repository.ProductRepository;

@SpringBootTest(classes = TestApplication.class)
class ApplicationTests {

	@Autowired
	PostRepository postRepostory;

	@Autowired
	ProductRepository productRepository;

	@Test
	@DisplayName("Application should start")
	void contextLoads() {
	}

	@Test
	@DisplayName("No posts available after startup")
	void startupPosts() {
		var posts = postRepostory.findAll();
		Assertions.assertTrue(posts.isEmpty(), "No posts should be available at startup");
	}

	@Test
	@DisplayName("Create and delete post")
	void createFindDeletePosts() {
		var post = new Post("title", "body");
		var postCreated = postRepostory.save(post);
		var postFind = postRepostory.findById(postCreated.getId());
		Assertions.assertTrue(postFind.isPresent(), "Created post should be findable by id");
		postRepostory.deleteById(postFind.get().getId());
		var postDeleted = postRepostory.findById(postCreated.getId());
		Assertions.assertTrue(postDeleted.isEmpty(), "Deleted post should not be findable by id");
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
}
