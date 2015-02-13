package models;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerTest {
	@Mock private Company cmp;
	@Mock private Company cmp2;

	@Test
	public void testComputerConstructor() {
		int id = 12;
		String name = "test";
		LocalDate date = LocalDate.of(2015, Month.JANUARY, 1);

		Computer c = new Computer();
		Computer c1 = new Computer(id, name, date, date, cmp);

		assertNotNull(c);
		assertNotNull(c1);
	}

	@Test
	public void testComputerGetter() {
		int id = 12;
		String name = "test";
		LocalDate date = LocalDate.of(2015, Month.JANUARY, 1);

		Computer c1 = new Computer(id, name, date, date, cmp);

		assertEquals(id, c1.getId());
		assertNotNull(c1.getCompany());
		assertEquals(name, c1.getName());
		assertEquals(date, c1.getDiscontinued());
		assertEquals(date, c1.getIntroduced());		
		assertEquals(cmp, c1.getCompany());

	}

	@Test
	public void testComputerSetter() {
		int id = 12;
		String name = "test";
		LocalDate dateI = LocalDate.of(2018, Month.MARCH, 6);
		LocalDate dateD = LocalDate.of(2019, Month.FEBRUARY, 9);

		Computer c1 = new Computer(id, name, dateI, dateD, cmp);

		assertEquals(id, c1.getId());
		assertNotNull(c1.getCompany());
		assertEquals(name, c1.getName());
		assertEquals(dateD, c1.getDiscontinued());
		assertEquals(dateI, c1.getIntroduced());
		assertEquals(cmp, c1.getCompany());

		int id2 = 12;
		String name2 = "test2";
		LocalDate dateI2 = LocalDate.of(2011, Month.MARCH, 1);
		LocalDate dateD2 = LocalDate.of(2014, Month.FEBRUARY, 1);

		c1.setName(name2);
		c1.setDiscontinued(dateD2);
		c1.setIntroduced(dateI2);
		c1.setId(id2);
		c1.setCompany(cmp2);
		
		assertEquals(id2, c1.getId());
		assertEquals(name2, c1.getName());
		assertEquals(dateD2, c1.getDiscontinued());
		assertEquals(dateI2, c1.getIntroduced());		
		assertEquals(cmp2, c1.getCompany());

	}
}
