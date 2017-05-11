package net.katagaitai.phpscan.interceptor.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_fetch_array;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_fetch_assoc;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_fetch_object;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_fetch_row;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_all;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_array;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_assoc;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_field;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_fields;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_object;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_fetch_row;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_result;
import net.katagaitai.phpscan.php.builtin.Mysqli.mysqli_stmt;
import net.katagaitai.phpscan.php.builtin.Pdo.PDOStatement;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_all;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_all_columns;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_array;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_assoc;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_object;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_result;
import net.katagaitai.phpscan.php.builtin.Pgsql.pg_fetch_row;
import net.katagaitai.phpscan.php.builtin.Sqlite.SQLiteResult;
import net.katagaitai.phpscan.php.builtin.Sqlite.SQLiteUnbuffered;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_fetch_all;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_fetch_array;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_fetch_object;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_fetch_single;
import net.katagaitai.phpscan.php.builtin.Sqlite.sqlite_fetch_string;
import net.katagaitai.phpscan.php.builtin.Sqlite3.SQLite3Result;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@Log4j2
@RequiredArgsConstructor
public class TaintInitializerDbAPI implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		PhpCallable callable = decorator.getDecrated();
		String functionName = SymbolUtils.getFunctionName(callable);
		Symbol resultSymbol = decorator.getResult();
		if (callable instanceof mysql_fetch_row
				|| callable instanceof mysql_fetch_array
				|| callable instanceof mysql_fetch_assoc
				|| callable instanceof mysql_fetch_object
				|| callable instanceof mysqli_result.fetch_field
				|| callable instanceof mysqli_result.fetch_field_direct
				|| callable instanceof mysqli_result.fetch_all
				|| callable instanceof mysqli_result.fetch_array
				|| callable instanceof mysqli_result.fetch_assoc
				|| callable instanceof mysqli_result.fetch_object
				|| callable instanceof mysqli_result.fetch_row
				|| callable instanceof mysqli_stmt.fetch
				|| callable instanceof mysqli_fetch_field
				|| callable instanceof mysqli_fetch_fields
				|| callable instanceof mysqli_fetch_all
				|| callable instanceof mysqli_fetch_array
				|| callable instanceof mysqli_fetch_assoc
				|| callable instanceof mysqli_fetch_object
				|| callable instanceof mysqli_fetch_row
				|| callable instanceof PDOStatement.fetch
				|| callable instanceof PDOStatement.fetchColumn
				|| callable instanceof PDOStatement.fetchAll
				|| callable instanceof PDOStatement.fetchObject
				|| callable instanceof pg_fetch_result
				|| callable instanceof pg_fetch_row
				|| callable instanceof pg_fetch_assoc
				|| callable instanceof pg_fetch_array
				|| callable instanceof pg_fetch_object
				|| callable instanceof pg_fetch_all
				|| callable instanceof pg_fetch_all_columns
				|| callable instanceof SQLiteResult.fetch
				|| callable instanceof SQLiteResult.fetchObject
				|| callable instanceof SQLiteResult.fetchSingle
				|| callable instanceof SQLiteResult.fetchAll
				|| callable instanceof SQLiteUnbuffered.fetch
				|| callable instanceof SQLiteUnbuffered.fetchObject
				|| callable instanceof SQLiteUnbuffered.fetchSingle
				|| callable instanceof SQLiteUnbuffered.fetchAll
				|| callable instanceof sqlite_fetch_array
				|| callable instanceof sqlite_fetch_object
				|| callable instanceof sqlite_fetch_single
				|| callable instanceof sqlite_fetch_string
				|| callable instanceof sqlite_fetch_all
				|| callable instanceof SQLite3Result.fetchArray) {
			log.debug("テイント初期化：" + resultSymbol);
			TaintUtils.applyInitialTaintRecursive(ip, resultSymbol, functionName);
		}
	}
}
