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

import com.csvreader.Constants;
import com.csvreader.config.ConfigProperties;
import com.csvreader.service.ReaderService;

@Controller
public class SimpleController {
	@Autowired
	ConfigProperties configProperties;

	@Autowired
	ReaderService readerService;

	@GetMapping("/data")
	public String homePage(@RequestParam(value = "page", required = false) Integer page, Model model)
			throws IOException, URISyntaxException {
		model.addAttribute(Constants.APP_NAME_ATTRIBUTE, configProperties.getAppName());
		readerService.readData(page, model);
		return Constants.HOME_VIEW;
	}
}
