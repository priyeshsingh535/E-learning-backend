package com.elearn.app;

import com.elearn.app.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartLearnBackApplicationTests {


	@Autowired
	private CategoryService categoryService;


	@Test
	void contextLoads() {
	}

	@Test
	public void testCategory()
	{
		categoryService.addCourseToCategory("79d22c79-c750-4cd5-9a12-8a1dfcf65933", "13b1ebf1-4116-4cf0-9f41-0408f400bbc8");
	}

}
