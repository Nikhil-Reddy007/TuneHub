package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Song;
import com.example.demo.services.SongService;

@Controller
public class SongController {

	@Autowired
	SongService serv;
	
	@PostMapping("/newSong")
	public String addSong(@ModelAttribute Song song) {
		boolean songStatus = serv.songExists(song.getName());
		if(songStatus==false) {
			serv.addSong(song);	
			System.out.println("User added successfully");
		}
		else {
			System.out.println("User already exists");
		}

		return "adminhome";
	}
	
	@GetMapping("/viewSongs")
	public String viewSongs(Model model) {
		
		List<Song> songlist = serv.fetchAllSongs();
		
		model.addAttribute("songs", songlist);
		

		return "displaySongs";
	}
	
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		
		boolean premiumUser = false;
		
		if(premiumUser == true) {
			
			List<Song> songlist = serv.fetchAllSongs();
			
			model.addAttribute("songs", songlist);
		}else {
			return "makePayment";
		}
		
		return "displaySongs";
	}
	
	
	
}
