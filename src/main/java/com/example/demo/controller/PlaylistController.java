package com.example.demo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController {

	@Autowired
	SongService songservice;
	
	@Autowired
	PlaylistService playlistservice;
	
	@GetMapping("/createplaylist")
	public String createPlaylist(Model model) {
		
		List<Song> songList = songservice.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createplaylists";
	}
	
    @PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		
    	//Updating playList table
		playlistservice.addPlaylist(playlist); 
		
		//updating song table
		List<Song> songList =  playlist.getSongs();
		for(Song s:songList) {
			s.getPlaylists().add(playlist);
			songservice.updateSong(s);
			//update song object in db
			
		}
		return "adminhome";
	}
    
    @GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {
		
		List<Playlist> playlist = playlistservice.fetchAllPlaylists();
		
		model.addAttribute("playlists", playlist);
		

		return "displayPlaylists";
	}
	
}
