package net.katagaitai.phpscan.php.builtin;

import java.util.List;
import java.util.Map.Entry;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Standard_3 extends BuiltinBase {
	public Standard_3(Interpreter ip) {
		super(ip);
	}

	// function getlastmod () {}
	public static class getlastmod implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function base64_decode ($data, $strict = null) {}
	public static class base64_decode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function base64_encode ($data) {}
	public static class base64_encode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function convert_uuencode ($data) {}
	public static class convert_uuencode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function convert_uudecode ($data) {}
	public static class convert_uudecode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function abs ($number) {}
	public static class abs implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function ceil ($value) {}
	public static class ceil implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function floor ($value) {}
	public static class floor implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function round ($val, $precision = 0, $mode = PHP_ROUND_HALF_UP) {}
	public static class round implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sin ($arg) {}
	public static class sin implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function cos ($arg) {}
	public static class cos implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function tan ($arg) {}
	public static class tan implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function asin ($arg) {}
	public static class asin implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function acos ($arg) {}
	public static class acos implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function atan ($arg) {}
	public static class atan implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function atanh ($arg) {}
	public static class atanh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function atan2 ($y, $x) {}
	public static class atan2 implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sinh ($arg) {}
	public static class sinh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function cosh ($arg) {}
	public static class cosh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function tanh ($arg) {}
	public static class tanh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function asinh ($arg) {}
	public static class asinh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function acosh ($arg) {}
	public static class acosh implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function expm1 ($arg) {}
	public static class expm1 implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function log1p ($number) {}
	public static class log1p implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function pi () {}
	public static class pi implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function is_finite ($val) {}
	public static class is_finite implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function is_nan ($val) {}
	public static class is_nan implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function intdiv ($numerator,  $divisor) {}
	public static class intdiv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function is_infinite ($val) {}
	public static class is_infinite implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pow ($base, $exp) {}
	public static class pow implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function exp ($arg) {}
	public static class exp implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function log ($arg, $base = null) {}
	public static class log implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function log10 ($arg) {}
	public static class log10 implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sqrt ($arg) {}
	public static class sqrt implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function hypot ($x, $y) {}
	public static class hypot implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function deg2rad ($number) {}
	public static class deg2rad implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function rad2deg ($number) {}
	public static class rad2deg implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function bindec ($binary_string) {}
	public static class bindec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function hexdec ($hex_string) {}
	public static class hexdec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function octdec ($octal_string) {}
	public static class octdec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function decbin ($number) {}
	public static class decbin implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function decoct ($number) {}
	public static class decoct implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function dechex ($number) {}
	public static class dechex implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function base_convert ($number, $frombase, $tobase) {}
	public static class base_convert implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function number_format ($number , $decimals = 0 , $dec_point = '.' , $thousands_sep = ',' ) {}
	public static class number_format implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function fmod ($x, $y) {}
	public static class fmod implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function inet_ntop ($in_addr) {}
	public static class inet_ntop implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function inet_pton ($address) {}
	public static class inet_pton implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function ip2long ($ip_address) {}
	public static class ip2long implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function long2ip ($proper_address) {}
	public static class long2ip implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function getenv ($varname) {}
	public static class getenv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function putenv ($setting) {}
	public static class putenv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function getopt ($options, array $longopts = null) {}
	public static class getopt implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function sys_getloadavg () {}
	public static class sys_getloadavg implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.integer());
		}
	}

	// function microtime ($get_as_float = null) {}
	public static class microtime implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function gettimeofday ($return_float = null) {}
	public static class gettimeofday implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(operator.integer());
		}
	}

	// function getrusage ($who = null) {}
	public static class getrusage implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function uniqid ($prefix = "", $more_entropy = false) {}
	public static class uniqid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol resultSymbol = operator.string();
			Symbol prefixSymbol = SymbolUtils.getArgument(ip, 0);
			operator.addNewTaintSet(resultSymbol, prefixSymbol.getTaintSet());
			return resultSymbol;
		}
	}

	// function quoted_printable_decode ($str) {}
	public static class quoted_printable_decode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function quoted_printable_encode ($str) {}
	public static class quoted_printable_encode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function convert_cyr_string ($str, $from, $to) {}
	public static class convert_cyr_string implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function get_current_user () {}
	public static class get_current_user implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function set_time_limit ($seconds) {}
	public static class set_time_limit implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function get_cfg_var ($option) {}
	public static class get_cfg_var implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function magic_quotes_runtime ($new_setting) {}
	//  * &Alias; <function>set_magic_quotes_runtime</function>
	public static class magic_quotes_runtime extends set_magic_quotes_runtime {
	}

	// function set_magic_quotes_runtime ($new_setting) {}
	public static class set_magic_quotes_runtime implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function get_magic_quotes_gpc () {}
	public static class get_magic_quotes_gpc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function get_magic_quotes_runtime () {}
	public static class get_magic_quotes_runtime implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function import_request_variables ($types, $prefix = null) {}
	// GET/POST/Cookie 変数をグローバルスコープにインポートします。
	public static class import_request_variables implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// インポートする リクエスト変数の種類を指定可能です。
			// 文字 'G'、'P'、'C' がそれぞれ GET、POST、Cookie を表します。
			// これらは大文字小文字を区別しないため、 'g'、'p'、'c' の組み合せも使用することが可能です。
			// POST には、アップロードされたファイルに関する情報も含まれます。
			//  "GP" とすると、POST 変数は同名の GET 変数を上書きします。GPC 以外の文字は無視されます。
			Symbol typesSymbol = SymbolUtils.getArgument(ip, 0);
			List<String> prefixList = null;
			if (SymbolUtils.getArgumentSize(ip) > 1) {
				Symbol prefixSymbol = SymbolUtils.getArgument(ip, 1);
				prefixList = operator.getJavaStringList(prefixSymbol);
			}
			for (String typesString : operator.getJavaStringList(typesSymbol)) {
				for (String c : typesString.split("")) {
					String name;
					if (c.equalsIgnoreCase("g")) {
						name = "$_GET";
					} else if (c.equalsIgnoreCase("p")) {
						name = "$_POST";
					} else if (c.equalsIgnoreCase("c")) {
						name = "$_COOKIE";
					} else {
						continue;
					}
					Symbol symbol = ip.getSymbolOrCreate(name);
					for (PhpArray phpArray : operator.extractPhpArray(symbol)) {
						for (Entry<SymbolId, SymbolId> entry : phpArray.getArray().entrySet()) {
							Symbol keySymbol = operator.getSymbol(entry.getKey());
							Symbol valueSymbol = operator.getSymbol(entry.getValue());
							if (keySymbol == null || valueSymbol == null) {
								continue;
							}
							for (String keyString : operator.getJavaStringList(keySymbol)) {
								if (prefixList == null) {
									ip.getGlobalScope().put(keyString, valueSymbol);
								} else {
									for (String prefix : prefixList) {
										ip.getGlobalScope().put(prefix + keyString, valueSymbol);
									}
								}
							}
						}
					}
				}
			}
			return operator.bool();
		}
	}

	// function error_log ($message, $message_type = null, $destination = null, $extra_headers = null) {}
	public static class error_log implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// TODO ファイル書き込みに対応する。
			return operator.bool();
		}
	}

}
