package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.ScriptRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDaoImplTest {

	public final static String SQL_REQ_FILE = "PrepDB.sql";
	@Mock private Company cmp;

	@Before
	public void init() {
		// reset database
		ScriptRunner.runScript(SQL_REQ_FILE);
	}

	@Test
	public void testGetNegativeID() {
		Computer c = null;
		c = ComputerDaoImpl.INSTANCE.getByID(-1);
		Assert.assertNull(c);
	}


	@Test
	public void testGetBadID() {
		Computer c = null;
		c = ComputerDaoImpl.INSTANCE.getByID(99999);
		Assert.assertNull(c);
	}

	@Test
	public void testGetByID() {
		Computer c = null;
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany =  new Company(1,"Apple Inc.");
		Computer refComputer = new Computer(17,"Apple III Plus", in, dis,refCompany);
		
		c = ComputerDaoImpl.INSTANCE.getByID(17);
		Assert.assertEquals(refComputer, c);
	}
	
	@Test
	public void testGetByIDNullDate() {
		Computer c = null;
		Company refCompany =  new Company(2,"Thinking Machines");
		Computer refComputer = new Computer(2,"CM-2a", null, null,refCompany);
		
		c = ComputerDaoImpl.INSTANCE.getByID(2);
		Assert.assertEquals(refComputer, c);
	}
	
	@Test
	public void testGetCount() {
		int count = ComputerDaoImpl.INSTANCE.getCount();
		Assert.assertEquals(21, count);
	}
	
	
	@Test
	public void testdelete() {
		boolean res = false;
		res = ComputerDaoImpl.INSTANCE.delete(17);
		Computer c = ComputerDaoImpl.INSTANCE.getByID(17);
		Assert.assertEquals(res, true);
		Assert.assertNull(c);
	}
	
	@Test
	public void testdeleteBadId() {
		boolean res = false;
		res = ComputerDaoImpl.INSTANCE.delete(30);
		Assert.assertEquals(res, false);
	}
	
	@Test
	public void testAdd() {
		boolean res = false;
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany =  new Company(1,"Apple Inc.");
		Computer refComputer = new Computer(22,"Apple III Plus", in, dis,refCompany);
		
		res = ComputerDaoImpl.INSTANCE.add(refComputer);
		Assert.assertEquals(res, true);
		Computer c = ComputerDaoImpl.INSTANCE.getByID(22);
		Assert.assertEquals(refComputer, c);
	}
	
	@Test
	public void testUpdate() {
		boolean res = false;
		LocalDate in = LocalDate.of(1983, 12, 01);
		LocalDate dis = LocalDate.of(1984, 04, 01);
		Company refCompany =  new Company(1,"Apple Inc.");
		Computer refComputer = new Computer(12,"Apple III Plus", in, dis,refCompany);
		
		res = ComputerDaoImpl.INSTANCE.update(refComputer);
		Assert.assertEquals(res, true);
		Computer c = ComputerDaoImpl.INSTANCE.getByID(12);
		Assert.assertEquals(refComputer, c);
	}
	
	@Test
	public void testGetAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = ComputerDaoImpl.INSTANCE.getAll();
		Assert.assertEquals(21,computerList.size());
	}
	
	@Test
	public void testGetBoundedList() {
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = ComputerDaoImpl.INSTANCE.getBoundedList(0);
		Assert.assertEquals(Page.NB_COMPUTERS,computerList.size());
	}
	
	@Test
	public void testGetBoundedListEmpty() {
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = ComputerDaoImpl.INSTANCE.getBoundedList(20);
		Assert.assertEquals(0,computerList.size());
	}
	
	@Test
	public void testGetBoundedListWrongIndex() {
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = ComputerDaoImpl.INSTANCE.getBoundedList(-10);
		Assert.assertEquals(0,computerList.size());
	}
}
