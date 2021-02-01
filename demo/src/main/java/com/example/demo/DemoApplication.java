package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.application.familyManager.service.FamilyManager;
import com.example.demo.application.memberManager.MemberManager;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext cat = SpringApplication.run(DemoApplication.class, args);
		
		FamilyManager familyManager = (FamilyManager)cat.getBean("familyManager");
		//familyManager.writeSamples();
		familyManager.writeSamples2();
		
		MemberManager memberManager = (MemberManager)cat.getBean("memberManager");
		memberManager.writeSamples();
	}
}