package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Mysql extends BuiltinBase {
	public Mysql(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\MYSQL_ASSOC", operator.createSymbol(1));
		putConstant("\\MYSQL_NUM", operator.createSymbol(2));
		putConstant("\\MYSQL_BOTH", operator.createSymbol(3));
		putConstant("\\MYSQL_CLIENT_COMPRESS", operator.createSymbol(32));
		putConstant("\\MYSQL_CLIENT_SSL", operator.createSymbol(2048));
		putConstant("\\MYSQL_CLIENT_INTERACTIVE", operator.createSymbol(1024));
		putConstant("\\MYSQL_CLIENT_IGNORE_SPACE", operator.createSymbol(256));
	}

	// function mysql_connect ($server = 'ini_get("mysql.default_host")', $username = 'ini_get("mysql.default_user")', $password = 'ini_get("mysql.default_password")', $new_link = false, $client_flags = 0) {}
	public static class mysql_connect implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_pconnect ($server = 'ini_get("mysql.default_host")', $username = 'ini_get("mysql.default_user")', $password = 'ini_get("mysql.default_password")', $client_flags = null) {}
	public static class mysql_pconnect implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_close ($link_identifier = null) {}
	public static class mysql_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_select_db ($database_name, $link_identifier = null) {}
	public static class mysql_select_db implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_query ($query, $link_identifier = null) {}
	public static class mysql_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_unbuffered_query ($query, $link_identifier = null) {}
	public static class mysql_unbuffered_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_db_query ($database, $query, $link_identifier = null) {}
	public static class mysql_db_query implements PhpCallable {

		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_list_dbs ($link_identifier = null) {}
	public static class mysql_list_dbs implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_list_tables ($database, $link_identifier = null) {}
	public static class mysql_list_tables implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_list_fields ($database_name, $table_name, $link_identifier = null) {}
	public static class mysql_list_fields implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_list_processes ($link_identifier = null) {}
	public static class mysql_list_processes implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.createSymbol(ip.getStorage().getMysqlDefaultConnection());
		}
	}

	// function mysql_error ($link_identifier = null) {}
	public static class mysql_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_errno ($link_identifier = null) {}
	public static class mysql_errno implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_affected_rows ($link_identifier = null) {}
	public static class mysql_affected_rows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_insert_id ($link_identifier = null) {}
	public static class mysql_insert_id implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_result ($result, $row, $field = 0) {}
	public static class mysql_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_num_rows ($result) {}
	public static class mysql_num_rows implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_num_fields ($result) {}
	public static class mysql_num_fields implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_fetch_row ($result) {}
	public static class mysql_fetch_row implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function mysql_fetch_array ($result, $result_type = MYSQL_BOTH) {}
	public static class mysql_fetch_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function mysql_fetch_assoc ($result) {}
	public static class mysql_fetch_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function mysql_fetch_object ($result, $class_name = null, array $params = null ) {}
	public static class mysql_fetch_object implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function mysql_data_seek ($result, $row_number) {}
	public static class mysql_data_seek implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_fetch_lengths ($result) {}
	public static class mysql_fetch_lengths implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.integer());
		}
	}

	// function mysql_fetch_field ($result, $field_offset = 0) {}
	public static class mysql_fetch_field implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function mysql_field_seek ($result, $field_offset) {}
	public static class mysql_field_seek implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_free_result ($result) {}
	public static class mysql_free_result implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_field_name ($result, $field_offset) {}
	public static class mysql_field_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_field_table ($result, $field_offset) {}
	public static class mysql_field_table implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_field_len ($result, $field_offset) {}
	public static class mysql_field_len implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_field_type ($result, $field_offset) {}
	public static class mysql_field_type implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_field_flags ($result, $field_offset) {}
	public static class mysql_field_flags implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_escape_string ($unescaped_string) {}
	public static class mysql_escape_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function mysql_real_escape_string ($unescaped_string, $link_identifier = null) {}
	public static class mysql_real_escape_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function mysql_stat ($link_identifier = null) {}
	public static class mysql_stat implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_thread_id ($link_identifier = null) {}
	public static class mysql_thread_id implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mysql_client_encoding ($link_identifier = null) {}
	public static class mysql_client_encoding implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_ping ($link_identifier = null) {}
	public static class mysql_ping implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql_get_client_info () {}
	public static class mysql_get_client_info implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_get_host_info ($link_identifier = null) {}
	public static class mysql_get_host_info implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_get_proto_info ($link_identifier = null) {}
	public static class mysql_get_proto_info implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_get_server_info ($link_identifier = null) {}
	public static class mysql_get_server_info implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_info ($link_identifier = null) {}
	public static class mysql_info implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_set_charset ($charset, $link_identifier = null) {}
	public static class mysql_set_charset implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function mysql ($database_name, $query, $link_identifier) {}
	//  * @deprecated 5.3.0 Use mysql_db_query instead.
	public static class mysql extends mysql_db_query {
	}

	// function mysql_fieldname ($result, $field_index) {}
	//	 * @deprecated 5.5 Use mysql_field_name instead.
	public static class mysql_fieldname extends mysql_field_name {
	}

	// function mysql_fieldtable ($result, $field_offset) {}
	//	 * @deprecated 5.5 Use mysql_field_table instead.
	public static class mysql_fieldtable extends mysql_field_table {
	}

	// function mysql_fieldlen ($result, $field_offset) {}
	//  * @deprecated 5.5 Use mysql_field_len instead.
	public static class mysql_fieldlen extends mysql_field_len {
	}

	// function mysql_fieldtype ($result, $field_offset) {}
	// * @deprecated 5.5 Use mysql_field_type instead.
	public static class mysql_fieldtype extends mysql_field_type {
	}

	// function mysql_fieldflags ($result, $field_offset) {}
	//  * @deprecated 5.5 Use mysql_field_flags instead.
	public static class mysql_fieldflags extends mysql_field_flags {
	}

	// function mysql_selectdb ($database_name, $link_identifier) {}
	//  * @deprecated 5.5 Use mysql_select_db instead.
	public static class mysql_selectdb extends mysql_select_db {
	}

	// function mysql_freeresult ($result) {}
	//  * @deprecated 5.5 Use mysql_free_result instead.
	public static class mysql_freeresult extends mysql_free_result {
	}

	// function mysql_numfields ($result) {}
	//  * @deprecated 5.5 Use mysql_num_fields instead.
	public static class mysql_numfields extends mysql_num_fields {
	}

	// function mysql_numrows ($result) {}
	//  * @deprecated 5.5 Use mysql_num_rows instead.
	public static class mysql_numrows extends mysql_num_rows {
	}

	// function mysql_listdbs ($link_identifier) {}
	//  * @deprecated 5.5 Use mysql_list_dbs instead.
	public static class mysql_listdbs extends mysql_list_dbs {
	}

	// function mysql_listtables ($database_name, $link_identifier) {}
	//  * @deprecated 5.5 Use mysql_list_tables instead.
	public static class mysql_listtables extends mysql_list_tables {
	}

	// function mysql_listfields ($database_name, $table_name, $link_identifier) {}
	//  * @deprecated 5.5 Use mysql_list_fields instead.
	public static class mysql_listfields extends mysql_list_fields {
	}

	// function mysql_db_name ($result, $row, $field = null) {}
	public static class mysql_db_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function mysql_dbname ($result, $row, $field) {}
	//  * @deprecated 5.5 Use mysql_db_name instead.
	public static class mysql_dbname extends mysql_db_name {
	}

	// function mysql_tablename ($result, $i) {}
	public static class mysql_tablename extends mysql_table_name {
	}

	// function mysql_table_name ($result, $row, $field) {}
	public static class mysql_table_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

}
