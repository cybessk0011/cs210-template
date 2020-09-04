package grade;

import org.junit.jupiter.api.BeforeAll;

public class Module0 extends SQLModule {
	@BeforeAll
	public static void setup() {
		module_tag = "M0";
		
		query_data = new Object[][]{
			// DEFAULT COLUMNS
			{ true,  "_squares", "SQUARES BELOW 20", null },
			{ true,  "_squares", "SQUARES BELOW 16", "the integer is a strict upper bound" },
			{ true,  "_squares", "SQUARES BELOW 0", "there are no non-positive squares" },
			{ true,  "_squares", "SQUARES BELOW 1000", "the integer data type is unbounded" },
			
			// BASE NAME WITH DEFAULT POWER NAME
			{ true,  "_squares", "SQUARES BELOW 500 AS a", null },
			{ true,  "_squares", "SQUARES BELOW 100 AS a", null },
			{ true,  "_squares", "SQUARES BELOW 100 AS x", "custom name can be the default name" },
			
			// BASE AND POWER NAMES
			{ true,  "_squares", "SQUARES BELOW 1000 AS a, b", null },
			{ true,  "_squares", "SQUARES BELOW 50 AS a, b", null },
			{ false, "_squares", "SQUARES BELOW 50 AS a, a", "custom column names must be different" },
			
			// SYNTAX
			{ true,  "_squares", "squares below 20 as A, B", "keywords are not case sensitive" },
			{ true,  "_squares", "SQUARES  BELOW  20 AS a ,b ", "additional whitespace is permitted" },
			{ false, "_squares", "SQUARESBELOW20ASa,b", "delineating whitespace is required" },
			{ false, "_squares", "SQUARES 20", "the BELOW keyword is required" },
			{ false, "_squares", "BELOW 20", "the SQUARES keyword is required" },
			{ false, "_squares", "SQUARES BELOW AS a, b", "the integer is required" },
			{ false, "_squares", "SQUARES BELOW 20 AS a b", "the comma is required before a power name" },
			{ false, "_squares", "SQUARES BELOW 20 AS a,", "the comma is forbidden without a power name" },
			{ false, "_squares", "SQUARES BELOW 20 AS , b", "the base name is required" },
			
			// ROBUSTNESS
			{ true,  null, "ECHO \"Hello, world!\"", "existing drivers should still function" }
		};
		
		serial_data = new Object[][]{
			{ "_squares", 2, 0, "x", "x_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16 },
			{ "_squares", 2, 0, "x", "x_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9 },
			{ "_squares", 2, 0, "x", "x_squared", "integer", "integer" },
			{ "_squares", 2, 0, "x", "x_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49, 8, 64, 9, 81, 10, 100, 11, 121, 12, 144, 13, 169, 14, 196, 15, 225, 16, 256, 17, 289, 18, 324, 19, 361, 20, 400, 21, 441, 22, 484, 23, 529, 24, 576, 25, 625, 26, 676, 27, 729, 28, 784, 29, 841, 30, 900, 31, 961 },
			{ "_squares", 2, 0, "a", "a_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49, 8, 64, 9, 81, 10, 100, 11, 121, 12, 144, 13, 169, 14, 196, 15, 225, 16, 256, 17, 289, 18, 324, 19, 361, 20, 400, 21, 441, 22, 484 },
			{ "_squares", 2, 0, "a", "a_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49, 8, 64, 9, 81 },
			{ "_squares", 2, 0, "x", "x_squared", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49, 8, 64, 9, 81 },
			{ "_squares", 2, 0, "a", "b", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49, 8, 64, 9, 81, 10, 100, 11, 121, 12, 144, 13, 169, 14, 196, 15, 225, 16, 256, 17, 289, 18, 324, 19, 361, 20, 400, 21, 441, 22, 484, 23, 529, 24, 576, 25, 625, 26, 676, 27, 729, 28, 784, 29, 841, 30, 900, 31, 961 },
			{ "_squares", 2, 0, "a", "b", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16, 5, 25, 6, 36, 7, 49 },
			null,
			{ "_squares", 2, 0, "A", "B", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16 },
			{ "_squares", 2, 0, "a", "b", "integer", "integer", 0, 0, 1, 1, 2, 4, 3, 9, 4, 16 },
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null
		};
	}
}