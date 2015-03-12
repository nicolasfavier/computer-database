package com.nicolas.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.service.Interfaces.ComputerService;



@Controller
@RequestMapping("/add-computer")
public class AddController {
	private static Logger LOGGER = LoggerFactory.getLogger(AddController.class);

	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	
	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model) {

		List<Company> companies = this.companyService.getAll();

		model.addAttribute("companies", companies);
        model.addAttribute("computerDto", new ComputerDto());
		LOGGER.info("get the view for add Computer");
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result, ModelMap model) {

		if(result.hasErrors()) {
			LOGGER.info("Wrong input, redirecting to the view");
			List<Company> companies = this.companyService.getAll();
			model.addAttribute("companies", companies);
			return "addComputer";
		} else {
			this.computerService.add(computerDtoMapper.ComputerFromDto(computerDto));
			LOGGER.info("Computer added with success, redirecting to the Dashboard");
			model.addAttribute("message", "Successfully add");
			return "redirect:dashboard";
		}
	}
}