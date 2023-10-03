package com.bjornmagnusson.learning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bjornmagnusson.learning.repository.PostRepository;

@SpringBootTest(classes = TestApplication.class)
class ApplicationTests {

	@Autowired
	PostRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	void startupPosts() {
		var posts = repository.findAll();
		Assertions.assertTrue(posts.size() == 1, "Only one post should be available at startup");
	}
}
