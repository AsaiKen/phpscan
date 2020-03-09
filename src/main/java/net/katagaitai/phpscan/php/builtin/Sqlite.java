package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Spl.RuntimeException;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Sqlite extends BuiltinBase {
	public Sqlite(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\SQLITE_BOTH", operator.createSymbol(3));
		putConstant("\\SQLITE_NUM", operator.createSymbol(2));
		putConstant("\\SQLITE_ASSOC", operator.createSymbol(1));
		putConstant("\\SQLITE_OK", operator.createSymbol(0));
		putConstant("\\SQLITE_ERROR", operator.createSymbol(1));
		putConstant("\\SQLITE_INTERNAL", operator.createSymbol(2));
		putConstant("\\SQLITE_PERM", operator.createSymbol(3));
		putConstant("\\SQLITE_ABORT", operator.createSymbol(4));
		putConstant("\\SQLITE_BUSY", operator.createSymbol(5));
		putConstant("\\SQLITE_LOCKED", operator.createSymbol(6));
		putConstant("\\SQLITE_NOMEM", operator.createSymbol(7));
		putConstant("\\SQLITE_READONLY", operator.createSymbol(8));
		putConstant("\\SQLITE_INTERRUPT", operator.createSymbol(9));
		putConstant("\\SQLITE_IOERR", operator.createSymbol(10));
		putConstant("\\SQLITE_CORRUPT", operator.createSymbol(11));
		putConstant("\\SQLITE_NOTFOUND", operator.createSymbol(12));
		putConstant("\\SQLITE_FULL", operator.createSymbol(13));
		putConstant("\\SQLITE_CANTOPEN", operator.createSymbol(14));
		putConstant("\\SQLITE_PROTOCOL", operator.createSymbol(15));
		putConstant("\\SQLITE_EMPTY", operator.createSymbol(16));
		putConstant("\\SQLITE_SCHEMA", operator.createSymbol(17));
		putConstant("\\SQLITE_TOOBIG", operator.createSymbol(18));
		putConstant("\\SQLITE_CONSTRAINT", operator.createSymbol(19));
		putConstant("\\SQLITE_MISMATCH", operator.createSymbol(20));
		putConstant("\\SQLITE_MISUSE", operator.createSymbol(21));
		putConstant("\\SQLITE_NOLFS", operator.createSymbol(22));
		putConstant("\\SQLITE_AUTH", operator.createSymbol(23));
		putConstant("\\SQLITE_NOTADB", operator.createSymbol(26));
		putConstant("\\SQLITE_FORMAT", operator.createSymbol(24));
		putConstant("\\SQLITE_ROW", operator.createSymbol(100));
		putConstant("\\SQLITE_DONE", operator.createSymbol(101));
	}

	public static class SQLiteDatabase extends PhpClassBase {

		public SQLiteDatabase(Interpreter ip) {
			super(ip);
		}

		// final public function __construct ($filename, $mode = 0666, &$error_message) {}

		// public function query ($query, $result_type, &$error_message) {}
		public static class query implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.createSymbol(operator.createPhpResource());
			}
		}

		// public function queryExec ($query, &$error_message) {}
		public static class queryExec implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function arrayQuery ($query, $result_type, $decode_binary) {}
		public static class arrayQuery implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array(operator.array());
			}
		}

		// public function singleQuery ($query, $first_row_only, $decode_binary) {}
		public static class singleQuery implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function unbufferedQuery ($query, $result_type = SQLITE_BOTH, &$error_message) {}
		public static class unbufferedQuery implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.createSymbol(operator.createPhpResource());
			}
		}

		// public function lastInsertRowid () {}
		public static class lastInsertRowid implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function changes () {}
		public static class changes implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function createAggregate ($function_name, $step_func, $finalize_func, $num_args = -1) {}
		// http://php.net/manual/ja/sqlite3.createaggregate.php
		public static class createAggregate implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				List<Symbol> dummyArgList = Lists.newArrayList(operator.array());
				for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE - 1; j++) {
					dummyArgList.add(operator.string());
				}
				Symbol callbackSymbol1 = SymbolUtils.getArgument(ip, 1);
				SymbolUtils.callCallable(ip, callbackSymbol1, dummyArgList);
				Symbol callbackSymbol2 = SymbolUtils.getArgument(ip, 2);
				SymbolUtils.callCallable(ip, callbackSymbol2, dummyArgList);
				return operator.bool();
			}
		}

		// public function createFunction ($function_name, $callback, $num_args = -1) {}
		// http://php.net/manual/ja/sqlite3.createfunction.php
		public static class createFunction implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
				List<Symbol> dummyArgList = Lists.newArrayList();
				for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE; j++) {
					dummyArgList.add(operator.string());
				}
				SymbolUtils.callCallable(ip, callbackSymbol, dummyArgList);
				return operator.bool();
			}
		}

		// public function busyTimeout ($milliseconds) {}
		public static class busyTimeout implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function lastError () {}
		public static class lastError implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function fetchColumnTypes ($table_name, $result_type = SQLITE_ASSOC) {}
		public static class fetchColumnTypes implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}
	}

	public static class SQLiteResult extends PhpClassBase {
		public SQLiteResult(Interpreter ip) {
			super(ip);
		}

		// public function fetch ($result_type = SQLITE_BOTH, $decode_binary =  true) {}
		public static class fetch implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function fetchObject ($class_name, $ctor_params, $decode_binary = true) {}
		public static class fetchObject implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.object();
			}
		}

		// public function fetchSingle ($decode_binary = true) {}
		public static class fetchSingle implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function fetchAll ($result_type, array $ctor_params,  $decode_binary = true) {}
		public static class fetchAll implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array(operator.array());
			}
		}

		// public function column ($index_or_name, $decode_binary = true) {}
		public static class column implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function numFields () {}
		public static class numFields implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function fieldName ($field_index) {}
		public static class fieldName implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function current ($result_type = SQLITE_BOTH , $decode_binary = true) {}
		public static class current implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function key () {}
		public static class key implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function next () {}
		public static class next implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function valid () {}
		public static class valid implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function rewind () {}
		public static class rewind implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.null_();
			}
		}

		// public function count () {}
		public static class count implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function prev () {}
		public static class prev implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function hasPrev () {}
		public static class hasPrev implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function numRows () {}
		public static class numRows implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function seek ($row) {}
		public static class seek implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}
	}

	public static class SQLiteUnbuffered extends PhpClassBase {
		public SQLiteUnbuffered(Interpreter ip) {
			super(ip);
		}

		// public function fetch ($result_type, $decode_binary) {}
		public static class fetch implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function fetchObject ($class_name, $ctor_params, $decode_binary) {}
		public static class fetchObject implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.object();
			}
		}

		// public function fetchSingle ($decode_binary) {}
		public static class fetchSingle implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function fetchAll ($result_type, $decode_binary) {}
		public static class fetchAll implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array(operator.array());
			}
		}

		// public function column ($index_or_name, $decode_binary) {}
		public static class column implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function numFields () {}
		public static class numFields implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function fieldName ($field_index) {}
		public static class fieldName implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function current ($result_type, $decode_binary) {}
		public static class current implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function next () {}
		public static class next implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function valid () {}
		public static class valid implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

	}

	public static class SQLiteException extends PhpClassBase {
		public SQLiteException(Interpreter ip) {
			super(ip);
		}

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
		}
	}

	// function sqlite_open ($filename, $mode = null, &$error_message = null) {}
	public static class sqlite_open implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function sqlite_popen ($filename, $mode = null, &$error_message = null) {}
	public static class sqlite_popen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function sqlite_close ($dbhandle) {}
	public static class sqlite_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function sqlite_query ($query, $dbhandle, $result_type = null, &$error_msg = SQLITE_BOTH) {}
	public static class sqlite_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function sqlite_exec ($dbhandle, $query, &$error_msg = null) {}
	public static class sqlite_exec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_array_query ($dbhandle, $query, $result_type = null, $decode_binary = null) {}
	public static class sqlite_array_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.array());
		}
	}

	// function sqlite_single_query ($db, $query, $first_row_only = null, $decode_binary = null) {}
	public static class sqlite_single_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function sqlite_fetch_array ($result, $result_type = SQLITE_BOTH, $decode_binary = null) {}
	public static class sqlite_fetch_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function sqlite_fetch_object ($result, $class_name = null, array $ctor_params = null, $decode_binary = null) {}
	public static class sqlite_fetch_object implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function sqlite_fetch_single ($result, $decode_binary = null) {}
	public static class sqlite_fetch_single implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_fetch_string ($result, $decode_binary) {}
	public static class sqlite_fetch_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_fetch_all ($result_type = null, $decode_binary = null) {}
	public static class sqlite_fetch_all implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.array());
		}
	}

	// function sqlite_current ($result, $result_type = null, $decode_binary = null) {}
	public static class sqlite_current implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function sqlite_column ($result, $index_or_name, $decode_binary = null) {}
	public static class sqlite_column implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_libversion () {}
	public static class sqlite_libversion implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_libencoding () {}
	public static class sqlite_libencoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_changes ($db) {}
	public static class sqlite_changes implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqlite_last_insert_rowid ($dbhandle) {}
	public static class sqlite_last_insert_rowid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqlite_num_rows ($result) {}
	public static class sqlite_num_rows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqlite_num_fields ($result) {}
	public static class sqlite_num_fields implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqlite_field_name ($result, $field_index) {}
	public static class sqlite_field_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_seek ($result, $rownum) {}
	public static class sqlite_seek implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_rewind ($result) {}
	public static class sqlite_rewind implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_next ($result) {}
	public static class sqlite_next implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_prev ($result) {}
	public static class sqlite_prev implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_valid ($result) {}
	public static class sqlite_valid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_has_more ($result) {}
	public static class sqlite_has_more implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_has_prev ($result) {}
	public static class sqlite_has_prev implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sqlite_escape_string ($item) {}
	public static class sqlite_escape_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function sqlite_busy_timeout ($dbhandle, $milliseconds) {}
	public static class sqlite_busy_timeout implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function sqlite_last_error ($dbhandle) {}
	public static class sqlite_last_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqlite_error_string ($error_code) {}
	public static class sqlite_error_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function sqlite_unbuffered_query ($dbhandle, $query, $result_type = SQLITE_BOTH, &$error_msg = null) {}
	public static class sqlite_unbuffered_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function sqlite_create_aggregate ($dbhandle, $function_name, $step_func, $finalize_func, $num_args = null) {}
	public static class sqlite_create_aggregate implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			List<Symbol> dummyArgList = Lists.newArrayList(operator.array());
			for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE - 1; j++) {
				dummyArgList.add(operator.string());
			}
			Symbol callbackSymbol1 = SymbolUtils.getArgument(ip, 2);
			SymbolUtils.callCallable(ip, callbackSymbol1, dummyArgList);
			Symbol callbackSymbol2 = SymbolUtils.getArgument(ip, 3);
			SymbolUtils.callCallable(ip, callbackSymbol2, dummyArgList);
			return operator.null_();
		}
	}

	// function sqlite_create_function ($dbhandle, $function_name, $callback, $num_args = null) {}
	public static class sqlite_create_function implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol callbackSymbol = SymbolUtils.getArgument(ip, 2);
			List<Symbol> dummyArgList = Lists.newArrayList();
			for (int j = 0; j < Constants.DUMMY_ARRAY_SIZE; j++) {
				dummyArgList.add(operator.string());
			}
			SymbolUtils.callCallable(ip, callbackSymbol, dummyArgList);
			return operator.null_();
		}
	}

	// function sqlite_factory ($filename, $mode = null, &$error_message = null) {}
	public static class sqlite_factory implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SQLiteDatabase clazz = new SQLiteDatabase(ip);
			return SymbolUtils.new_(ip, clazz, Lists.newArrayList());
		}
	}

	// function sqlite_udf_encode_binary ($data) {}
	public static class sqlite_udf_encode_binary implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function sqlite_udf_decode_binary ($data) {}
	public static class sqlite_udf_decode_binary implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function sqlite_fetch_column_types ($dbhandle, $table_name, $result_type = null) {}
	public static class sqlite_fetch_column_types implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

}
