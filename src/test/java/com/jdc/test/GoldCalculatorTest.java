package com.jdc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jdc.calc.GoldCalculator;
import com.jdc.calc.GoldCalculator.Purity;
import com.jdc.calc.GoldCalculator.Unit;

@TestMethodOrder(value = OrderAnnotation.class)
public class GoldCalculatorTest {

	@Test
	@Order(1)
	void should_throw_error_when_no_reference_price() {
		var calc = new GoldCalculator();
		var error = assertThrows(IllegalStateException.class, () -> calc.getPrice(Purity.Sixteen));
		assertEquals("There is no reference price for calculator", error.getMessage());

	}
	@Test
	@Order(2)
   void should_throw_error_when_no_purity() {
		var calc = new GoldCalculator();
		calc.setReferencePrice(2000000);
		var error=assertThrows(IllegalArgumentException.class, ()->calc.getPrice(null));
		assertEquals("Purity must not be null", error.getMessage());
   }
	@Test
	@Order(3)
	void should_throw_error_when_no_data() {
		var calc=new GoldCalculator();
		calc.setReferencePrice(2000000);
	    var error=assertThrows(IllegalArgumentException.class, ()->calc.getPrice(null, null, 0));
	    assertEquals("data must not be empty",error.getMessage());
	}
	@ParameterizedTest
	@Order(4)
	@CsvSource(value = { 
			"2000000,1875000,1750000", 
			"1900000,1781250,1662500", 
			"1800000,1687500,1575000",
			"1600000,1500000,1400000", 
			"1500000,1406250,1312500" })

	void show_calculate_price_per_purity(int refPrice, int fifteen, int fourteen) {
		var calc = new GoldCalculator();
		calc.setReferencePrice(refPrice);
		assertEquals(refPrice, calc.getPrice(Purity.Sixteen));
		assertEquals(fifteen, calc.getPrice(Purity.Fifteen));
		assertEquals(fourteen, calc.getPrice(Purity.Fourteen));

	}
	@ParameterizedTest
	@Order(5)
	@CsvSource(value= {
			"2000000,2000000,Sixteen,Kyat,1",
			"1900000,519530,Fourteen,Pae,5",
			"1900000,3562500,Fifteen,Kyat,2",
			"1800000,375000,Sixteen,Ywae,25",
			"2000000,937496,Fifteen,Pae,8"
			
		
	})
	void show_calculate_price_per_unit(int refPrice,int totalPrice,Purity purity,Unit unit,int weight) {
		var calc = new GoldCalculator();
		calc.setReferencePrice(refPrice);
		assertEquals(totalPrice, calc.getPrice(purity,unit,weight));
		
	}
}
