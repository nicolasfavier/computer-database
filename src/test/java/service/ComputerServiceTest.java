package service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Interfaces.ComputerService;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceTest {
	private ComputerService cut;
	@Mock
	private ComputerDao computerDao;
	private Computer computer;
	private List<Computer> computers;
	private Page page;
	private static final int COUNT_TOTAL = 20;
	
	@Autowired
	ComputerDtoMapper computerDtoMapper;

	@Before
	public void setUp() {
		computer = new Computer.Builder().id(2).name("test")
				.company(new Company(2, "super company")).build();

		page = new Page();

		computers = new ArrayList<>();
		for (int i = 0; i < COUNT_TOTAL; i++)
			computers.add(computer);

		page.setIndex(1);
		page.setNbComputerPerPage(10);
		page.setComputerList(computerDtoMapper.ComputerToDto(computers));

		when(computerDao.getAll()).thenReturn(computers);
		when(computerDao.getByID(3)).thenReturn(computer);
		when(computerDao.getPage(page)).thenReturn(page);
		when(computerDao.getCount("test")).thenReturn(10);

		cut = new ComputerServiceImpl(computerDao);
	}

	@Test
	public void TestGetAll() {
		List<Computer> computers = cut.getAll();
		Assert.assertEquals(computers.size(), COUNT_TOTAL);
	}

	@Test
	public void TestGetById() {
		Computer computerReturn = cut.getByID(3);
		Assert.assertEquals(computerReturn, computer);

		computerReturn = cut.getByID(300);
		Assert.assertNull(computerReturn);
	}

}
