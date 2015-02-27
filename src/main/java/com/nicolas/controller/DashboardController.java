package com.nicolas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.models.Page;
import com.nicolas.service.Impl.ComputerServiceImpl;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ComputerServiceImpl computerService;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(@RequestParam(value = "page", required = true, defaultValue="0") int intPage,
			@RequestParam(value = "nbPerPage", defaultValue="10", required = true) int nbPerPage,
			@RequestParam(value = "search", defaultValue="", required = true) String search, ModelMap model) {

		Page p = new Page();

		p.setIndex(intPage);
		p.setNbComputerPerPage(nbPerPage);

		// the view need it put in back in the search input
		model.addAttribute("search", search);

		Page page = this.computerService.getPage(p, search);
		
		model.addAttribute("page", page);

		return "dashboard";
	}

}