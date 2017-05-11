package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpCallableStatic;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Spl.RuntimeException;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Pdo extends BuiltinBase {
	public Pdo(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\PDO::PARAM_BOOL", operator.createSymbol(5));
		putConstant("\\PDO::PARAM_NULL", operator.createSymbol(0));
		putConstant("\\PDO::PARAM_INT", operator.createSymbol(1));
		putConstant("\\PDO::PARAM_STR", operator.createSymbol(2));
		putConstant("\\PDO::PARAM_LOB", operator.createSymbol(3));
		putConstant("\\PDO::PARAM_STMT", operator.createSymbol(4));
		putConstant("\\PDO::PARAM_INPUT_OUTPUT", operator.createSymbol(2147483648L));
		putConstant("\\PDO::PARAM_EVT_ALLOC", operator.createSymbol(0));
		putConstant("\\PDO::PARAM_EVT_FREE", operator.createSymbol(1));
		putConstant("\\PDO::PARAM_EVT_EXEC_PRE", operator.createSymbol(2));
		putConstant("\\PDO::PARAM_EVT_EXEC_POST", operator.createSymbol(3));
		putConstant("\\PDO::PARAM_EVT_FETCH_PRE", operator.createSymbol(4));
		putConstant("\\PDO::PARAM_EVT_FETCH_POST", operator.createSymbol(5));
		putConstant("\\PDO::PARAM_EVT_NORMALIZE", operator.createSymbol(6));
		putConstant("\\PDO::FETCH_LAZY", operator.createSymbol(1));
		putConstant("\\PDO::FETCH_ASSOC", operator.createSymbol(2));
		putConstant("\\PDO::FETCH_NUM", operator.createSymbol(3));
		putConstant("\\PDO::FETCH_BOTH", operator.createSymbol(4));
		putConstant("\\PDO::FETCH_OBJ", operator.createSymbol(5));
		putConstant("\\PDO::FETCH_BOUND", operator.createSymbol(6));
		putConstant("\\PDO::FETCH_COLUMN", operator.createSymbol(7));
		putConstant("\\PDO::FETCH_CLASS", operator.createSymbol(8));
		putConstant("\\PDO::FETCH_INTO", operator.createSymbol(9));
		putConstant("\\PDO::FETCH_FUNC", operator.createSymbol(10));
		putConstant("\\PDO::FETCH_GROUP", operator.createSymbol(65536));
		putConstant("\\PDO::FETCH_UNIQUE", operator.createSymbol(196608));
		putConstant("\\PDO::FETCH_KEY_PAIR", operator.createSymbol(12));
		putConstant("\\PDO::FETCH_CLASSTYPE", operator.createSymbol(262144));
		putConstant("\\PDO::FETCH_SERIALIZE", operator.createSymbol(524288));
		putConstant("\\PDO::FETCH_PROPS_LATE", operator.createSymbol(1048576));
		putConstant("\\PDO::FETCH_NAMED", operator.createSymbol(11));
		putConstant("\\PDO::ATTR_AUTOCOMMIT", operator.createSymbol(0));
		putConstant("\\PDO::ATTR_PREFETCH", operator.createSymbol(1));
		putConstant("\\PDO::ATTR_TIMEOUT", operator.createSymbol(2));
		putConstant("\\PDO::ATTR_ERRMODE", operator.createSymbol(3));
		putConstant("\\PDO::ATTR_SERVER_VERSION", operator.createSymbol(4));
		putConstant("\\PDO::ATTR_CLIENT_VERSION", operator.createSymbol(5));
		putConstant("\\PDO::ATTR_SERVER_INFO", operator.createSymbol(6));
		putConstant("\\PDO::ATTR_CONNECTION_STATUS", operator.createSymbol(7));
		putConstant("\\PDO::ATTR_CASE", operator.createSymbol(8));
		putConstant("\\PDO::ATTR_CURSOR_NAME", operator.createSymbol(9));
		putConstant("\\PDO::ATTR_CURSOR", operator.createSymbol(10));
		putConstant("\\PDO::ATTR_ORACLE_NULLS", operator.createSymbol(11));
		putConstant("\\PDO::ATTR_PERSISTENT", operator.createSymbol(12));
		putConstant("\\PDO::ATTR_STATEMENT_CLASS", operator.createSymbol(13));
		putConstant("\\PDO::ATTR_FETCH_TABLE_NAMES", operator.createSymbol(14));
		putConstant("\\PDO::ATTR_FETCH_CATALOG_NAMES", operator.createSymbol(15));
		putConstant("\\PDO::ATTR_DRIVER_NAME", operator.createSymbol(16));
		putConstant("\\PDO::ATTR_STRINGIFY_FETCHES", operator.createSymbol(17));
		putConstant("\\PDO::ATTR_MAX_COLUMN_LEN", operator.createSymbol(18));
		putConstant("\\PDO::ATTR_EMULATE_PREPARES", operator.createSymbol(20));
		putConstant("\\PDO::ATTR_DEFAULT_FETCH_MODE", operator.createSymbol(19));
		putConstant("\\PDO::ERRMODE_SILENT", operator.createSymbol(0));
		putConstant("\\PDO::ERRMODE_WARNING", operator.createSymbol(1));
		putConstant("\\PDO::ERRMODE_EXCEPTION", operator.createSymbol(2));
		putConstant("\\PDO::CASE_NATURAL", operator.createSymbol(0));
		putConstant("\\PDO::CASE_LOWER", operator.createSymbol(2));
		putConstant("\\PDO::CASE_UPPER", operator.createSymbol(1));
		putConstant("\\PDO::NULL_NATURAL", operator.createSymbol(0));
		putConstant("\\PDO::NULL_EMPTY_STRING", operator.createSymbol(1));
		putConstant("\\PDO::NULL_TO_STRING", operator.createSymbol(2));
		putConstant("\\PDO::ERR_NONE", operator.createSymbol(00000));
		putConstant("\\PDO::FETCH_ORI_NEXT", operator.createSymbol(0));
		putConstant("\\PDO::FETCH_ORI_PRIOR", operator.createSymbol(1));
		putConstant("\\PDO::FETCH_ORI_FIRST", operator.createSymbol(2));
		putConstant("\\PDO::FETCH_ORI_LAST", operator.createSymbol(3));
		putConstant("\\PDO::FETCH_ORI_ABS", operator.createSymbol(4));
		putConstant("\\PDO::FETCH_ORI_REL", operator.createSymbol(5));
		putConstant("\\PDO::CURSOR_FWDONLY", operator.createSymbol(0));
		putConstant("\\PDO::CURSOR_SCROLL", operator.createSymbol(1));
		putConstant("\\PDO::MYSQL_ATTR_USE_BUFFERED_QUERY", operator.createSymbol(1000));
		putConstant("\\PDO::MYSQL_ATTR_LOCAL_INFILE", operator.createSymbol(1001));
		putConstant("\\PDO::MYSQL_ATTR_INIT_COMMAND", operator.createSymbol(1002));
		putConstant("\\PDO::MYSQL_ATTR_MAX_BUFFER_SIZE", operator.createSymbol(1005));
		putConstant("\\PDO::MYSQL_ATTR_READ_DEFAULT_FILE", operator.createSymbol(1003));
		putConstant("\\PDO::MYSQL_ATTR_READ_DEFAULT_GROUP", operator.createSymbol(1004));
		putConstant("\\PDO::MYSQL_ATTR_COMPRESS", operator.createSymbol(1006));
		putConstant("\\PDO::MYSQL_ATTR_DIRECT_QUERY", operator.createSymbol(1007));
		putConstant("\\PDO::MYSQL_ATTR_FOUND_ROWS", operator.createSymbol(1008));
		putConstant("\\PDO::MYSQL_ATTR_IGNORE_SPACE", operator.createSymbol(1009));
		putConstant("\\PDO::MYSQL_ATTR_SSL_KEY", operator.createSymbol(1010));
		putConstant("\\PDO::MYSQL_ATTR_SSL_CERT", operator.createSymbol(1011));
		putConstant("\\PDO::MYSQL_ATTR_SSL_CA", operator.createSymbol(1012));
		putConstant("\\PDO::MYSQL_ATTR_SSL_CAPATH", operator.createSymbol(1013));
		putConstant("\\PDO::MYSQL_ATTR_SSL_CIPHER", operator.createSymbol(1014));
		putConstant("\\PDO::PGSQL_ATTR_DISABLE_NATIVE_PREPARED_STATEMENT", operator.createSymbol(1000));
		putConstant("\\PDO::PGSQL_TRANSACTION_IDLE", operator.createSymbol(0));
		putConstant("\\PDO::PGSQL_TRANSACTION_ACTIVE", operator.createSymbol(1));
		putConstant("\\PDO::PGSQL_TRANSACTION_INTRANS", operator.createSymbol(2));
		putConstant("\\PDO::PGSQL_TRANSACTION_INERROR", operator.createSymbol(3));
		putConstant("\\PDO::PGSQL_TRANSACTION_UNKNOWN", operator.createSymbol(4));
		putConstant("\\PDO::PGSQL_CONNECT_ASYNC", operator.createSymbol(4));
		putConstant("\\PDO::PGSQL_CONNECTION_AUTH_OK", operator.createSymbol(5));
		putConstant("\\PDO::PGSQL_CONNECTION_AWAITING_RESPONSE", operator.createSymbol(4));
		putConstant("\\PDO::PGSQL_CONNECTION_MADE", operator.createSymbol(3));
		putConstant("\\PDO::PGSQL_CONNECTION_SETENV", operator.createSymbol(6));
		putConstant("\\PDO::PGSQL_CONNECTION_SSL_STARTUP", operator.createSymbol(7));
		putConstant("\\PDO::PGSQL_CONNECTION_STARTED", operator.createSymbol(2));
		putConstant("\\PDO::PGSQL_DML_ESCAPE", operator.createSymbol(4096));
		putConstant("\\PDO::PGSQL_POLLING_ACTIVE", operator.createSymbol(4));
		putConstant("\\PDO::PGSQL_POLLING_FAILED", operator.createSymbol(0));
		putConstant("\\PDO::PGSQL_POLLING_OK", operator.createSymbol(3));
		putConstant("\\PDO::PGSQL_POLLING_READING", operator.createSymbol(1));
		putConstant("\\PDO::PGSQL_POLLING_WRITING", operator.createSymbol(2));
	}

	public static class PDOException extends PhpClassBase {
		public PDOException(Interpreter ip) {
			super(ip);
		}

		private static final String ERROR_INFO = "errorInfo";

		@Override
		public String getAbsoluteParentClassName() {
			return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
		}

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			SymbolOperator operator = interpreter.getOperator();
			//			public $errorInfo;
			operator.putFieldValue(thisSymbol, ERROR_INFO, operator.string());
		}
	}

	public static class PDO extends PhpClassBase {
		public PDO(Interpreter ip) {
			super(ip);
		}

		// public function __construct ($dsn, $username, $passwd, $options) {}

		// public function prepare ($statement, array $driver_options = operator.array()) {}
		public static class prepare implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				PDOStatement clazz = new PDOStatement(ip);
				Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				operator.putFieldValue(resultSymbol, PDOStatement.QUERY_STRING, SymbolUtils.getArgument(ip, 0));
				return resultSymbol;
			}
		}

		// public function beginTransaction () {}
		public static class beginTransaction implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function commit () {}
		public static class commit implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function rollBack () {}
		public static class rollBack implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function inTransaction () {}
		public static class inTransaction implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function setAttribute ($attribute, $value) {}
		public static class setAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function exec ($statement) {}
		public static class exec implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function query ($statement, $mode = PDO::ATTR_DEFAULT_FETCH_MODE, $arg3 = null) {}
		public static class query implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				PDOStatement clazz = new PDOStatement(ip);
				Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
				operator.putFieldValue(resultSymbol, PDOStatement.QUERY_STRING, SymbolUtils.getArgument(ip, 0));
				return resultSymbol;
			}
		}

		// public function lastInsertId ($name = null) {}
		public static class lastInsertId implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function errorCode () {}
		public static class errorCode implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function errorInfo () {}
		public static class errorInfo implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function getAttribute ($attribute) {}
		public static class getAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function quote ($string, $parameter_type = PDO::PARAM_STR) {}
		public static class quote implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				return SymbolUtils.getArgument(ip, 0);
			}
		}

		// final public function __wakeup () {}
		// final public function __sleep () {}

		// public static function getAvailableDrivers () {}
		public static class getAvailableDrivers implements PhpCallableStatic {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

	}

	public static class PDOStatement extends PhpClassBase {
		public PDOStatement(Interpreter ip) {
			super(ip);
		}

		private static final String QUERY_STRING = "queryString";

		@Override
		public void initialize(Interpreter interpreter) {
			super.initialize(interpreter);
			Symbol thisSymbol = interpreter.getContext().getThisSymbol();
			SymbolOperator operator = interpreter.getOperator();
			//			/**
			//			 * @var string
			//			 */
			//			public $queryString;
			operator.putFieldValue(thisSymbol, QUERY_STRING, operator.string());
		}

		// public function execute (array $input_parameters = null) {}
		public static class execute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function fetch ($fetch_style = null, $cursor_orientation = PDO::FETCH_ORI_NEXT, $cursor_offset = 0) {}
		public static class fetch implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function bindParam ($parameter, &$variable, $data_type = PDO::PARAM_STR, $length = null, $driver_options = null) {}
		public static class bindParam implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function bindColumn ($column, &$param, $type = null, $maxlen = null, $driverdata = null) {}
		public static class bindColumn implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function bindValue ($parameter, $value, $data_type = PDO::PARAM_STR) {}
		public static class bindValue implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function rowCount () {}
		public static class rowCount implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function fetchColumn ($column_number = 0) {}
		public static class fetchColumn implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function fetchAll ($fetch_style = null, $fetch_argument = null, array $ctor_args = 'array()') {}
		public static class fetchAll implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array(operator.array());
			}
		}

		// public function fetchObject ($class_name = "stdClass", array $ctor_args = null) {}
		public static class fetchObject implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.object();
			}
		}

		// public function errorCode () {}
		public static class errorCode implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function errorInfo () {}
		public static class errorInfo implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function setAttribute ($attribute, $value) {}
		public static class setAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function getAttribute ($attribute) {}
		public static class getAttribute implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.string();
			}
		}

		// public function columnCount () {}
		public static class columnCount implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.integer();
			}
		}

		// public function getColumnMeta ($column) {}
		public static class getColumnMeta implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.array();
			}
		}

		// public function setFetchMode ($mode) {}
		public static class setFetchMode implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function nextRowset () {}
		public static class nextRowset implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function closeCursor () {}
		public static class closeCursor implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// public function debugDumpParams () {}
		public static class debugDumpParams implements PhpCallable {
			@Override
			public Symbol call(Interpreter ip) {
				SymbolOperator operator = ip.getOperator();
				return operator.bool();
			}
		}

		// final public function __wakeup () {}
		// final public function __sleep () {}

	}

	public static class PDORow extends PhpClassBase {
		public PDORow(Interpreter ip) {
			super(ip);
		}
	}

	public static class pdo_drivers implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

}
