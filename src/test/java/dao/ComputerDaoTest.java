package dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.DaoManager;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.ScriptRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDaoTest {
	private ComputerDao computerDao = DaoManager.INSTANCE.getComputerDaoImpl();

	@Mock
	private Company cmp;

	@Before
	public void init() {
		// reset database
		ScriptRunner.runScript();
	}

	@Test
	public void testGetNegativeID() {
		Computer c = null;
		c = computerDao.getByID(-1);
		Assert.assertNull(c);
	}

	@Test
	public void testGetBadID() {
		Computer c = null;
		c = computerDao.getByID(99999);
		Assert.assertNull(c);
	}

	@Test
	public void testGetByID() {
		Computer c = null;
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany = new Company(1, "Apple Inc.");
		Computer refComputer = new Computer.Builder().id(17).name("Apple III Plus").introduced(in)
				.discontinued(dis).company(refCompany).build();

		c = computerDao.getByID(17);
		Assert.assertEquals(refComputer, c);
	}

	@Test
	public void testGetByIDNullDate() {
		Computer c = null;
		Company refCompany = new Company(2, "Thinking Machines");
		Computer refComputer = new Computer.Builder().id(2).name("CM-2a").company(refCompany)
				.build();

		c = computerDao.getByID(2);
		Assert.assertEquals(refComputer, c);
	}

	@Test
	public void testGetCount() {
		int count = computerDao.getCount("");
		Assert.assertEquals(21, count);
	}

	@Test
	public void testdelete() {
		computerDao.delete(17);
		Computer c = computerDao.getByID(17);
		Assert.assertNull(c);
	}

	@Test
	public void testAdd() {
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany = new Company(1, "Apple Inc.");
		Computer refComputer = new Computer.Builder().id(22).name("Apple III Plus").introduced(in)
				.discontinued(dis).company(refCompany).build();

		computerDao.add(refComputer);
		Computer c = computerDao.getByID(22);
		Assert.assertEquals(refComputer, c);
	}

	@Test
	public void testUpdate() {
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany = new Company(1, "Apple Inc.");
		Computer refComputer = new Computer.Builder().id(12).name("Apple III Plus").introduced(in)
				.discontinued(dis).company(refCompany).build();

		computerDao.update(refComputer);
		Computer c = computerDao.getByID(12);
		Assert.assertEquals(refComputer, c);
	}

	@Test
	public void testGetAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = computerDao.getAll();
		Assert.assertEquals(21, computerList.size());
	}

	@Test
	public void testGetBoundedList() {
		Page page = new Page();
		page = computerDao.getPage(page, "");
		Assert.assertEquals(10, page.getComputerList().size());
	}

	@Test
	public void testDeleteByCompanyId() {
		Connection connection = ConnectionManager.getConnection(true);
		int count = computerDao.getCount("");
		Assert.assertEquals(21, count);

		computerDao.deleteByCompanyId(1, connection);
		count = computerDao.getCount("");
		Assert.assertEquals(15, count);
	}
}
