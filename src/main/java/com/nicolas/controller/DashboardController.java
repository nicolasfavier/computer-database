package com.nicolas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.models.Page;
import com.nicolas.service.Impl.ComputerServiceImpl;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ComputerServiceImpl computerService;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(Page page, ModelMap model) {

		Page newpage = this.computerService.getPage(page);
		
		model.addAttribute("page", newpage);

		return "dashboard";
	}

}