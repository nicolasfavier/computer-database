package com.nicolas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.validator.DtoValidator;


@Controller
@RequestMapping("/edit-computer")
public class EditController {
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private ComputerServiceImpl computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(value = "id", required = true) int index, ModelMap model) {

			Computer computer = this.computerService.getByID(index);
			
			if (computer != null) {
				List<Company> companies = this.companyService.getAll();

				model.addAttribute("computer", ComputerDtoMapper.ComputerToDto(computer));
				model.addAttribute("companies", companies);
				
				return "editComputer";
			} else {
				return "404";
			}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute ComputerDto computerDto, ModelMap model) {

			List<String> validationErrors = new ArrayList<>();
			validationErrors = DtoValidator.validate(computerDto);

			if (validationErrors.size() == 0) {
				this.computerService.update(ComputerDtoMapper.ComputerFromDto(computerDto));
				LOGGER.info("Computer edit with success, redirecting to the Dashboard");
				return "redirect:dashboard";
			} else {
				LOGGER.info("Wrong input, redirecting to the view");
				model.addAttribute("validationErrors", validationErrors);
				//doGet(request, response);
				return "";
			}
	}
}