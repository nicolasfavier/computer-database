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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.service.Interfaces.ComputerService;



@Controller
@RequestMapping("/edit-computer")
public class EditController {
	private static Logger LOGGER = LoggerFactory.getLogger(EditController.class);

	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	
	@Autowired
	private CompanyService companyService;
	
		
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String doGet(@PathVariable int id, ModelMap model) {

			Computer computer = this.computerService.getByID(id);
			
			if (computer != null) {
				List<Company> companies = this.companyService.getAll();

				model.addAttribute("computer", computerDtoMapper.ComputerToDto(computer));
				model.addAttribute("companies", companies);
				
				return "editComputer";
			} else {
				return "404";
			}
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public String doPost(@Valid @ModelAttribute ComputerDto computer, BindingResult result, ModelMap model) {

			if(result.hasErrors()) {
				LOGGER.info("Wrong input, redirecting to the view");
				List<Company> companies = this.companyService.getAll();
				model.addAttribute("companies", companies);
				return "editComputer";
			} else {
				this.computerService.update(computerDtoMapper.ComputerFromDto(computer));
				LOGGER.info("Computer edit with success, redirecting to the Dashboard");
				return "redirect:/dashboard";
			}
	}
}