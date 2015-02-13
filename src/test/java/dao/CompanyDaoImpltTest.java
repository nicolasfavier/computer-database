package dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.models.Company;
import com.nicolas.utils.ScriptRunner;

@RunWith(MockitoJUnitRunner.class)
public class CompanyDaoImpltTest {
	private CompanyDaoImpl companyDaoImpl = DaoManagerImpl.INSTANCE
			.getCompanyDaoImpl();

	@Before
	public void init() {
		// reset database
		ScriptRunner.runScript();
	}

	@Test
	public void testGetNegativeID() {
		Company c = null;
		c = companyDaoImpl.getByID(-1);
		Assert.assertNull(c);
	}

	@Test
	public void testGetBadID() {
		Company c = null;
		c = companyDaoImpl.getByID(9999);
		Assert.assertNull(c);
	}

	@Test
	public void testGetByID() {
		Company c = null;
		Company refCompany = new Company(1, "Apple Inc.");

		c = companyDaoImpl.getByID(1);
		Assert.assertEquals(refCompany, c);
	}

	@Test
	public void testGetAll() {
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyDaoImpl.getAll();
		Assert.assertEquals(5, companyList.size());
	}
}
