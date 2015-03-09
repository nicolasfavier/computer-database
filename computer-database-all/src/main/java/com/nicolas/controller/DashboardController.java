package com.nicolas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nicolas.dto.PageDto;
import com.nicolas.dto.PageDtoMapper;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private PageDtoMapper pageDtoMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(PageDto p, ModelMap model) {

		Page viewPage = pageDtoMapper.PageFromDto(p);
		Page newpage = this.computerService.getPage(viewPage);
		PageDto pageDto = pageDtoMapper.toDto(newpage);
		model.addAttribute("page", pageDto);

		return "dashboard";
	}

}