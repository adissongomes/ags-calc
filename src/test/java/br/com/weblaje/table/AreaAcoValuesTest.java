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
				.as(7.14).espacamento(7).diametro(8).build();
		assertEquals(data, values.getData(7.14));

		data = AreaAcoData.builder()
				.as(4.85).espacamento(6.5).diametro(6.3).build();
		assertEquals(data, values.getData(4.85));

	}
}
