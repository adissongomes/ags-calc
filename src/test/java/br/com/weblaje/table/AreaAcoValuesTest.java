package br.com.weblaje.table;

import br.com.weblaje.table.AreaAcoValues.AreaAcoData;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AreaAcoValuesTest {

	@BeforeClass
	public static void setUp() {
		AreaAcoValues.getInstance();
	}

	@Test
	public void getData() {

		AreaAcoValues values = AreaAcoValues.getInstance();

		AreaAcoData data = AreaAcoData.builder()
				.as(7.14).espacamento(17.5).diametro(12.5).build();
		assertEquals(data, values.getData(7.14));

	}
}
