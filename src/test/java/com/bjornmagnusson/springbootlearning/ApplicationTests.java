package com.bjornmagnusson.springbootlearning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bjornmagnusson.springbootlearning.model.Post;
import com.bjornmagnusson.springbootlearning.repository.PostRepository;

@SpringBootTest(classes = TestApplication.class)
class ApplicationTests {

	@Autowired
	PostRepository repository;

	@Test
	@DisplayName("Application should start")
	void contextLoads() {
	}

	@Test
	@DisplayName("One post available after startup")
	void startupPosts() {
		var posts = repository.findAll();
		Assertions.assertTrue(posts.size() == 1, "Only one post should be available at startup");
	}

	@Test
	@DisplayName("Create and delete post")
	void createFindDeletePosts() {
		var post = new Post("title", "body");
		var postCreated = repository.save(post);
		var postFind = repository.findById(postCreated.getId());
		Assertions.assertTrue(postFind.isPresent(), "Created post should be findable by id");
		repository.deleteById(postFind.get().getId());
		var postDeleted = repository.findById(postCreated.getId());
		Assertions.assertTrue(postDeleted.isEmpty(), "Deleted post should not be findable by id");
	}
}
