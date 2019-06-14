package net.katagaitai.phpscan.interceptor.sink;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_db_query;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_query;
import net.katagaitai.phpscan.php.builtin.Mysql.mysql_unbuffered_query;
import net.katagaitai.phpscan.php.builtin.Mysqli.*;
import net.katagaitai.phpscan.php.builtin.Pdo.PDO;
import net.katagaitai.phpscan.php.builtin.Pgsql.*;
import net.katagaitai.phpscan.php.builtin.Sqlite.*;
import net.katagaitai.phpscan.php.builtin.Sqlite3.SQLite3;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TaintCheckerSQLICall implements CallInterceptor {
    private final Interpreter ip;

    @Override
    public void intercept(CallDecorator decorator) {
        PhpCallable callable = decorator.getDecorated();
        int argIndex;
        String comment = SymbolUtils.getFunctionName(callable);
        if (callable instanceof mysql_query || callable instanceof mysql_unbuffered_query) {
            //		function mysql_query ($query, $link_identifier = null) {}
            //		function mysql_unbuffered_query ($query, $link_identifier = null) {}
            argIndex = 0;
        } else if (callable instanceof mysql_db_query || callable instanceof mysql) {
            //		function mysql_db_query ($database, $query, $link_identifier = null) {}
            //		function mysql ($database_name, $query, $link_identifier) {}
            argIndex = 1;
        } else
            // mysqli
            if (callable instanceof mysqli.multi_query || callable instanceof mysqli.prepare
                    || callable instanceof mysqli.query
                    || callable instanceof mysqli.real_query) {
                //		public function multi_query ($query) {}
                //		public function prepare ($query) {}
                //		public function query ($query, $resultmode = MYSQLI_STORE_RESULT) {}
                //		public function real_query ($query) {}
                argIndex = 0;
            } else
                // mysqli_stmt
                if (callable instanceof mysqli_stmt.__construct) {
                    //		public function __construct ($link, $query) {}
                    argIndex = 1;
                } else if (callable instanceof mysqli_stmt.prepare) {
                    //		public function prepare ($query) {}
                    argIndex = 0;
                } else if (callable instanceof mysqli_multi_query || callable instanceof mysqli_prepare
                        || callable instanceof mysqli_query || callable instanceof mysqli_real_query
                        || callable instanceof mysqli_stmt_prepare) {
                    //	function mysqli_multi_query ($link, $query) {}
                    //	function mysqli_prepare ($link, $query) {}
                    //	function mysqli_query ($link, $query, $resultmode = MYSQLI_STORE_RESULT) {}
                    //	function mysqli_real_query ($link, $query) {}
                    //	function mysqli_stmt_prepare ($stmt, $query) {}
                    argIndex = 1;
                } else
                    // PDO
                    if (callable instanceof PDO.prepare || callable instanceof PDO.exec ||
                            callable instanceof PDO.query) {
                        //	    public function prepare ($statement, array $driver_options = array()) {}
                        //		public function exec ($statement) {}
                        //		public function query ($statement, $mode = PDO::ATTR_DEFAULT_FETCH_MODE, $arg3 = null) {}
                        argIndex = 0;
                    } else if (callable instanceof pg_query || callable instanceof pg_query_params
                            || callable instanceof pg_prepare || callable instanceof pg_send_query
                            || callable instanceof pg_send_query_params || callable instanceof pg_send_prepare
                            || callable instanceof pg_exec) {
                        //		function pg_query ($connection = null, $query) {}
                        //		function pg_query_params ($connection = null, $query, array $params) {}
                        //		function pg_prepare ($connection = null, $stmtname, $query) {}
                        //		function pg_send_query ($connection, $query) {}
                        //		function pg_send_query_params ($connection, $query, array $params) {}
                        //		function pg_send_prepare ($connection, $stmtname, $query) {}
                        //		function pg_exec ($connection, $query) {}
                        argIndex = 1;
                    } else
                        // SQLiteDatabase
                        if (callable instanceof SQLiteDatabase.query || callable instanceof SQLiteDatabase.queryExec
                                || callable instanceof SQLiteDatabase.arrayQuery ||
                                callable instanceof SQLiteDatabase.singleQuery
                                || callable instanceof SQLiteDatabase.unbufferedQuery) {
                            //		public function query ($query, $result_type, &$error_message) {}
                            //		public function queryExec ($query, &$error_message) {}
                            //		public function arrayQuery ($query, $result_type, $decode_binary) {}
                            //		public function singleQuery ($query, $first_row_only, $decode_binary) {}
                            //		public function unbufferedQuery ($query, $result_type = SQLITE_BOTH, &$error_message) {}
                            argIndex = 0;
                        } else if (callable instanceof sqlite_query) {
                            //	function sqlite_query ($query, $dbhandle, $result_type = null, &$error_msg = SQLITE_BOTH) {}
                            argIndex = 0;
                        } else if (callable instanceof sqlite_exec
                                || callable instanceof sqlite_array_query || callable instanceof sqlite_single_query
                                || callable instanceof sqlite_unbuffered_query) {
                            //	function sqlite_exec ($dbhandle, $query, &$error_msg = null) {}
                            //	function sqlite_array_query ($dbhandle, $query, $result_type = null, $decode_binary = null) {}
                            //	function sqlite_single_query ($db, $query, $first_row_only = null, $decode_binary = null) {}
                            //	function sqlite_unbuffered_query ($dbhandle, $query, $result_type = SQLITE_BOTH, &$error_msg = null) {}
                            argIndex = 1;
                        } else
                            // SQLite3
                            if (callable instanceof SQLite3.exec || callable instanceof SQLite3.prepare
                                    || callable instanceof SQLite3.query || callable instanceof SQLite3.querySingle) {
                                //		public function exec ($query) {}
                                //		public function prepare ($query) {}
                                //		public function query ($query) {}
                                //		public function querySingle ($query, $entire_row = false) {}
                                argIndex = 0;
                            } else {
                                return;
                            }

        Symbol symbol = SymbolUtils.getArgument(ip, argIndex);
        Set<Taint> taintSet = TaintUtils.getSQLTaintSet(symbol.getTaintSet());
        List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                ip, VulnerabilityCategory.SQLI, taintSet, comment);
        ScanUtils.addVulnerability(ip, vulnerabilityList);
    }

}
