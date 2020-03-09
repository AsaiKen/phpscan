package net.katagaitai.phpscan.php.builtin;

import java.util.Set;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Sets;

public class Filter extends BuiltinBase {
	public Filter(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\INPUT_POST", operator.createSymbol(0));
		putConstant("\\INPUT_GET", operator.createSymbol(1));
		putConstant("\\INPUT_COOKIE", operator.createSymbol(2));
		putConstant("\\INPUT_ENV", operator.createSymbol(4));
		putConstant("\\INPUT_SERVER", operator.createSymbol(5));
		putConstant("\\INPUT_SESSION", operator.createSymbol(6));
		putConstant("\\INPUT_REQUEST", operator.createSymbol(99));
		putConstant("\\FILTER_FLAG_NONE", operator.createSymbol(0));
		putConstant("\\FILTER_REQUIRE_SCALAR", operator.createSymbol(33554432));
		putConstant("\\FILTER_REQUIRE_ARRAY", operator.createSymbol(16777216));
		putConstant("\\FILTER_FORCE_ARRAY", operator.createSymbol(67108864));
		putConstant("\\FILTER_NULL_ON_FAILURE", operator.createSymbol(134217728));
		putConstant("\\FILTER_VALIDATE_INT", operator.createSymbol(257));
		putConstant("\\FILTER_VALIDATE_BOOLEAN", operator.createSymbol(258));
		putConstant("\\FILTER_VALIDATE_FLOAT", operator.createSymbol(259));
		putConstant("\\FILTER_VALIDATE_REGEXP", operator.createSymbol(272));
		putConstant("\\FILTER_VALIDATE_URL", operator.createSymbol(273));
		putConstant("\\FILTER_VALIDATE_EMAIL", operator.createSymbol(274));
		putConstant("\\FILTER_VALIDATE_IP", operator.createSymbol(275));
		putConstant("\\FILTER_VALIDATE_MAC", operator.createSymbol(276));
		putConstant("\\FILTER_DEFAULT", operator.createSymbol(516));
		putConstant("\\FILTER_UNSAFE_RAW", operator.createSymbol(516));
		putConstant("\\FILTER_SANITIZE_STRING", operator.createSymbol(513));
		putConstant("\\FILTER_SANITIZE_STRIPPED", operator.createSymbol(513));
		putConstant("\\FILTER_SANITIZE_ENCODED", operator.createSymbol(514));
		putConstant("\\FILTER_SANITIZE_SPECIAL_CHARS", operator.createSymbol(515));
		putConstant("\\FILTER_SANITIZE_FULL_SPECIAL_CHARS", operator.createSymbol(522));
		putConstant("\\FILTER_SANITIZE_EMAIL", operator.createSymbol(517));
		putConstant("\\FILTER_SANITIZE_URL", operator.createSymbol(518));
		putConstant("\\FILTER_SANITIZE_NUMBER_INT", operator.createSymbol(519));
		putConstant("\\FILTER_SANITIZE_NUMBER_FLOAT", operator.createSymbol(520));
		putConstant("\\FILTER_SANITIZE_MAGIC_QUOTES", operator.createSymbol(521));
		putConstant("\\FILTER_CALLBACK", operator.createSymbol(1024));
		putConstant("\\FILTER_FLAG_ALLOW_OCTAL", operator.createSymbol(1));
		putConstant("\\FILTER_FLAG_ALLOW_HEX", operator.createSymbol(2));
		putConstant("\\FILTER_FLAG_STRIP_LOW", operator.createSymbol(4));
		putConstant("\\FILTER_FLAG_STRIP_HIGH", operator.createSymbol(8));
		putConstant("\\FILTER_FLAG_STRIP_BACKTICK", operator.createSymbol(512));
		putConstant("\\FILTER_FLAG_ENCODE_LOW", operator.createSymbol(16));
		putConstant("\\FILTER_FLAG_ENCODE_HIGH", operator.createSymbol(32));
		putConstant("\\FILTER_FLAG_ENCODE_AMP", operator.createSymbol(64));
		putConstant("\\FILTER_FLAG_NO_ENCODE_QUOTES", operator.createSymbol(128));
		putConstant("\\FILTER_FLAG_EMPTY_STRING_NULL", operator.createSymbol(256));
		putConstant("\\FILTER_FLAG_ALLOW_FRACTION", operator.createSymbol(4096));
		putConstant("\\FILTER_FLAG_ALLOW_THOUSAND", operator.createSymbol(8192));
		putConstant("\\FILTER_FLAG_ALLOW_SCIENTIFIC", operator.createSymbol(16384));
		putConstant("\\FILTER_FLAG_SCHEME_REQUIRED", operator.createSymbol(65536));
		putConstant("\\FILTER_FLAG_HOST_REQUIRED", operator.createSymbol(131072));
		putConstant("\\FILTER_FLAG_PATH_REQUIRED", operator.createSymbol(262144));
		putConstant("\\FILTER_FLAG_QUERY_REQUIRED", operator.createSymbol(524288));
		putConstant("\\FILTER_FLAG_IPV4", operator.createSymbol(1048576));
		putConstant("\\FILTER_FLAG_IPV6", operator.createSymbol(2097152));
		putConstant("\\FILTER_FLAG_NO_RES_RANGE", operator.createSymbol(4194304));
		putConstant("\\FILTER_FLAG_NO_PRIV_RANGE", operator.createSymbol(8388608));
	}

