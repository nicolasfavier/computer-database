package service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.service.Impl.CompanyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

	@Mock
	private Company company;
	@Mock
	private Computer computer;
	@Mock
	private CompanyDao companyDao;
	@Mock
	private ComputerDao computerDao;


	private List<Company> companyListRef;
	private List<Computer> computerListRef;
	private CompanyServiceImpl companyServiceImpl; 
	
	@Before
	public void init() {
		companyListRef = new ArrayList<Company>();
		for (int i = 0; i < 4; i++) {
			companyListRef.add(company);
		}
		
		computerListRef = new ArrayList<Computer>();
		for (int i = 0; i < 20; i++) {
			computerListRef.add(computer);
		}
		
		when(companyDao.getAll()).thenReturn(companyListRef);
		when(companyDao.getByID(1)).thenReturn(company);
		when(companyDao.getByID(-1)).thenReturn(null);
		when(companyDao.getByID(9999)).thenReturn(null);
				 
		companyServiceImpl = new CompanyServiceImpl(
				companyDao, computerDao);
	}


	@Test
	public void testGetNegativeID() {
		Company c = null;
		c = companyServiceImpl.getByID(-1);
		Assert.assertNull(c);
	}

	@Test
	public void testGetBadID() {
		Company c = null;
		c = companyServiceImpl.getByID(9999);
		Assert.assertNull(c);
	}

	@Test
	public void testGetByID() {
		Company c = null;
		c = companyServiceImpl.getByID(1);
		Assert.assertEquals(company, c);
	}

	@Test
	public void testGetAll() {
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyServiceImpl.getAll();
		Assert.assertEquals(companyListRef, companyList);
	}
	
}
