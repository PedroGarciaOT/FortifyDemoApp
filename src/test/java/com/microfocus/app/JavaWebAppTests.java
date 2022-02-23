package com.microfocus.app;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		properties = {
				"app.operation=test"
		}
)
class JavaWebAppTests extends BaseTest {

	/*
	@Test
	void contextLoads() {
	}

	@Test public void Test1() {
		run();
	}

	@Test public void Test2() {
		run();
	}

	@Test public void Test3() {
		run();
	}

	@Test public void Test4() {
		run();
	}

	@Test public void Test5() {
		run();
	}
	*/

}
