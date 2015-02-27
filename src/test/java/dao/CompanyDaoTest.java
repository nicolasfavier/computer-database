package dao;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.models.Company;
import com.nicolas.utils.ScriptRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-context-persistence-test.xml" })
public class CompanyDaoTest {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Before
	public void init() {
		// reset database
		ScriptRunner.runScript();
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
		int count = companyDao.getAll().size();
		Assert.assertEquals(5, count);

		companyDao.deleteId(5);
		count = companyDao.getAll().size();
		Assert.assertEquals(4, count);
	}
}
