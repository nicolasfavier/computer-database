package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.dao.impl.DaoManager;
import com.nicolas.models.Company;
import com.nicolas.utils.ScriptRunner;

@RunWith(MockitoJUnitRunner.class)
public class CompanyDaoTest {
	private CompanyDaoImpl companyDao = DaoManager.INSTANCE
			.getCompanyDaoImpl();

	@Before
	public void init() {
		// reset database
		ScriptRunner.runScript();
	}
	
	@Test
	public void testGetNegativeID() {
		Company c = null;
		c = companyDao.getByID(-1);
		Assert.assertNull(c);
	}

	@Test
	public void testGetBadID() {
		Company c = null;
		c = companyDao.getByID(9999);
		Assert.assertNull(c);
	}

	@Test
	public void testGetByID() {
		Company c = null;
		Company refCompany = new Company(1, "Apple Inc.");

		c = companyDao.getByID(1);
		Assert.assertEquals(refCompany, c);
	}

	@Test
	public void testGetAll() {
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyDao.getAll();
		Assert.assertEquals(5, companyList.size());
	}
	
	//delete a company where there isn't any computer of this kind
	@Test
	public void testDelete() {
		Connection connection = ConnectionManager.getConnection(true);
		int count = companyDao.getAll().size();
		Assert.assertEquals(5, count);

		companyDao.deleteId(5, connection);
		count = companyDao.getAll().size();
		Assert.assertEquals(4, count);
	}
}
