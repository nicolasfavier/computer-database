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

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.models.Company;
import com.nicolas.service.Impl.CompanyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest {

	@Mock
	private Company company;
	@Mock
	private CompanyDaoImpl companyDaoImpl;
	private List<Company> companyListRef;
	private CompanyServiceImpl companyServiceImpl; 

	@Before
	public void init() {
		companyListRef = new ArrayList<Company>();
		for (int i = 0; i < 4; i++) {
			companyListRef.add(company);
		}
		when(companyDaoImpl.getAll()).thenReturn(companyListRef);
		when(companyDaoImpl.getByID(1)).thenReturn(company);
		when(companyDaoImpl.getByID(-1)).thenReturn(null);
		when(companyDaoImpl.getByID(9999)).thenReturn(null);
		companyServiceImpl = new CompanyServiceImpl(
				companyDaoImpl);
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
