package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Pgsql extends BuiltinBase {
	public Pgsql(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\PGSQL_LIBPQ_VERSION", operator.createSymbol("9.1.10"));
		putConstant(
				"PGSQL_LIBPQ_VERSION_STR",
				operator.createSymbol("PostgreSQL 9.1.10 on x86_64-unknown-linux-gnu, compiled by gcc (Ubuntu/Linaro 4.8.1-10ubuntu7) 4.8.1, 64-bit"));
		putConstant("\\PGSQL_CONNECT_FORCE_NEW", operator.createSymbol(2));
		putConstant("\\PGSQL_ASSOC", operator.createSymbol(1));
		putConstant("\\PGSQL_NUM", operator.createSymbol(2));
		putConstant("\\PGSQL_BOTH", operator.createSymbol(3));
		putConstant("\\PGSQL_CONNECTION_BAD", operator.createSymbol(1));
		putConstant("\\PGSQL_CONNECTION_OK", operator.createSymbol(0));
		putConstant("\\PGSQL_TRANSACTION_IDLE", operator.createSymbol(0));
		putConstant("\\PGSQL_TRANSACTION_ACTIVE", operator.createSymbol(1));
		putConstant("\\PGSQL_TRANSACTION_INTRANS", operator.createSymbol(2));
		putConstant("\\PGSQL_TRANSACTION_INERROR", operator.createSymbol(3));
		putConstant("\\PGSQL_TRANSACTION_UNKNOWN", operator.createSymbol(4));
		putConstant("\\PGSQL_ERRORS_TERSE", operator.createSymbol(0));
		putConstant("\\PGSQL_ERRORS_DEFAULT", operator.createSymbol(1));
		putConstant("\\PGSQL_ERRORS_VERBOSE", operator.createSymbol(2));
		putConstant("\\PGSQL_SEEK_SET", operator.createSymbol(0));
		putConstant("\\PGSQL_SEEK_CUR", operator.createSymbol(1));
		putConstant("\\PGSQL_SEEK_END", operator.createSymbol(2));
		putConstant("\\PGSQL_STATUS_LONG", operator.createSymbol(1));
		putConstant("\\PGSQL_STATUS_STRING", operator.createSymbol(2));
		putConstant("\\PGSQL_EMPTY_QUERY", operator.createSymbol(0));
		putConstant("\\PGSQL_COMMAND_OK", operator.createSymbol(1));
		putConstant("\\PGSQL_TUPLES_OK", operator.createSymbol(2));
		putConstant("\\PGSQL_COPY_OUT", operator.createSymbol(3));
		putConstant("\\PGSQL_COPY_IN", operator.createSymbol(4));
		putConstant("\\PGSQL_BAD_RESPONSE", operator.createSymbol(5));
		putConstant("\\PGSQL_NONFATAL_ERROR", operator.createSymbol(6));
		putConstant("\\PGSQL_FATAL_ERROR", operator.createSymbol(7));
		putConstant("\\PGSQL_DIAG_SEVERITY", operator.createSymbol(83));
		putConstant("\\PGSQL_DIAG_SQLSTATE", operator.createSymbol(67));
		putConstant("\\PGSQL_DIAG_MESSAGE_PRIMARY", operator.createSymbol(77));
		putConstant("\\PGSQL_DIAG_MESSAGE_DETAIL", operator.createSymbol(68));
		putConstant("\\PGSQL_DIAG_MESSAGE_HINT", operator.createSymbol(72));
		putConstant("\\PGSQL_DIAG_STATEMENT_POSITION", operator.createSymbol(80));
		putConstant("\\PGSQL_DIAG_INTERNAL_POSITION", operator.createSymbol(112));
		putConstant("\\PGSQL_DIAG_INTERNAL_QUERY", operator.createSymbol(113));
		putConstant("\\PGSQL_DIAG_CONTEXT", operator.createSymbol(87));
		putConstant("\\PGSQL_DIAG_SOURCE_FILE", operator.createSymbol(70));
		putConstant("\\PGSQL_DIAG_SOURCE_LINE", operator.createSymbol(76));
		putConstant("\\PGSQL_DIAG_SOURCE_FUNCTION", operator.createSymbol(82));
		putConstant("\\PGSQL_CONV_IGNORE_DEFAULT", operator.createSymbol(2));
		putConstant("\\PGSQL_CONV_FORCE_NULL", operator.createSymbol(4));
		putConstant("\\PGSQL_CONV_IGNORE_NOT_NULL", operator.createSymbol(8));
		putConstant("\\PGSQL_DML_NO_CONV", operator.createSymbol(256));
		putConstant("\\PGSQL_DML_EXEC", operator.createSymbol(512));
		putConstant("\\PGSQL_DML_ASYNC", operator.createSymbol(1024));
		putConstant("\\PGSQL_DML_STRING", operator.createSymbol(2048));
		putConstant("\\PGSQL_CONNECT_ASYNC", operator.createSymbol(4));
		putConstant("\\PGSQL_CONNECTION_AUTH_OK", operator.createSymbol(5));
		putConstant("\\PGSQL_CONNECTION_AWAITING_RESPONSE", operator.createSymbol(4));
		putConstant("\\PGSQL_CONNECTION_MADE", operator.createSymbol(3));
		putConstant("\\PGSQL_CONNECTION_SETENV", operator.createSymbol(6));
		putConstant("\\PGSQL_CONNECTION_STARTED", operator.createSymbol(2));
		putConstant("\\PGSQL_DML_ESCAPE", operator.createSymbol(4096));
		putConstant("\\PGSQL_POLLING_ACTIVE", operator.createSymbol(4));
		putConstant("\\PGSQL_POLLING_FAILED", operator.createSymbol(0));
		putConstant("\\PGSQL_POLLING_OK", operator.createSymbol(3));
		putConstant("\\PGSQL_POLLING_READING", operator.createSymbol(1));
		putConstant("\\PGSQL_POLLING_WRITING", operator.createSymbol(2));
	}

	private static final String CONNECTION_STRING = "connection_string";

	// function pg_connect ($connection_string, $connect_type = null) {}
	public static class pg_connect implements PhpCallable {

		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			operator.putResourceValue(ip.getStorage().getPgsqlDefaultConnection(),
					CONNECTION_STRING, SymbolUtils.getArgument(ip, 0));
			return operator.createSymbol(ip.getStorage().getPgsqlDefaultConnection());
		}
	}

	// function pg_pconnect ($connection_string, $connect_type = null) {}
	public static class pg_pconnect implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getPgsqlDefaultConnection());
		}
	}

	// function pg_close ($connection = null) {}
	public static class pg_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_connection_status ($connection) {}
	public static class pg_connection_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_connection_busy ($connection) {}
	public static class pg_connection_busy implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_connection_reset ($connection) {}
	public static class pg_connection_reset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_host ($connection = null) {}
	public static class pg_host implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.getResourceValue(ip.getStorage().getPgsqlDefaultConnection(),
					CONNECTION_STRING);
		}
	}

	// function pg_dbname ($connection = null) {}
	public static class pg_dbname implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.getResourceValue(ip.getStorage().getPgsqlDefaultConnection(),
					CONNECTION_STRING);
		}
	}

	// function pg_port ($connection = null) {}
	public static class pg_port implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_tty ($connection = null) {}
	public static class pg_tty implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.getResourceValue(ip.getStorage().getPgsqlDefaultConnection(),
					CONNECTION_STRING);
		}
	}

	// function pg_options ($connection = null) {}
	public static class pg_options implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.getResourceValue(ip.getStorage().getPgsqlDefaultConnection(),
					CONNECTION_STRING);
		}
	}

	// function pg_version ($connection = null) {}
	public static class pg_version implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_ping ($connection = null) {}
	public static class pg_ping implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_parameter_status ($connection = null, $param_name) {}
	public static class pg_parameter_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_transaction_status ($connection) {}
	public static class pg_transaction_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_query ($connection = null, $query) {}
	public static class pg_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_query_params ($connection = null, $query, array $params) {}
	public static class pg_query_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_prepare ($connection = null, $stmtname, $query) {}
	public static class pg_prepare implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_execute ($connection = null, $stmtname, array $params) {}
	public static class pg_execute implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_send_query ($connection, $query) {}
	public static class pg_send_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_send_query_params ($connection, $query, array $params) {}
	public static class pg_send_query_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_send_prepare ($connection, $stmtname, $query) {}
	public static class pg_send_prepare implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_send_execute ($connection, $stmtname, array $params) {}
	public static class pg_send_execute implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_cancel_query ($connection) {}
	public static class pg_cancel_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_fetch_result ($result, $row = null, $field) {}
	public static class pg_fetch_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_fetch_row ($result, $row = null, $result_type = null) {}
	public static class pg_fetch_row implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_fetch_assoc ($result, $row = null) {}
	public static class pg_fetch_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_fetch_array ($result, $row = null, $result_type = PGSQL_BOTH) {}
	public static class pg_fetch_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_fetch_object ($result, $row = null, $result_type = PGSQL_ASSOC) {}
	public static class pg_fetch_object implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function pg_fetch_all ($result) {}
	public static class pg_fetch_all implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.array());
		}
	}

	// function pg_fetch_all_columns ($result, $column = 0) {}
	public static class pg_fetch_all_columns implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_affected_rows ($result) {}
	public static class pg_affected_rows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_get_result ($connection = null) {}
	public static class pg_get_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_result_seek ($result, $offset) {}
	public static class pg_result_seek implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_result_status ($result, $type = PGSQL_STATUS_LONG) {}
	public static class pg_result_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_free_result ($result) {}
	public static class pg_free_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_last_oid ($result) {}
	public static class pg_last_oid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_num_rows ($result) {}
	public static class pg_num_rows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_num_fields ($result) {}
	public static class pg_num_fields implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_name ($result, $field_number) {}
	public static class pg_field_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_field_num ($result, $field_name) {}
	public static class pg_field_num implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_size ($result, $field_number) {}
	public static class pg_field_size implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_type ($result, $field_number) {}
	public static class pg_field_type implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_field_type_oid ($result, $field_number) {}
	public static class pg_field_type_oid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_prtlen ($result, $row_number, $field_name_or_number) {}
	public static class pg_field_prtlen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_is_null ($result, $row, $field) {}
	public static class pg_field_is_null implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_field_table ($result, $field_number, $oid_only = false) {}
	public static class pg_field_table implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_get_notify ($connection, $result_type = null) {}
	public static class pg_get_notify implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_get_pid ($connection) {}
	public static class pg_get_pid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_result_error ($result) {}
	public static class pg_result_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_result_error_field ($result, $fieldcode) {}
	public static class pg_result_error_field implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_last_error ($connection = null) {}
	public static class pg_last_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_last_notice ($connection) {}
	public static class pg_last_notice implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_put_line ($connection = null, $data) {}
	public static class pg_put_line implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_end_copy ($connection = null) {}
	public static class pg_end_copy implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_copy_to ($connection, $table_name, $delimiter = null, $null_as = null) {}
	public static class pg_copy_to implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_copy_from ($connection, $table_name, array $rows, $delimiter = null, $null_as = null) {}
	public static class pg_copy_from implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_trace ($pathname, $mode = "w", $connection = null) {}
	public static class pg_trace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_untrace ($connection = null) {}
	public static class pg_untrace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_lo_create ($connection = null, $object_id = null) {}
	public static class pg_lo_create implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lo_unlink ($connection, $oid) {}
	public static class pg_lo_unlink implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_lo_open ($connection, $oid, $mode) {}
	public static class pg_lo_open implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_lo_close ($large_object) {}
	public static class pg_lo_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_lo_read ($large_object, $len = 8192) {}
	public static class pg_lo_read implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_lo_write ($large_object, $data, $len = null) {}
	public static class pg_lo_write implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lo_read_all ($large_object) {}
	public static class pg_lo_read_all implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lo_import ($connection = null, $pathname, $object_id = null) {}
	public static class pg_lo_import implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lo_export ($connection = null, $oid, $pathname) {}
	public static class pg_lo_export implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lo_seek ($large_object, $offset, $whence = PGSQL_SEEK_CUR) {}
	public static class pg_lo_seek implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_lo_tell ($large_object) {}
	public static class pg_lo_tell implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_escape_string ($connection = null, $data) {}
	public static class pg_escape_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, -1);
		}
	}

	// function pg_escape_bytea ($connection = null, $data) {}
	public static class pg_escape_bytea implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, -1);
		}
	}

	// function pg_escape_identifier ($connection = null, $data) {}
	public static class pg_escape_identifier implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, -1);
		}
	}

	// function pg_escape_literal ($connection = null, $data) {}
	public static class pg_escape_literal implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, -1);
		}
	}

	// function pg_unescape_bytea ($data) {}
	public static class pg_unescape_bytea implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function pg_set_error_verbosity ($connection = null, $verbosity) {}
	public static class pg_set_error_verbosity implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_client_encoding ($connection = null) {}
	public static class pg_client_encoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_set_client_encoding ($connection = null, $encoding) {}
	public static class pg_set_client_encoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_meta_data ($connection, $table_name) {}
	public static class pg_meta_data implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function pg_convert ($connection, $table_name, array $assoc_array, $options = 0) {}
	public static class pg_convert implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 2);
		}
	}

	// function pg_insert ($connection, $table_name, array $assoc_array, $options = PGSQL_DML_EXEC) {}
	public static class pg_insert implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_update ($connection, $table_name, array $data, array $condition, $options = PGSQL_DML_EXEC) {}
	public static class pg_update implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_delete ($connection, $table_name, array $assoc_array, $options = PGSQL_DML_EXEC) {}
	public static class pg_delete implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_select ($connection, $table_name, array $assoc_array, $options = PGSQL_DML_EXEC) {}
	public static class pg_select implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_exec ($connection, $query) {}
	public static class pg_exec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_getlastoid ($result) {}
	public static class pg_getlastoid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_cmdtuples ($result) {} // TODO remove
	// http://www2.osipp.osaka-u.ac.jp/~shirai/php-doc-j/function.pg-cmdtuples.html
	public static class pg_cmdtuples implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_errormessage ($connection) {}
	public static class pg_errormessage implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_numrows ($result) {}
	public static class pg_numrows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_numfields ($result) {}
	public static class pg_numfields implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_fieldname ($result, $field_number) {}
	public static class pg_fieldname implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_fieldsize ($result, $field_number) {}
	public static class pg_fieldsize implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_fieldtype ($result, $field_number) {}
	public static class pg_fieldtype implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_fieldnum ($result, $field_name) {}
	public static class pg_fieldnum implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_fieldprtlen ($result, $row, $field_name_or_number) {}
	public static class pg_fieldprtlen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_fieldisnull ($result, $row, $field_name_or_number) {}
	public static class pg_fieldisnull implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_freeresult ($result) {}
	public static class pg_freeresult implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_result ($connection) {} // TODO remove
	// http://www2.osipp.osaka-u.ac.jp/~shirai/php-doc-j/function.pg-result.html
	public static class pg_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_loreadall ($large_object) {} // TODO remove
	// http://www2.osipp.osaka-u.ac.jp/~shirai/php-doc-j/function.pg-loreadall.html
	public static class pg_loreadall implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function pg_locreate ($connection, $large_object_id) {}
	public static class pg_locreate implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_lounlink ($connection, $large_object_oid) {}
	public static class pg_lounlink implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_loopen ($connection, $large_object_oid, $mode) {}
	public static class pg_loopen implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(operator.createPhpResource());
		}
	}

	// function pg_loclose ($large_object) {}
	public static class pg_loclose implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_loread ($large_object, $len) {}
	public static class pg_loread implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_lowrite ($large_object, $buf, $len) {}
	public static class pg_lowrite implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_loimport ($connection, $filename, $large_object_oid) {}
	public static class pg_loimport implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pg_loexport ($connection, $objoid, $filename) {}
	public static class pg_loexport implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pg_clientencoding ($connection) {}
	public static class pg_clientencoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function pg_setclientencoding ($connection, $encoding) {}
	public static class pg_setclientencoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

}