	// function filter_input ($type, $variable_name, $filter = FILTER_DEFAULT, $options = null) {}
	public static class filter_input implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol typeSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol variablenameSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol resultSymbol = operator.createSymbol();
			for (long type : operator.getJavaLongList(typeSymbol)) {
				Symbol targetSymbol = getSymbol(ip, type);
				Symbol valueSymbol = operator.getArrayValue(targetSymbol, variablenameSymbol);
				Set<PhpAnyType> tSet = Sets.newHashSet(operator.getTypeSet(valueSymbol));
				Symbol symbol = operator.createSymbol(tSet);
				operator.addNewTaintSet(symbol, valueSymbol.getTaintSet());
				operator.merge(resultSymbol, symbol);
			}
			return resultSymbol;
		}
	}

	// function filter_var ($variable, $filter = FILTER_DEFAULT, $options = null) {}
	public static class filter_var implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function filter_input_array ($type, $definition = null, $add_empty = true) {}
	public static class filter_input_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol typeSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol definitionSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol keySymbol = operator.createSymbol();
			for (PhpArray phpArray : operator.extractPhpArray(definitionSymbol)) {
				operator.merge(keySymbol, operator.getMergedArrayKeySymbol(phpArray));
			}
			Symbol resultSymbol = operator.createSymbol();
			for (long type : operator.getJavaLongList(typeSymbol)) {
				Symbol targetSymbol = getSymbol(ip, type);
				Symbol valueSymbol = operator.getArrayValue(targetSymbol, keySymbol);
				Set<PhpAnyType> tSet = Sets.newHashSet(operator.getTypeSet(valueSymbol));
				Symbol symbol = operator.createSymbol(tSet);
				operator.addNewTaintSet(symbol, valueSymbol.getTaintSet());
				operator.merge(resultSymbol, symbol);
			}
			return resultSymbol;
		}
	}

	// function filter_var_array (array $data, $definition = null, $add_empty = true) {}
	public static class filter_var_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol dataSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol valueSymbol = operator.createSymbol();
			for (PhpArray phpArray : operator.extractPhpArray(dataSymbol)) {
				operator.merge(valueSymbol, operator.getMergedArrayValueSymbol(phpArray));
			}
			Set<PhpAnyType> tSet = Sets.newHashSet(operator.getTypeSet(valueSymbol));
			Symbol symbol = operator.createSymbol(tSet);
			operator.addNewTaintSet(symbol, valueSymbol.getTaintSet());
			return symbol;
		}
	}

	// function filter_list () {}
	public static class filter_list implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array();
		}
	}

	// function filter_has_var ($type, $variable_name) {}
	public static class filter_has_var implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

	// function filter_id ($filtername) {}
	public static class filter_id implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.string();
		}
	}

	public static Symbol getSymbol(Interpreter ip, long type) {
		String target = "$_REQUEST";
		if (type == 0) {
			target = "$_POST";
		} else if (type == 1) {
			target = "$_GET";
		} else if (type == 2) {
			target = "$_COOKIE";
		} else if (type == 4) {
			target = "$_ENV";
		} else if (type == 5) {
			target = "$_SERVER";
		} else if (type == 6) {
			target = "$_SESSION";
		} else if (type == 99) {
			target = "$_REQUEST";
		}
		Symbol targetSymbol = ip.getGlobalScope().getOrPhpNull(target);
		return targetSymbol;
	}

}
