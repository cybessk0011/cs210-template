package grade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import apps.Database;
import types.Map;
import types.Response;
import types.SearchList;
import types.Table;

public abstract class SQLModule {
	protected static Database DB = new Database();
	protected static int passed = 0, total = 0;
	protected static String module_tag;
	protected static Object[][] query_data;
	protected static Object[][] serial_data;
	protected static Table actual_table;

	protected static Arguments[] data() {
		var arguments = new Arguments[query_data.length];

		for (var a = 0; a < arguments.length; a++) {
			Table table = null;

			if (serial_data[a] != null) {
				var i = 0;

				var table_name = (String) serial_data[a][i++];

				var schema_size = (Integer) serial_data[a][i++];
				var primary_index = (Integer) serial_data[a][i++];

				var column_names = new LinkedList<String>();
				for (var j = 1; j <= schema_size; j++)
					column_names.add((String) serial_data[a][i++]);

				var column_types = new LinkedList<String>();
				for (var j = 1; j <= schema_size; j++)
					column_types.add((String) serial_data[a][i++]);

				Map<String, Object> schema = SearchList.of(
					"table_name", table_name,
					"column_names", column_names,
					"column_types", column_types,
					"primary_index", primary_index
				);

				Map<Object, List<Object>> state = new SearchList<>();

				for (var j = i; j < serial_data[a].length; j += schema_size) {
					var key = serial_data[a][j+primary_index];
					var value = new LinkedList<>();

					for (var k = 0; k < schema_size; k++)
						value.add(serial_data[a][j+k]);

					state.put(key, value);
				}

				table = new Table(schema, state);
			}

			arguments[a] = Arguments.of(
				query_data[a][0],
				query_data[a][1],
				query_data[a][2],
				query_data[a][3],
				table
			);
		}

		return arguments;
	}

	@DisplayName("Queries")
	@ParameterizedTest(name = "[{index}] {2}")
	@MethodSource("data")
	protected void testQuery(
		boolean success,
		String table_name,
		String sql,
		String reason,
		Table expected_table
	) {
		total++;

		System.out.println(sql);

		var queries = Arrays.asList(sql.split("\\s*;\\s*"));
		var count = queries.size();

		List<Response> responses;
		try {
			responses = DB.interpret(queries);
		}
		catch (Exception e) {
			fail("Interpreter must not throw exceptions", e);
			return;
		}

		Response last;
		try {
			last = responses.get(responses.size()-1);
		}
		catch (Exception e) {
			fail("Interpreter must return non-null and non-empty list of responses");
			return;
		}

		assertEquals(
			count,
			responses.stream().filter(it -> it != null).count(),
			String.format(
				"%s returned list with wrong number of non-null responses,",
				count == 1 ? "Query" : "Script"
			)
		);

		assertEquals(
			count == 1 ? success : Stream.concat(Stream.generate(() -> true).limit(count-1), Stream.of(success)).collect(Collectors.toList()),
			count == 1 ? last.success() : responses.stream().map(it -> it.success()).collect(Collectors.toList()),
			String.format(
				"%s %s was expected to %s, reason: <%s>, message: <%s>,",
				success ? "Valid" : "Invalid",
				count == 1 ? "query" : "script",
				(count == 1
					? (success ? "succeed" : "fail")
					: (success ? "succeed for all queries" : "fail only on last query")),
				reason != null ? reason : "none provided",
				last.message() != null ? last.message() : "none returned"
			)
		);

		actual_table = null;

		String friendly_name = null;
		if (table_name != null) {
			if (table_name.startsWith("_")) {
				actual_table = last.table();
				friendly_name = String.format("result table <%s>", table_name);
			}
			else {
				actual_table = DB.tables().get(table_name);
				friendly_name = String.format("table <%s> in the database", table_name);
			}
		}

		if (expected_table != null) {
			assertNotNull(
				actual_table,
				String.format(
					"%s is null,",
					friendly_name
				)
			);

			if (expected_table.schema() != null) {
				assertNotNull(
					actual_table.schema(),
					String.format(
						"%s has null schema,",
						friendly_name
					)
				);

				if (actual_table.schema() != null) {
					assertEquals(
						expected_table.schema().get("table_name"),
						actual_table.schema().get("table_name"),
						String.format(
							"%s has incorrect table name in schema,",
							friendly_name
						)
					);

					assertEquals(
						expected_table.schema().get("column_names"),
						actual_table.schema().get("column_names"),
						String.format(
							"%s has incorrect column names in schema,",
							friendly_name
						)
					);

					assertEquals(
						expected_table.schema().get("column_types"),
						actual_table.schema().get("column_types"),
						String.format(
							"%s has incorrect column types in schema,",
							friendly_name
						)
					);

					assertEquals(
						expected_table.schema().get("primary_index"),
						actual_table.schema().get("primary_index"),
						String.format(
							"%s has incorrect primary index in schema,",
							friendly_name
						)
					);
				}
			}

			if (expected_table.state() != null) {
				assertNotNull(
					actual_table.state(),
					String.format(
						"%s has null state,",
						friendly_name
					)
				);

				if (actual_table.state() != null) {
					assertEquals(
						expected_table.state(),
						actual_table.state(),
						String.format(
							"%s has incorrect rows in state,",
							friendly_name
						)
					);
				}
			}
		}

		passed++;
	}

	@AfterAll
	protected static void report() throws IOException {
		System.out.printf(
			"[%s PASSED %d%% OF UNIT TESTS]\n",
			module_tag,
			(int) Math.ceil(passed / (double) total * 100)
		);

		DB.close();
	}
}