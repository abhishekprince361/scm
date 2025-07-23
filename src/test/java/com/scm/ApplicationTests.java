package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
    private EmailService mservice;

    @Test
    void sendEmailTest(){
        mservice.sendEmail("pkworks361@gmail.com", "Testing", "this is scm project working on email service");
    }

}
