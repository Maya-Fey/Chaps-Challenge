package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.ContractViolationException;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.commons.List2D;

/**
 * @author jamesmarston
 *
 */
class TestingCommons {

	/**
	 * Testing Contracts for null
	 * should throw error
	 */
	@Test
	void Test_not_null() {
		Contracts contracts = new Contracts();
		Object o = null;

		Assertions.assertThrows(ContractViolationException.class, () -> {
			Contracts.notNull(o, "null object passed");
		  });
	}

	/**
	 * Testing Contracts for non null
	 * test should not throw an error
	 */
	@Test
	void Test_Null() {
		Object o = new Object();

		Contracts.notNull(o, "non null object passed");
	}

	/**
	 * Testing Contracts for null file
	 * test should not throw an error
	 */
	@Test
	void Test_ExistsAndIsFile_noFile() {
		File f = new File("");
		Assertions.assertThrows(ContractViolationException.class, () -> {
			Contracts.existsAndIsFile(f, "no file found");
		  });
	}

	/**
	 * Testing Contracts for not null
	 * test should not throw an error
	 */
	@Test
	void Test_ExistsAndIsFile_hasFile() {
		File f = new File("testfile");

		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Contracts.existsAndIsFile(f, "no file found");
	}

	/**
	 * Testing Contracts for boolean
	 * test should throw an error
	 */
	@Test
	void Test_arbitrary_false() {
		Assertions.assertThrows(ContractViolationException.class, () -> {
			Contracts.arbitrary(false, "false value found");
		  });
	}

	/**
	 * Testing Contracts for boolean
	 * test should not throw an error
	 */
	@Test
	void Test_arbitrary_true() {
		Contracts.arbitrary(true, "false value found");
	}



	/**
	 * Testing List2d returns the value entered to a certain position
	 */
	@Test
	void Test_List2d() {
		List2D<String> array = new List2D<String>("a");
		array.set("max", 50, 50);

		assertTrue(array.get(50, 50).equals("max"));
	}

	/**
	 * Testing List2d value outside of range returns null_value which is "a"
	 */
	@Test
	void Test_List2d_returnVal() {
		List2D<String> array = new List2D<String>("a");
		array.set("min", 0, 0);
		array.set("max", 50, 50);

		assertTrue(array.get(50, 51).equals("a"));
	}

	/**
	 * Testing List2d to ensure export converts to a 2d array of type correctly
	 * any unset values should return as null
	 */
	@Test
	void Test_List2d_Export() {
		List2D<String> array = new List2D<String>("a");
		array.set("min", 0, 0);
		array.set("max", 2, 2);
		String[][] a = array.export(String[].class, String.class);

		assertTrue(a[0][0].equals("min"));
		assertTrue(a[2][2].equals("max"));
		assertTrue(a[1][1]==null);
	}








}
