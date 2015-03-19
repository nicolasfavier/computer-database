package com.nicolas.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nicolas.models.Page;

@Component
public class PageDtoMapper {
	@Autowired
	private ComputerDtoMapper computerDtoMapper;

	public Page PageFromDto(PageDto pageDto) {
		Page p = new Page();

		p.setNbComputerPerPage(pageDto.getNbComputerPerPage());
		p.setIndex(pageDto.getIndex());
		p.setSearch(pageDto.getSearch());
		p.setSortCriterion(pageDto.getSortCriterion());
		p.setTotalComputers(pageDto.getTotalComputers());
		p.setTotalPages(pageDto.getTotalPages());
		p.setComputerList(computerDtoMapper.ComputerFromDto(pageDto
				.getComputerList()));

		return p;
	}

	public PageDto toDto(Page page) {
		PageDto pageDTO = new PageDto();

		pageDTO.setIndex(page.getIndex());
		pageDTO.setComputerList(computerDtoMapper.ComputerToDto(page
				.getComputerList()));
		pageDTO.setSearch(page.getSearch());
		pageDTO.setSortCriterion(page.getSortCriterion());
		pageDTO.setTotalComputers(page.getTotalComputers());
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setNbComputerPerPage(page.getNbComputerPerPage());

		return pageDTO;
	}

}
