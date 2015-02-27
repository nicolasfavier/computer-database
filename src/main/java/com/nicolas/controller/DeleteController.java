package com.nicolas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.utils.Utils;

@Controller
@RequestMapping("/delete")
public class DeleteController {
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private ComputerServiceImpl computerService;

	@Autowired
	private CompanyService companyService;

	@RequestMapping(method = RequestMethod.POST)
	public String printHello(@ModelAttribute String idsToDelete, ModelMap model) {

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