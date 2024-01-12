package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.Userservice;

import jakarta.servlet.http.HttpSession;

@Controller
public class Tunecontroller {

	@Autowired
	Userservice us;
	
	@Autowired
	SongService sonser;
	
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
    		 @RequestParam("password")String password,HttpSession session,Model model) {
    	
    	if(us.validate(email,password)==true) {
    		String role = us.getRole(email);
    		session.setAttribute("email", email);
    		if(role.equals("admin")) {
    			return "adminhome";
    		}else {

        		Users user = us.getUser(email);
        		boolean userStatus = user.isPremium();
        		List<Song> songList = sonser.fetchAllSongs();
        		model.addAttribute("isPremium", userStatus);
        		model.addAttribute("songs", songList);
        		
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
