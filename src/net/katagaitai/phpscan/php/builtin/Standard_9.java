package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Standard_8.count;
import net.katagaitai.phpscan.php.builtin.Standard_8.current;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Standard_9 extends BuiltinBase {
	public Standard_9(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\ARRAY_FILTER_USE_BOTH", operator.createSymbol(1));
		putConstant("\\ARRAY_FILTER_USE_KEY", operator.createSymbol(2));
	}

	// function array_merge_recursive(array $array1, array $_ = null) { }
	public static class array_merge_recursive implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			PhpArray phpArray = operator.createPhpArray();
			for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
				Symbol arraySymbol = SymbolUtils.getArgument(ip, i);
				operator.putArrayValue(phpArray, operator.getMergedArrayKeySymbol(arraySymbol),
						operator.getMergedArrayValueSymbol(arraySymbol));
			}
			return operator.createSymbol(phpArray);
		}
	}

	// function array_replace(array $array, array $array1, array $array2 = null, array $_ = null) { }
	public static class array_replace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				Symbol symbol = SymbolUtils.getArgument(ip, i);
				for (PhpArray phpArray : operator.extractPhpArray(symbol)) {
					operator.putArrayValue(arraySymbol, operator.getNextIndex(arraySymbol),
							operator.getMergedArrayValueSymbol(phpArray));
				}
			}
			return arraySymbol;
		}
	}

	// function array_replace_recursive(array $array, array $array1, array $array2 = null, array $_ = null) { }
	public static class array_replace_recursive implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				Symbol symbol = SymbolUtils.getArgument(ip, i);
				for (PhpArray phpArray : operator.extractPhpArray(symbol)) {
					operator.putArrayValue(arraySymbol, operator.getNextIndex(arraySymbol),
							operator.getMergedArrayValueSymbol(phpArray));
				}
			}
			return arraySymbol;
		}
	}

	// function array_keys(array $input, $search_value = null, $strict = null) { }
	public static class array_keys implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			return operator.array(operator.getMergedArrayKeySymbol(inputSymbol));
		}
	}

	// function array_values(array $input) { }
	public static class array_values implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			return operator.array(operator.getMergedArrayValueSymbol(inputSymbol));
		}
	}

	// function array_count_values(array $input) { }
	public static class array_count_values implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			PhpArray phpArray = operator.createPhpArray();
			operator.putArrayValue(phpArray, operator.getMergedArrayKeySymbol(inputSymbol), operator.integer());
			return operator.createSymbol(phpArray);
		}
	}

	// function array_column(array $array, $column, $index_key = null) { }
	public static class array_column implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
			Symbol columnSymbol = SymbolUtils.getArgument(ip, 1);
			PhpArray resultArray = operator.createPhpArray();
			// ２次元配列
			for (PhpArray phpArray : operator.extractPhpArray(arraySymbol)) {
				for (PhpArray phpArray2 : operator.extractPhpArray(operator.getMergedArrayValueSymbol(phpArray))) {
					Symbol valuesSymbol = operator.getArrayValue(phpArray2, columnSymbol);
					if (!operator.isNull(valuesSymbol)) {
						operator.putArrayValue(resultArray, operator.getNextIndex(resultArray), valuesSymbol);
					}
				}
			}
			return operator.createSymbol(resultArray);
		}
	}

	// function array_reverse(array $array, $preserve_keys = null) { }
	public static class array_reverse implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_reduce(array $input, $function, $initial = null) { }
	public static class array_reduce implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol functionSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol initialSymbol = SymbolUtils.getArgument(ip, 2);
			return SymbolUtils.callCallable(ip, functionSymbol,
					Lists.newArrayList(initialSymbol, operator.getMergedArrayValueSymbol(inputSymbol)));
		}
	}

	// function array_pad(array $input, $pad_size, $pad_value) { }
	public static class array_pad implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol padvalueSymbol = SymbolUtils.getArgument(ip, 2);
			operator.putArrayValue(inputSymbol, operator.getNextIndex(inputSymbol), padvalueSymbol);
			return inputSymbol;
		}
	}

	// function array_flip(array $trans) { }
	public static class array_flip implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol transSymbol = SymbolUtils.getArgument(ip, 0);
			PhpArray resultArray = operator.createPhpArray();
			operator.putArrayValue(resultArray, operator.getMergedArrayValueSymbol(transSymbol),
					operator.getMergedArrayKeySymbol(transSymbol));
			return operator.createSymbol(resultArray);
		}
	}

	// function array_change_key_case(array $input, $case = null) { }
	public static class array_change_key_case implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_rand(array $input, $num_req = null) { }
	public static class array_rand implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol mergedKeySymbol = operator.getMergedArrayKeySymbol(inputSymbol);
			if (SymbolUtils.getArgumentSize(ip) == 1) {
				return mergedKeySymbol;
			} else {
				Symbol resultSymbol = operator.createSymbol();
				Symbol numreqSymbol = SymbolUtils.getArgument(ip, 1);
				for (long long_ : operator.getJavaLongList(numreqSymbol)) {
					if (long_ > 1) {
						operator.merge(resultSymbol, operator.array(mergedKeySymbol));
					} else {
						operator.merge(resultSymbol, mergedKeySymbol);
					}
				}
				return resultSymbol;
			}
		}
	}

	// function array_unique(array $array, $sort_flags = null) { }
	public static class array_unique implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_intersect(array $array1, array $array2, array $_ = null) { }
	public static class array_intersect implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_intersect_key(array $array1, array $array2, array $_ = null) { }
	public static class array_intersect_key implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_intersect_ukey(array $array1, array $array2, array $_ = null, $key_compare_func) { }
	public static class array_intersect_ukey implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_uintersect(array $array1, array $array2, array $_ = null, $data_compare_func) { }
	public static class array_uintersect implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_intersect_assoc(array $array1, array $array2, array $_ = null) { }
	public static class array_intersect_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_uintersect_assoc(array $array1, array $array2, array $_ = null, $data_compare_func) { }
	public static class array_uintersect_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_intersect_uassoc(array $array1, array $array2, array $_ = null, $key_compare_func) { }
	public static class array_intersect_uassoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_uintersect_uassoc(array $array1, array $array2, array $_ = null, $data_compare_func, $key_compare_func) { }
	public static class array_uintersect_uassoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -2);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 2; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_diff(array $array1, array $array2, array $_ = null) { }
	public static class array_diff implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_diff_key(array $array1, array $array2, array $_ = null) { }
	public static class array_diff_key implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_diff_ukey(array $array1, array $array2, array $_ = null, $key_compare_func) { }
	public static class array_diff_ukey implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_udiff(array $array1, array $array2, array $_ = null, $data_compare_func) { }
	public static class array_udiff implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_diff_assoc(array $array1, array $array2, array $_ = null) { }
	public static class array_diff_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function array_udiff_assoc(array $array1, array $array2, array $_ = null, $data_compare_func) { }
	public static class array_udiff_assoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_diff_uassoc(array $array1, array $array2, array $_ = null, $key_compare_func) { }
	public static class array_diff_uassoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 1; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_udiff_uassoc(array $array1, array $array2, array $_ = null, $data_compare_func, $key_compare_func) { }
	public static class array_udiff_uassoc implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol array1Symbol = SymbolUtils.getArgument(ip, 0);
			Symbol keycomparefuncSymbol = SymbolUtils.getArgument(ip, -1);
			Symbol datacomparefuncSymbol = SymbolUtils.getArgument(ip, -2);
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip) - 2; i++) {
				Symbol array2Symbol = SymbolUtils.getArgument(ip, i);
				SymbolUtils.callCallable(
						ip,
						keycomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayKeySymbol(array1Symbol),
								operator.getMergedArrayKeySymbol(array2Symbol)));
				SymbolUtils.callCallable(
						ip,
						datacomparefuncSymbol,
						Lists.newArrayList(operator.getMergedArrayValueSymbol(array1Symbol),
								operator.getMergedArrayValueSymbol(array2Symbol)));
			}
			return array1Symbol;
		}
	}

	// function array_sum(array $array) { }
	public static class array_sum implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function array_product(array $array) { }
	public static class array_product implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function array_filter(array $input, $callback = null, $flag = 0) { }
	public static class array_filter implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
			SymbolUtils.callCallable(ip, callbackSymbol,
					Lists.newArrayList(operator.getMergedArrayValueSymbol(inputSymbol)));
			return inputSymbol;
		}
	}

	// function array_map($callback, array $arr1, array $_ = null) { }
	public static class array_map implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol callbackSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 1);
			List<Symbol> list = Lists.newArrayList(operator.getMergedArrayValueSymbol(inputSymbol));
			for (int i = 2; i < SymbolUtils.getArgumentSize(ip); i++) {
				list.add(SymbolUtils.getArgument(ip, i));
			}
			PhpArray resultArray = operator.createPhpArray();
			operator.putArrayValue(resultArray, 0, SymbolUtils.callCallable(ip, callbackSymbol, list));
			return operator.createSymbol(resultArray);
		}
	}

	// function array_chunk(array $input, $size, $preserve_keys = null) { }
	public static class array_chunk implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol inputSymbol = SymbolUtils.getArgument(ip, 0);
			return operator.array(inputSymbol);
		}
	}

	// function array_combine(array $keys, array $values) { }
	public static class array_combine implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol keysSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valuesSymbol = SymbolUtils.getArgument(ip, 1);
			PhpArray resultArray = operator.createPhpArray();
			operator.putArrayValue(resultArray, operator.getMergedArrayValueSymbol(keysSymbol),
					operator.getMergedArrayValueSymbol(valuesSymbol));
			return operator.createSymbol(resultArray);
		}
	}

	// function array_key_exists($key, array $search) { }
	public static class array_key_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function pos(&$arg) { }
	//  * &Alias; <function>current</function>
	public static class pos extends current {
	}

	// function sizeof($var, $mode) { }
	//  * &Alias; <function>count</function>
	public static class sizeof extends count {
	}

	// function key_exists($key, $search) { }
	public static class key_exists implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function assert($assertion, $description) { }
	public static class assert_ implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function cli_get_process_title() { }
	public static class cli_get_process_title implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function cli_set_process_title($title) { }
	public static class cli_set_process_title implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function assert_options($what, $value = null) { }
	public static class assert_options implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function version_compare($version1, $version2, $operator = null) { }
	public static class version_compare implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function ftok($pathname, $proj) { }
	public static class ftok implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

	// function str_rot13($str) { }
	public static class str_rot13 implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function stream_get_filters() { }
	public static class stream_get_filters implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function stream_filter_register($filtername, $classname) { }
	public static class stream_filter_register implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function stream_bucket_make_writeable($brigade) { }
	public static class stream_bucket_make_writeable implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function stream_bucket_prepend($brigade, $bucket) { }
	public static class stream_bucket_prepend implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function stream_bucket_append($brigade, $bucket) { }
	public static class stream_bucket_append implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.null_();
		}
	}

	// function stream_bucket_new($stream, $buffer) { }
	public static class stream_bucket_new implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function output_add_rewrite_var($name, $value) { }
	public static class output_add_rewrite_var implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function output_reset_rewrite_vars() { }
	public static class output_reset_rewrite_vars implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function sys_get_temp_dir() { }
	public static class sys_get_temp_dir implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	// function realpath_cache_get() { }
	public static class realpath_cache_get implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function realpath_cache_size() { }
	public static class realpath_cache_size implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

}
