package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.Services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;
	@Test
	void sendMailTest(){
		emailService.sendMail("suyashmali16@gmail.com", "Just For Trial", "SMc Working Test");
	}

}
