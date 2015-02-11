package dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDaoImplTest {


	@Test
	public void testGetComputerNegativeID() {
		Computer c = null;
		c = ComputerDaoImpl.INSTANCE.getByID(-1);
		Assert.assertNull(c);
	}
	
	@Test
	public void testGetComputerBadID() {
		Computer c = null;
		c = ComputerDaoImpl.INSTANCE.getByID(99999);
		Assert.assertNull(c);
	}
	
	@Test
	public void testGetComputerID() {
		Computer c = null;
		c = ComputerDaoImpl.INSTANCE.getByID(12);
		Assert.assertEquals(c.getName(), "fdsfsd");
	}
}
