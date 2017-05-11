package net.katagaitai.phpscan.php.builtin;

import java.util.Set;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Sets;

public class Standard_2 extends BuiltinBase {
	public Standard_2(Interpreter ip) {
		super(ip);
	}

	// function nl_langinfo ($item) {}
	public static class nl_langinfo implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function soundex ($str) {}
	public static class soundex implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function levenshtein ($str1, $str2, $cost_ins = null, $cost_rep = null, $cost_del = null) {}
	public static class levenshtein implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function chr ($ascii) {}
	public static class chr implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function ord ($string) {}
	public static class ord implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function parse_str ($str, array &$arr = null) {}
	// URL 経由で渡されるクエリ文字列と同様に str を処理し、現在のスコープに変数をセットします。
	// 2 番目の引数 arr が指定された場合、 変数は、代わりに配列の要素としてこの変数に保存されます。
	public static class parse_str implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			int size = SymbolUtils.getArgumentSize(ip);
			Symbol strSymbol = SymbolUtils.getArgument(ip, 0);
			if (size >= 2) {
				Symbol arrSymbol = SymbolUtils.getArgumentRef(ip, 1);
				operator.assign(arrSymbol, operator.array(strSymbol));
			} else {
				for (String string : operator.getJavaStringList(strSymbol)) {
					String[] params = string.split("&");
					for (String param : params) {
						String[] kv = param.split("=", 2);
						String key = kv[0];
						String value = "";
						if (kv.length >= 2) {
							value = kv[1];
						}
						Symbol valueSymbol = operator.createSymbol(new PhpString(value));
						operator.addNewTaintSet(valueSymbol, strSymbol.getTaintSet());
						if (key.matches(".*\\[[^\\]]*\\]")) {
							// a[b]=1
							int start = key.indexOf("[");
							int end = key.lastIndexOf("]");
							String array = key.substring(0, start);
							Symbol arraySymbol = ip.getSymbolOrCreate("$" + array);
							if (start + 1 == end) {
								// a[] = 1
								Symbol nextKeySymbol = operator.getNextIndex(arraySymbol);
								operator.putArrayValue(arraySymbol, nextKeySymbol, valueSymbol);
							} else {
								// a[b] = 1
								String arrayKey = key.substring(start + 1, end);
								Symbol keySymbol = operator.createSymbol(new PhpString(arrayKey));
								operator.addNewTaintSet(keySymbol, strSymbol.getTaintSet());
								operator.putArrayValue(arraySymbol, keySymbol, valueSymbol);
							}
						} else {
							// a=1
							Symbol symbol = ip.getSymbolOrCreate("$" + key);
							operator.merge(symbol, valueSymbol);
						}
					}
				}
			}
			return operator.null_();
		}
	}

	// function str_getcsv ($input, $delimiter = null, $enclosure = null, $escape = null) {}
	public static class str_getcsv implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(SymbolUtils.getArgument(ip, 0));
		}
	}

	// function str_pad ($input, $pad_length, $pad_string = null, $pad_type = null) {}
	public static class str_pad implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol padstringSymbol = SymbolUtils.getArgument(ip, 2);
			operator.addNewTaintSet(inputSymbol, padstringSymbol.getTaintSet());
			return inputSymbol;
		}
	}

	// function chop ($str, $character_mask) {}
	public static class chop implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function strchr ($haystack, $needle, $part) {}
	public static class strchr implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function sprintf ($format, $args = null, $_ = null) {}
	public static class sprintf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			// sprintf("%d %d", 1, 1); == "1 1"
			Symbol formatSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = Sets.newHashSet();
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				taintSet.addAll(SymbolUtils.getArgument(ip, i).getTaintSet());
			}
			operator.addNewTaintSet(formatSymbol, taintSet);
			return formatSymbol;
		}
	}

	// function printf ($format, $args = null, $_ = null) {}
	public static class printf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function vprintf ($format, array $args) {}
	public static class vprintf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function vsprintf ($format, array $args) {}
	public static class vsprintf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol formatSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol argsSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol argsValueSymbol = operator.getMergedArrayValueSymbol(argsSymbol);
			operator.addNewTaintSet(formatSymbol, argsValueSymbol.getTaintSet());
			return formatSymbol;
		}
	}

	// function fprintf ($handle, $format, $args = null, $_ = null) {}
	public static class fprintf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function vfprintf ($handle, $format, array $args) {}
	public static class vfprintf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function sscanf ($str, $format, &$_ = null) {}
	public static class sscanf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(SymbolUtils.getArgument(ip, 0));
		}
	}

	// function fscanf ($handle, $format, &$_ = null) {}
	public static class fscanf implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valueSymbol = operator.getResourceValue(handleSymbol, Standard_5.OPEN_CONTENT);
			return operator.array(valueSymbol);
		}
	}

	// function parse_url ($url, $component = -1) {}
	public static class parse_url implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(SymbolUtils.getArgument(ip, 0));
		}
	}

	// function urlencode ($str) {}
	public static class urlencode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function urldecode ($str) {}
	public static class urldecode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function rawurlencode ($str) {}
	public static class rawurlencode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function rawurldecode ($str) {}
	public static class rawurldecode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function http_build_query ($query_data, $numeric_prefix = null, $arg_separator = null, $enc_type = PHP_QUERY_RFC1738){}
	// 与えられた連想配列 (もしくは添字配列) から URL エンコードされたクエリ文字列を生成します。
	public static class http_build_query implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol querydataSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol resultSymbol = operator.createSymbol();
			operator.merge(resultSymbol, operator.getMergedArrayKeySymbol(querydataSymbol));
			operator.merge(resultSymbol, operator.getMergedArrayValueSymbol(querydataSymbol));
			return resultSymbol;
		}
	}

	// function readlink ($path) {}
	public static class readlink implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function linkinfo ($path) {}
	public static class linkinfo implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function symlink ($target, $link) {}
	public static class symlink implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function link (string $target , string $link):bool {}
	public static class link implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function unlink ($filename, $context = null) {}
	public static class unlink implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function exec ($command, array &$output = null, &$return_var = null) {}
	public static class exec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function system ($command, &$return_var = null) {}
	public static class system implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function escapeshellcmd ($command) {}
	public static class escapeshellcmd implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function escapeshellarg ($arg) {}
	public static class escapeshellarg implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function passthru ($command, &$return_var = null) {}
	public static class passthru implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function shell_exec ($cmd) {}
	public static class shell_exec implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function proc_open ($cmd, array $descriptorspec, array &$pipes, $cwd = null, array $env = null, array $other_options = null) {}
	public static class proc_open implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpResource phpResource = operator.createPhpResource();
			operator.putResourceValue(phpResource, Standard_5.OPEN_CONTENT, operator.string());
			if (SymbolUtils.getArgumentSize(ip) > 2) {
				Symbol pipesSymbol = SymbolUtils.getArgumentRef(ip, 2);
				operator.putArrayValue(pipesSymbol, operator.createSymbol(0),
						operator.createSymbol(phpResource));
				operator.putArrayValue(pipesSymbol, operator.createSymbol(1),
						operator.createSymbol(phpResource));
			}
			// 不正確だが検査では問題ないはず
			return operator.createSymbol(phpResource);
		}
	}

	// function proc_close ($process) {}
	public static class proc_close implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function proc_terminate ($process, $signal = null) {}
	public static class proc_terminate implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function proc_get_status ($process) {}
	public static class proc_get_status implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function proc_nice ($increment) {}
	public static class proc_nice implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function rand ($min, $max) {}
	public static class rand implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function srand ($seed = null) {}
	public static class srand implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function getrandmax () {}
	public static class getrandmax implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mt_rand ($min, $max) {}
	public static class mt_rand implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function mt_srand ($seed = null) {}
	public static class mt_srand implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function mt_getrandmax () {}
	public static class mt_getrandmax implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getservbyname ($service, $protocol) {}
	public static class getservbyname implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getservbyport ($port, $protocol) {}
	public static class getservbyport implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function getprotobyname ($name) {}
	public static class getprotobyname implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getprotobynumber ($number) {}
	public static class getprotobynumber implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function getmyuid () {}
	public static class getmyuid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getmygid () {}
	public static class getmygid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getmypid () {}
	public static class getmypid implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function getmyinode () {}
	public static class getmyinode implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

}
