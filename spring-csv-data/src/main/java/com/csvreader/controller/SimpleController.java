package com.csvreader.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.csvreader.service.ReaderService;

@Controller
public class SimpleController {
	@Value("${spring.application.name}")
	String appName;
	
	@Value("${csv.page.limit}")
	int recordsPerPage;

	@Autowired
	ReaderService readerService;

	@GetMapping("/data")
	public String homePage(@RequestParam(value = "page", required = false) Integer page,Model model) throws IOException, URISyntaxException {
		model.addAttribute("appName", appName);
		List<String[]> data = readerService.readData(page,model);
		
		System.out.println("Read data from csv - recordCount : " + (null != data ? data.size() : 0));
		
		return "home";
	}
}
