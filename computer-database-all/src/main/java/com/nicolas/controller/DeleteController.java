package com.nicolas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.service.Interfaces.ComputerService;
import com.nicolas.utils.Utils;

@Controller
@RequestMapping("/delete")
public class DeleteController {
	private static Logger LOGGER = LoggerFactory.getLogger(DeleteController.class);

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@RequestParam(value = "selection") String idsToDelete, ModelMap model) {

		String[] array = idsToDelete.split(",");
		// TODO send an unique command for delete
		for (String idString : array) {
			int id = Utils.getIntFromString(idString);
			this.computerService.delete(id);
		}

		LOGGER.info("Computer(s) have been delete, redirecting to the view ");

		return "redirect:dashboard";

	}
}