package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Spl.RuntimeException;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Mysqli extends BuiltinBase {
    public Mysqli(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\MYSQLI_READ_DEFAULT_GROUP", operator.createSymbol(5));
        putConstant("\\MYSQLI_READ_DEFAULT_FILE", operator.createSymbol(4));
        putConstant("\\MYSQLI_OPT_CONNECT_TIMEOUT", operator.createSymbol(0));
        putConstant("\\MYSQLI_OPT_LOCAL_INFILE", operator.createSymbol(8));
        putConstant("\\MYSQLI_SERVER_PUBLIC_KEY", operator.createSymbol(27));
        putConstant("\\MYSQLI_INIT_COMMAND", operator.createSymbol(3));
        putConstant("\\MYSQLI_OPT_NET_CMD_BUFFER_SIZE", operator.createSymbol(202));
        putConstant("\\MYSQLI_OPT_NET_READ_BUFFER_SIZE", operator.createSymbol(203));
        putConstant("\\MYSQLI_OPT_INT_AND_FLOAT_NATIVE", operator.createSymbol(201));
        putConstant("\\MYSQLI_CLIENT_SSL", operator.createSymbol(2048));
        putConstant("\\MYSQLI_CLIENT_COMPRESS", operator.createSymbol(32));
        putConstant("\\MYSQLI_CLIENT_INTERACTIVE", operator.createSymbol(1024));
        putConstant("\\MYSQLI_CLIENT_IGNORE_SPACE", operator.createSymbol(256));
        putConstant("\\MYSQLI_CLIENT_NO_SCHEMA", operator.createSymbol(16));
        putConstant("\\MYSQLI_CLIENT_FOUND_ROWS", operator.createSymbol(2));
        putConstant("\\MYSQLI_STORE_RESULT", operator.createSymbol(0));
        putConstant("\\MYSQLI_USE_RESULT", operator.createSymbol(1));
        putConstant("\\MYSQLI_ASYNC", operator.createSymbol(8));
        putConstant("\\MYSQLI_ASSOC", operator.createSymbol(1));
        putConstant("\\MYSQLI_NUM", operator.createSymbol(2));
        putConstant("\\MYSQLI_BOTH", operator.createSymbol(3));
        putConstant("\\MYSQLI_STMT_ATTR_UPDATE_MAX_LENGTH", operator.createSymbol(0));
        putConstant("\\MYSQLI_STMT_ATTR_CURSOR_TYPE", operator.createSymbol(1));
        putConstant("\\MYSQLI_CURSOR_TYPE_NO_CURSOR", operator.createSymbol(0));
        putConstant("\\MYSQLI_CURSOR_TYPE_READ_ONLY", operator.createSymbol(1));
        putConstant("\\MYSQLI_CURSOR_TYPE_FOR_UPDATE", operator.createSymbol(2));
        putConstant("\\MYSQLI_CURSOR_TYPE_SCROLLABLE", operator.createSymbol(4));
        putConstant("\\MYSQLI_STMT_ATTR_PREFETCH_ROWS", operator.createSymbol(2));
        putConstant("\\MYSQLI_NOT_NULL_FLAG", operator.createSymbol(1));
        putConstant("\\MYSQLI_PRI_KEY_FLAG", operator.createSymbol(2));
        putConstant("\\MYSQLI_UNIQUE_KEY_FLAG", operator.createSymbol(4));
        putConstant("\\MYSQLI_MULTIPLE_KEY_FLAG", operator.createSymbol(8));
        putConstant("\\MYSQLI_BLOB_FLAG", operator.createSymbol(16));
        putConstant("\\MYSQLI_UNSIGNED_FLAG", operator.createSymbol(32));
        putConstant("\\MYSQLI_ZEROFILL_FLAG", operator.createSymbol(64));
        putConstant("\\MYSQLI_AUTO_INCREMENT_FLAG", operator.createSymbol(512));
        putConstant("\\MYSQLI_TIMESTAMP_FLAG", operator.createSymbol(1024));
        putConstant("\\MYSQLI_SET_FLAG", operator.createSymbol(2048));
        putConstant("\\MYSQLI_NUM_FLAG", operator.createSymbol(32768));
        putConstant("\\MYSQLI_PART_KEY_FLAG", operator.createSymbol(16384));
        putConstant("\\MYSQLI_GROUP_FLAG", operator.createSymbol(32768));
        putConstant("\\MYSQLI_ENUM_FLAG", operator.createSymbol(256));
        putConstant("\\MYSQLI_BINARY_FLAG", operator.createSymbol(128));
        putConstant("\\MYSQLI_NO_DEFAULT_VALUE_FLAG", operator.createSymbol(4096));
        putConstant("\\MYSQLI_ON_UPDATE_NOW_FLAG", operator.createSymbol(8192));
        putConstant("\\MYSQLI_TYPE_DECIMAL", operator.createSymbol(0));
        putConstant("\\MYSQLI_TYPE_TINY", operator.createSymbol(1));
        putConstant("\\MYSQLI_TYPE_SHORT", operator.createSymbol(2));
        putConstant("\\MYSQLI_TYPE_LONG", operator.createSymbol(3));
        putConstant("\\MYSQLI_TYPE_FLOAT", operator.createSymbol(4));
        putConstant("\\MYSQLI_TYPE_DOUBLE", operator.createSymbol(5));
        putConstant("\\MYSQLI_TYPE_NULL", operator.createSymbol(6));
        putConstant("\\MYSQLI_TYPE_TIMESTAMP", operator.createSymbol(7));
        putConstant("\\MYSQLI_TYPE_LONGLONG", operator.createSymbol(8));
        putConstant("\\MYSQLI_TYPE_INT24", operator.createSymbol(9));
        putConstant("\\MYSQLI_TYPE_DATE", operator.createSymbol(10));
        putConstant("\\MYSQLI_TYPE_TIME", operator.createSymbol(11));
        putConstant("\\MYSQLI_TYPE_DATETIME", operator.createSymbol(12));
        putConstant("\\MYSQLI_TYPE_YEAR", operator.createSymbol(13));
        putConstant("\\MYSQLI_TYPE_NEWDATE", operator.createSymbol(14));
        putConstant("\\MYSQLI_TYPE_ENUM", operator.createSymbol(247));
        putConstant("\\MYSQLI_TYPE_SET", operator.createSymbol(248));
        putConstant("\\MYSQLI_TYPE_TINY_BLOB", operator.createSymbol(249));
        putConstant("\\MYSQLI_TYPE_MEDIUM_BLOB", operator.createSymbol(250));
        putConstant("\\MYSQLI_TYPE_LONG_BLOB", operator.createSymbol(251));
        putConstant("\\MYSQLI_TYPE_BLOB", operator.createSymbol(252));
        putConstant("\\MYSQLI_TYPE_VAR_STRING", operator.createSymbol(253));
        putConstant("\\MYSQLI_TYPE_STRING", operator.createSymbol(254));
        putConstant("\\MYSQLI_TYPE_CHAR", operator.createSymbol(1));
        putConstant("\\MYSQLI_TYPE_INTERVAL", operator.createSymbol(247));
        putConstant("\\MYSQLI_TYPE_GEOMETRY", operator.createSymbol(255));
        putConstant("\\MYSQLI_TYPE_NEWDECIMAL", operator.createSymbol(246));
        putConstant("\\MYSQLI_TYPE_BIT", operator.createSymbol(16));
        putConstant("\\MYSQLI_SET_CHARSET_NAME", operator.createSymbol(7));
        putConstant("\\MYSQLI_NO_DATA", operator.createSymbol(100));
        putConstant("\\MYSQLI_DATA_TRUNCATED", operator.createSymbol(101));
        putConstant("\\MYSQLI_REPORT_INDEX", operator.createSymbol(4));
        putConstant("\\MYSQLI_REPORT_ERROR", operator.createSymbol(1));
        putConstant("\\MYSQLI_REPORT_STRICT", operator.createSymbol(2));
        putConstant("\\MYSQLI_REPORT_ALL", operator.createSymbol(255));
        putConstant("\\MYSQLI_REPORT_OFF", operator.createSymbol(0));
        putConstant("\\MYSQLI_DEBUG_TRACE_ENABLED", operator.createSymbol(1));
        putConstant("\\MYSQLI_SERVER_QUERY_NO_GOOD_INDEX_USED", operator.createSymbol(16));
        putConstant("\\MYSQLI_SERVER_QUERY_NO_INDEX_USED", operator.createSymbol(32));
        putConstant("\\MYSQLI_REFRESH_GRANT", operator.createSymbol(1));
        putConstant("\\MYSQLI_REFRESH_LOG", operator.createSymbol(2));
        putConstant("\\MYSQLI_REFRESH_TABLES", operator.createSymbol(4));
        putConstant("\\MYSQLI_REFRESH_HOSTS", operator.createSymbol(8));
        putConstant("\\MYSQLI_REFRESH_STATUS", operator.createSymbol(16));
        putConstant("\\MYSQLI_REFRESH_THREADS", operator.createSymbol(32));
        putConstant("\\MYSQLI_REFRESH_SLAVE", operator.createSymbol(64));
        putConstant("\\MYSQLI_REFRESH_MASTER", operator.createSymbol(128));
        putConstant("\\MYSQLI_SERVER_QUERY_WAS_SLOW", operator.createSymbol(1024));
        putConstant("\\MYSQLI_REFRESH_BACKUP_LOG", operator.createSymbol(2097152));
        putConstant("\\MYSQLI_OPT_SSL_VERIFY_SERVER_CERT", operator.createSymbol(21));
        putConstant("\\MYSQLI_SET_CHARSET_DIR", operator.createSymbol(6));
        putConstant("\\MYSQLI_SERVER_PS_OUT_PARAMS", operator.createSymbol(4096));
    }

    public static class mysqli_sql_exception extends PhpClassBase {
        public mysqli_sql_exception(Interpreter ip) {
            super(ip);
        }

        @Override
        public String getAbsoluteParentClassName() {
            return SymbolUtils.getAbsoluteNameFromJavaClassName(RuntimeException.class.getName());
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //			protected $sqlstate;
            operator.putFieldValue(thisSymbol, "sqlstate", operator.string());
        }
    }

    //	/**
    //	 * MySQLi Driver.
    //	 * @link http://php.net/manual/en/class.mysqli-driver.php
    //	 */
    //	final class mysqli_driver  {
    public static class mysqli_driver extends PhpClassBase {
        public mysqli_driver(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            SymbolOperator operator = interpreter.getOperator();
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            //		/**
            //		 * @var string
            //		 */
            //		public $client_info;
            operator.putFieldValue(thisSymbol, "client_info", operator.string());
            //		/**
            //		 * @var string
            //		 */
            //		public $client_version;
            operator.putFieldValue(thisSymbol, "client_version", operator.string());
            //		/**
            //		 * @var string
            //		 */
            //		public $driver_version;
            operator.putFieldValue(thisSymbol, "driver_version", operator.string());
            //		/**
            //		 * @var string
            //		 */
            //		public $embedded;
            operator.putFieldValue(thisSymbol, "embedded", operator.string());
            //		/**
            //		 * @var bool
            //		 */
            //		public $reconnect;
            operator.putFieldValue(thisSymbol, "reconnect", operator.bool());
            //		/**
            //		 * @var int
            //		 */
            //		public $report_mode;
            operator.putFieldValue(thisSymbol, "reconnect", operator.integer());
            //
            //	}
        }
    }

    public static class mysqli extends PhpClassBase {
        public mysqli(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //			/**
            //			 * @var int
            //			 */
            //			public $affected_rows;
            operator.putFieldValue(thisSymbol, "affected_rows", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $client_info;
            operator.putFieldValue(thisSymbol, "client_info", operator.string());
            //			/**
            //			 * @var int
            //			 */
            //			public $client_version;
            operator.putFieldValue(thisSymbol, "client_version", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $connect_errno;
            operator.putFieldValue(thisSymbol, "connect_errno", operator.string());
            //			/**
            //			 * @var string
            //			 */
            //			public $connect_error;
            operator.putFieldValue(thisSymbol, "connect_error", operator.string());
            //			/**
            //			 * @var int
            //			 */
            //			public $errno;
            operator.putFieldValue(thisSymbol, "errno", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $error;
            operator.putFieldValue(thisSymbol, "error", operator.string());
            //			/**
            //			 * @var int
            //			 */
            //			public $field_count;
            operator.putFieldValue(thisSymbol, "field_count", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $host_info;
            operator.putFieldValue(thisSymbol, "host_info", operator.string());
            //			/**
            //			 * @var string
            //			 */
            //			public $info;
            operator.putFieldValue(thisSymbol, "info", operator.string());
            //			/**
            //			 * @var mixed
            //			 */
            //			public $insert_id;
            operator.putFieldValue(thisSymbol, "insert_id", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $server_info;
            operator.putFieldValue(thisSymbol, "server_info", operator.string());
            //			/**
            //			 * @var int
            //			 */
            //			public $server_version;
            operator.putFieldValue(thisSymbol, "server_version", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $sqlstate;
            operator.putFieldValue(thisSymbol, "sqlstate", operator.string());
            //			/**
            //			 * @var string
            //			 */
            //			public $protocol_version;
            operator.putFieldValue(thisSymbol, "protocol_version", operator.string());
            //			/**
            //			 * @var int
            //			 */
            //			public $thread_id;
            operator.putFieldValue(thisSymbol, "thread_id", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $warning_count;
            operator.putFieldValue(thisSymbol, "warning_count", operator.integer());
            //
            //		    /**
            //		     * @var array A list of errors, each as an associative array containing the errno, error, and sqlstate.
            //		     * @link http://www.php.net/manual/en/mysqli.error-list.php
            //		     */
            //		    public $error_list;
            operator.putFieldValue(thisSymbol, "error_list", operator.array());
        }

        public static class autocommit implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class begin_transaction implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class change_user implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class character_set_name implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class client_encoding implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class commit implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class connect implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class dump_debug_info implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class debug implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class get_charset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class get_client_info implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class get_connection_stats implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class get_server_info implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class get_warnings implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class init implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                PhpResource phpResource = operator.createPhpResource();
                return operator.createSymbol(phpResource);
            }
        }

        public static class kill implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class multi_query implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		public static class mysqli implements PhpCallable {
        //			@Override
        //			public Symbol call(Interpreter ip) {SymbolOperator operator = ip.getOperator();
        //			}
        //		}

        public static class more_results implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class next_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class options implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class ping implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class prepare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_stmt clazz = new mysqli_stmt(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class query implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class real_connect implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class real_escape_string implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return SymbolUtils.getArgument(ip, 0);
            }
        }

        public static class poll implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        public static class reap_async_query implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class escape_string implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return SymbolUtils.getArgument(ip, 0);
            }
        }

        public static class real_query implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class release_savepoint implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class rollback implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class savepoint implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class select_db implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class set_charset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class set_opt implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class ssl_set implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class stat implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }

        public static class stmt_init implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_stmt clazz = new mysqli_stmt(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class store_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class thread_safe implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class use_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class refresh implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class mysqli_warning extends PhpClassBase {
        public mysqli_warning(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //		/**
            //		 * @var string
            //		 */
            //		public $message;
            operator.putFieldValue(thisSymbol, "message", operator.string());
            //		/**
            //		 * @var string
            //		 */
            //		public $sqlstate;
            operator.putFieldValue(thisSymbol, "sqlstate", operator.string());
            //		/**
            //		 * @var int
            //		 */
            //		public $errno;
            operator.putFieldValue(thisSymbol, "errno", operator.integer());
        }

        public static class next implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }
    }

    public static class mysqli_result extends PhpClassBase {
        public mysqli_result(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //		/**
            //		 * @var int
            //		 */
            //		public $current_field;
            operator.putFieldValue(thisSymbol, "current_field", operator.integer());
            //		/**
            //		 * @var int
            //		 */
            //		public $field_count;
            operator.putFieldValue(thisSymbol, "field_count", operator.integer());
            //		/**
            //		 * @var array
            //		 */
            //		public $lengths;
            operator.putFieldValue(thisSymbol, "lengths", operator.array(operator.integer()));
            //		/**
            //		 * @var int
            //		 */
            //		public $num_rows;
            operator.putFieldValue(thisSymbol, "num_rows", operator.integer());
            //		/**
            //		 * @var mixed
            //		 */
            //		public $type;
            operator.putFieldValue(thisSymbol, "type", operator.string());
        }

        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class free implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class data_seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class fetch_field implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.object();
            }
        }

        public static class fetch_fields implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class fetch_field_direct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.object();
            }
        }

        public static class fetch_all implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class fetch_array implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class fetch_assoc implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class fetch_object implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.object();
            }
        }

        public static class fetch_row implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.array();
            }
        }

        public static class field_seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class free_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

    }

    public static class mysqli_stmt extends PhpClassBase {
        public mysqli_stmt(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //			/**
            //			 * @var int
            //			 */
            //			public $affected_rows;
            operator.putFieldValue(thisSymbol, "affected_rows", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $insert_id;
            operator.putFieldValue(thisSymbol, "insert_id", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $num_rows;
            operator.putFieldValue(thisSymbol, "num_rows", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $param_count;
            operator.putFieldValue(thisSymbol, "param_count", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $field_count;
            operator.putFieldValue(thisSymbol, "field_count", operator.integer());
            //			/**
            //			 * @var int
            //			 */
            //			public $errno;
            operator.putFieldValue(thisSymbol, "errno", operator.integer());
            //			/**
            //			 * @var string
            //			 */
            //			public $error;
            operator.putFieldValue(thisSymbol, "error", operator.string());
            //			/**
            //			 * @var array
            //			 */
            //			public $error_list;
            operator.putFieldValue(thisSymbol, "error_list", operator.array());
            //			/**
            //			 * @var string
            //			 */
            //			public $sqlstate;
            operator.putFieldValue(thisSymbol, "sqlstate", operator.string());
            //			/**
            //			 * @var string
            //			 */
            //			public $id;
            operator.putFieldValue(thisSymbol, "id", operator.string());
        }

        // public function __construct ($link, $query) {}
        public static class __construct implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return null;
            }
        }

        public static class attr_get implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        public static class attr_set implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class bind_param implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class bind_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class data_seek implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class execute implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class fetch implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class get_warnings implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_warning clazz = new mysqli_warning(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class result_metadata implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }

        public static class more_results implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class next_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class num_rows implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.integer();
            }
        }

        public static class send_long_data implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        //		 * No documentation available
        //		 * @deprecated 5.3 This function has been DEPRECATED as of PHP 5.3.0 and REMOVED as of PHP 5.4.0.
        //		public static class stmt implements PhpCallable {
        //			@Override
        //			public Symbol call(Interpreter ip) {SymbolOperator operator = ip.getOperator();
        //			}
        //		}

        public static class free_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        public static class reset implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class prepare implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class store_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.bool();
            }
        }

        public static class get_result implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                mysqli_result clazz = new mysqli_result(ip);
                Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
                return resultSymbol;
            }
        }
    }

    // function mysqli_affected_rows ($link) {}
    public static class mysqli_affected_rows implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_autocommit ($link, $mode) {}
    public static class mysqli_autocommit implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_begin_transaction ($link, $flags = 0, $name = null) {}
    public static class mysqli_begin_transaction implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_change_user ($link, $user, $password, $database) {}
    public static class mysqli_change_user implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_character_set_name ($link) {}
    public static class mysqli_character_set_name implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_close ($link) {}
    public static class mysqli_close implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_commit ($link) {}
    public static class mysqli_commit implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_connect ($host = '', $user = '', $password = '', $database = '', $port = '', $socket = '') {}
    public static class mysqli_connect implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli clazz = new mysqli(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_connect_errno () {}
    public static class mysqli_connect_errno implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_connect_error () {}
    public static class mysqli_connect_error implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_data_seek ($result, $offset) {}
    public static class mysqli_data_seek implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_dump_debug_info ($link) {}
    public static class mysqli_dump_debug_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_debug ($message) {}
    public static class mysqli_debug implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_errno ($link) {}
    public static class mysqli_errno implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_error_list ($link) {}
    public static class mysqli_error_list implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_stmt_error_list ($stmt) {}
    public static class mysqli_stmt_error_list implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_error ($link) {}
    public static class mysqli_error implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_stmt_execute ($stmt) {}
    public static class mysqli_stmt_execute implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_execute ($stmt) {}
    public static class mysqli_execute implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_stmt clazz = new mysqli_stmt(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_fetch_field ($result) {}
    public static class mysqli_fetch_field implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.object();
        }
    }

    // function mysqli_fetch_fields ($result) {}
    public static class mysqli_fetch_fields implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.object());
        }
    }

    // function mysqli_fetch_field_direct ($result, $fieldnr) {}
    public static class mysqli_fetch_field_direct implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.object();
        }
    }

    // function mysqli_fetch_lengths ($result) {}
    public static class mysqli_fetch_lengths implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function mysqli_fetch_all ($result, $resulttype = MYSQLI_NUM) {}
    public static class mysqli_fetch_all implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.array());
        }
    }

    // function mysqli_fetch_array ($result, $resulttype = MYSQLI_BOTH) {}
    public static class mysqli_fetch_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_fetch_assoc ($result) {}
    public static class mysqli_fetch_assoc implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_fetch_object ($result, $class_name = '', $params = null) {}
    public static class mysqli_fetch_object implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.object();
        }
    }

    // function mysqli_fetch_row ($result) {}
    public static class mysqli_fetch_row implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_field_count ($link) {}
    public static class mysqli_field_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_field_seek ($result, $fieldnr) {}
    public static class mysqli_field_seek implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_field_tell ($result) {}
    public static class mysqli_field_tell implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_free_result ($result) {}
    public static class mysqli_free_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function mysqli_get_cache_stats ($link) {}
    public static class mysqli_get_cache_stats implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_get_connection_stats ($link) {}
    public static class mysqli_get_connection_stats implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_get_client_stats () {}
    public static class mysqli_get_client_stats implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mysqli_get_charset ($link) {}
    public static class mysqli_get_charset implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.object();
        }
    }

    // function mysqli_get_client_info () {}
    public static class mysqli_get_client_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_get_client_version ($link) {}
    public static class mysqli_get_client_version implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_get_host_info ($link) {}
    public static class mysqli_get_host_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_get_proto_info ($link) {}
    public static class mysqli_get_proto_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_get_server_info ($link) {}
    public static class mysqli_get_server_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_get_server_version ($link) {}
    public static class mysqli_get_server_version implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_get_warnings ($link) {}
    public static class mysqli_get_warnings implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_warning clazz = new mysqli_warning(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_init () {}
    public static class mysqli_init implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli clazz = new mysqli(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_info ($link) {}
    public static class mysqli_info implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_insert_id ($link) {}
    public static class mysqli_insert_id implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_kill ($link, $processid) {}
    public static class mysqli_kill implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_set_local_infile_default ($link) {}
    public static class mysqli_set_local_infile_default implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function mysqli_set_local_infile_handler ($link, $read_func) {}
    public static class mysqli_set_local_infile_handler implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_more_results ($link) {}
    public static class mysqli_more_results implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_multi_query ($link, $query) {}
    public static class mysqli_multi_query implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_next_result ($link) {}
    public static class mysqli_next_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_num_fields ($result) {}
    public static class mysqli_num_fields implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_num_rows ($result) {}
    public static class mysqli_num_rows implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_options ($link, $option, $value) {}
    public static class mysqli_options implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_ping ($link) {}
    public static class mysqli_ping implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_poll (array &$read = null, array &$write = null, &$error = null, $sec, $usec = 0) {}
    public static class mysqli_poll implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_prepare ($link, $query) {}
    public static class mysqli_prepare implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_stmt clazz = new mysqli_stmt(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_report ($flags) {}

    public static class mysqli_report implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_query ($link, $query, $resultmode = MYSQLI_STORE_RESULT) {}
    public static class mysqli_query implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_real_connect ($link, $host = '', $user = '', $password = '', $database = '', $port = '', $socket = '', $flags = null) {}
    public static class mysqli_real_connect implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_real_escape_string ($link, $escapestr) {}
    public static class mysqli_real_escape_string implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function mysqli_real_query ($link, $query) {}
    public static class mysqli_real_query implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_reap_async_query ($link) {}
    public static class mysqli_reap_async_query implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_release_savepoint ($link ,$name) {}
    public static class mysqli_release_savepoint implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_rollback ($link) {}
    public static class mysqli_rollback implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_savepoint ($link ,$name) {}
    public static class mysqli_savepoint implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_select_db ($link, $dbname) {}
    public static class mysqli_select_db implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_set_charset ($link, $charset) {}
    public static class mysqli_set_charset implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_affected_rows ($stmt) {}
    public static class mysqli_stmt_affected_rows implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_attr_get ($stmt, $attr) {}
    public static class mysqli_stmt_attr_get implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_attr_set ($stmt, $attr, $mode) {}
    public static class mysqli_stmt_attr_set implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_field_count ($stmt) {}
    public static class mysqli_stmt_field_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_init () {}
    public static class mysqli_stmt_init implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_stmt clazz = new mysqli_stmt(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_stmt_prepare ($stmt, $query) {}
    public static class mysqli_stmt_prepare implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_result_metadata ($stmt) {}
    public static class mysqli_stmt_result_metadata implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_stmt_send_long_data ($stmt, $param_nr, $data) {}
    public static class mysqli_stmt_send_long_data implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_bind_param ($stmt, $types, &$var1) {}
    public static class mysqli_stmt_bind_param implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_bind_result ($stmt, &$var1, &...$_) {}
    public static class mysqli_stmt_bind_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_fetch ($stmt) {}
    public static class mysqli_stmt_fetch implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_free_result ($stmt) {}
    public static class mysqli_stmt_free_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function mysqli_stmt_get_result ($stmt) {}
    public static class mysqli_stmt_get_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_stmt_get_warnings ($stmt) {}
    public static class mysqli_stmt_get_warnings implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_warning clazz = new mysqli_warning(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_stmt_insert_id ($stmt) {}
    public static class mysqli_stmt_insert_id implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_reset ($stmt) {}
    public static class mysqli_stmt_reset implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_param_count ($stmt) {}
    public static class mysqli_stmt_param_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_sqlstate ($link) {}
    public static class mysqli_sqlstate implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_stat ($link) {}
    public static class mysqli_stat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_stmt_close ($stmt) {}
    public static class mysqli_stmt_close implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_data_seek ($stmt, $offset) {}
    public static class mysqli_stmt_data_seek implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function mysqli_stmt_errno ($stmt) {}
    public static class mysqli_stmt_errno implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_error ($stmt) {}
    public static class mysqli_stmt_error implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_stmt_more_results ($stmt) {}
    public static class mysqli_stmt_more_results implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_next_result ($stmt) {}
    public static class mysqli_stmt_next_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_stmt_num_rows ($stmt) {}
    public static class mysqli_stmt_num_rows implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_stmt_sqlstate ($stmt) {}
    public static class mysqli_stmt_sqlstate implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mysqli_stmt_store_result ($stmt) {}
    public static class mysqli_stmt_store_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_store_result ($link) {}
    public static class mysqli_store_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_thread_id ($link) {}
    public static class mysqli_thread_id implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_thread_safe () {}
    public static class mysqli_thread_safe implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_use_result ($link) {}
    public static class mysqli_use_result implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            mysqli_result clazz = new mysqli_result(ip);
            Symbol resultSymbol = SymbolUtils.new_(ip, clazz, Lists.newArrayList());
            return resultSymbol;
        }
    }

    // function mysqli_warning_count ($link) {}
    public static class mysqli_warning_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mysqli_refresh ($link, $options) {}
    public static class mysqli_refresh implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mysqli_bind_param ($stmt, $types) {}
    //  * Alias for <b>mysqli_stmt_bind_param</b>
    public static class mysqli_bind_param extends mysqli_stmt_bind_param {
    }

    // function mysqli_bind_result ($stmt, $types, &$var1) {}
    // * Alias for <b>mysqli_stmt_bind_result</b>
    public static class mysqli_bind_result extends mysqli_stmt_bind_result {
    }

    // function mysqli_client_encoding ($link) {}
    // * Alias of <b>mysqli_character_set_name</b>
    public static class mysqli_client_encoding extends mysqli_character_set_name {
    }

    // function mysqli_escape_string ($link, $query) {}
    //  * Alias of <b>mysqli_real_escape_string</b>
    public static class mysqli_escape_string extends mysqli_real_escape_string {
    }

    // function mysqli_fetch ($stmt) {}
    //  * Alias for <b>mysqli_stmt_fetch</b>
    public static class mysqli_fetch extends mysqli_stmt_fetch {
    }

    // function mysqli_param_count ($stmt) {}
    //  * Alias for <b>mysqli_stmt_param_count</b>
    public static class mysqli_param_count extends mysqli_stmt_param_count {
    }

    // function mysqli_get_metadata ($stmt) {}
    //  * Alias for <b>mysqli_stmt_result_metadata</b>
    public static class mysqli_get_metadata extends mysqli_stmt_result_metadata {
    }

    // function mysqli_send_long_data ($stmt, $param_nr, $data) {}
    //  * Alias for <b>mysqli_stmt_send_long_data</b>
    public static class mysqli_send_long_data extends mysqli_stmt_send_long_data {
    }

    // function mysqli_set_opt ($link, $option, $value) {}
    //  * Alias of <b>mysqli_options</b>
    public static class mysqli_set_opt extends mysqli_options {
    }

}
