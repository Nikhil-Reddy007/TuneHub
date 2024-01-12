package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.Userservice;

import jakarta.servlet.http.HttpSession;

@Controller
public class Tunecontroller {

	@Autowired
	Userservice us;
	
	@PostMapping("/tc")
	public String addUsers(@ModelAttribute Users user) {
		boolean userStatus = us.emailExists(user.getEmail());
		if(userStatus == false) {
			us.addUser(user);
			System.out.println("user added successfully");
		}
		else {
			System.out.println("User already Exists");
		}
		
		return "home";
	}
	
	@PostMapping("/validate")
    public String validate(@RequestParam("email")String email, 
    		 @RequestParam("password")String password) {
    	
    	if(us.validate(email,password)==true) {
    		String role = us.getRole(email);
    		if(role.equals("admin")) {
    			return "adminhome";
    		}else {
    			return "customerhome";
    		}
    		
    	}
    	else {
    		return "login";
    	}
    }
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "login";
	}
	
	
	
}
