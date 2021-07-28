package com.corona.main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corona.main.model.Corona;
import com.corona.main.service.CoronaVirusDataService;

@RestController
public class CoronaController {
	
	
	@Autowired
	private CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/data")
	public ResponseEntity<List<Corona>> home() throws IOException, InterruptedException {
		List<Corona> list = coronaVirusDataService.fetchVirusData();
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
}
