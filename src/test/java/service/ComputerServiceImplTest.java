package service;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceImplTest {
		@Mock private Company cmp;
		@Mock private Computer computer;

		@Test
		public void testComputerConstructor() {
			int id = 12;
			String name = "test";
			LocalDate date = LocalDate.of(2015, Month.JANUARY, 1);

			Computer c = new Computer();
			Computer c1 = new Computer(id, name, date, date, cmp);

			Assert.assertNotNull(c);
			Assert.assertNotNull(c1);
		}
//		
//		List<Computer> computerList = new ArrayList<Computer>();
//		for( int i = 0 ; i<20 ; i++)
//		{
//			computerList.add(computer);
//		}
}
