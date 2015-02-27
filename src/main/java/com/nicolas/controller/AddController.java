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

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.validator.DtoValidator;

@Controller
@RequestMapping("/add-computer")
public class AddController {
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private ComputerServiceImpl computerService;

	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model) {

		List<Company> companies = this.companyService.getAll();

		model.addAttribute("companies", companies);

		LOGGER.info("get the view for add Computer");
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute ComputerDto computerDto, ModelMap model) {

		List<String> validationErrors = new ArrayList<>();
		validationErrors = DtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			this.computerService.add(ComputerDtoMapper.ComputerFromDto(computerDto));
			LOGGER.info("Computer added with success, redirecting to the Dashboard");
			return "redirect:dashboard";
		} else {
			LOGGER.info("Wrong input, redirecting to the view");
			model.addAttribute("validationErrors", validationErrors);
			// doGet(request, response);
			return "";
		}
	}
}