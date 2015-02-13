package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerServiceImplTest {
		@Mock private Company cmp;

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
}
