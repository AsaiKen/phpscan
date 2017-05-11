package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Standard_6 extends BuiltinBase {
	public Standard_6(Interpreter ip) {
		super(ip);
	}

	// function stream_select (array &$read, array &$write, array &$except, $tv_sec, $tv_usec = null) {}
	public static class stream_select implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function stream_context_create (array $options = null, array $params = null) {}
	public static class stream_context_create implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_context_set_params ($stream_or_context, array $params) {}
	public static class stream_context_set_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_context_get_params ($stream_or_context) {}
	public static class stream_context_get_params implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_context_set_option ($stream_or_context, $wrapper, $option, $value) {}
	// function stream_context_set_option ($stream_or_context, array $options) {}
	public static class stream_context_set_option implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_context_get_options ($stream_or_context) {}
	public static class stream_context_get_options implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_context_get_default (array $options = null) {}
	public static class stream_context_get_default implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_context_set_default (array $options) {}
	public static class stream_context_set_default implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_filter_prepend ($stream, $filtername, $read_write = null, $params = null) {}
	public static class stream_filter_prepend implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_filter_append ($stream, $filtername, $read_write = null, $params = null) {}
	public static class stream_filter_append implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_filter_remove ($stream_filter) {}
	public static class stream_filter_remove implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_socket_client ($remote_socket, &$errno = null, &$errstr = null, $timeout = null, $flags = null, $context = null) {}
	public static class stream_socket_client implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_socket_server ($local_socket, &$errno = null, &$errstr = null, $flags = null, $context = null) {}
	public static class stream_socket_server implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_socket_accept ($server_socket, $timeout = null, &$peername = null) {}
	public static class stream_socket_accept implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			return operator.createSymbol(phpResource);
		}
	}

	// function stream_socket_get_name ($handle, $want_peer) {}
	public static class stream_socket_get_name implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function stream_socket_recvfrom ($socket, $length, $flags = null, &$address = null) {}
	public static class stream_socket_recvfrom implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function stream_socket_sendto ($socket, $data, $flags = null, $address = null) {}
	public static class stream_socket_sendto implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function stream_socket_enable_crypto ($stream, $enable, $crypto_type = null, $session_stream = null) {}
	public static class stream_socket_enable_crypto implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_socket_shutdown ($stream, $how) {}
	public static class stream_socket_shutdown implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_socket_pair ($domain, $type, $protocol) {}
	public static class stream_socket_pair implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			PhpResource phpResource2 = operator.createPhpResource();
			PhpArray phpArray = operator.createPhpArray();
			operator.putArrayValue(phpArray, 0, operator.createSymbol(phpResource));
			operator.putArrayValue(phpArray, 1, operator.createSymbol(phpResource2));
			return operator.createSymbol(phpArray);
		}
	}

	// function stream_copy_to_stream ($source, $dest, $maxlength = null, $offset = null) {}
	public static class stream_copy_to_stream implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function stream_get_contents ($handle, $maxlength = null, $offset = null) {}
	public static class stream_get_contents implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function stream_supports_lock ($stream) {}
	public static class stream_supports_lock implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function fgetcsv ($handle, $length = null, $delimiter = null, $enclosure = null, $escape = null) {}
	public static class fgetcsv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valueSymbol = operator.getResourceValue(handleSymbol, Standard_5.OPEN_CONTENT);
			return operator.array(valueSymbol);
		}
	}

	// function fputcsv ($handle, array $fields, $delimiter = null, $enclosure = null) {}
	public static class fputcsv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol fieldsSymbol = SymbolUtils.getArgument(ip, 1);
			operator.addResourceValue(handleSymbol, Standard_5.OPEN_CONTENT,
					operator.getMergedArrayValueSymbol(fieldsSymbol));
			return operator.integer();
		}
	}

	// function flock ($handle, $operation, &$wouldblock = null) {}
	public static class flock implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_meta_tags ($filename, $use_include_path = null) {}
	public static class get_meta_tags implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_set_write_buffer ($stream, $buffer) {}
	public static class stream_set_write_buffer implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function stream_set_read_buffer ($stream, $buffer) {}
	public static class stream_set_read_buffer implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function set_file_buffer ($fp, $buffer) {}
	public static class set_file_buffer implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function set_socket_blocking ($socket, $mode) {}
	public static class set_socket_blocking implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_set_blocking ($stream, $mode) {}
	public static class stream_set_blocking implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function socket_set_blocking ($socket, $mode) {}
	public static class socket_set_blocking implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_get_meta_data ($stream) {}
	public static class stream_get_meta_data implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_get_line ($handle, $length, $ending = null) {}
	public static class stream_get_line implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function stream_wrapper_register ($protocol, $classname, $flags = null) {}
	public static class stream_wrapper_register implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_register_wrapper ($protocol, $classname, $flags) {}
	public static class stream_register_wrapper implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_resolve_include_path ($filename) {}
	public static class stream_resolve_include_path implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function stream_wrapper_unregister ($protocol) {}
	public static class stream_wrapper_unregister implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_wrapper_restore ($protocol) {}
	public static class stream_wrapper_restore implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_get_wrappers () {}
	public static class stream_get_wrappers implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_get_transports () {}
	public static class stream_get_transports implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_is_local ($stream_or_url) {}
	public static class stream_is_local implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_headers ($url, $format = null) {}
	// レスポンス内で サーバーによって送出された全てのヘッダを取得する
	public static class get_headers implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_set_timeout ($stream, $seconds, $microseconds = null) {}
	public static class stream_set_timeout implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function socket_set_timeout ($stream, $seconds, $microseconds = 0) {}
	public static class socket_set_timeout implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function socket_get_status (resource $stream) {}
	public static class socket_get_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function realpath ($path) {}
	public static class realpath implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function fnmatch ($pattern, $string, $flags = null) {}
	public static class fnmatch implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

}
