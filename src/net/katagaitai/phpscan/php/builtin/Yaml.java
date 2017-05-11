package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Yaml extends BuiltinBase {
	public Yaml(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\YAML_ANY_SCALAR_STYLE", operator.createSymbol(0));
		putConstant("\\YAML_PLAIN_SCALAR_STYLE", operator.createSymbol(1));
		putConstant("\\YAML_SINGLE_QUOTED_SCALAR_STYLE", operator.createSymbol(2));
		putConstant("\\YAML_DOUBLE_QUOTED_SCALAR_STYLE", operator.createSymbol(3));
		putConstant("\\YAML_LITERAL_SCALAR_STYLE", operator.createSymbol(4));
		putConstant("\\YAML_FOLDED_SCALAR_STYLE", operator.createSymbol(5));
		putConstant("\\YAML_NULL_TAG", operator.createSymbol("tag:yaml.org,2002:null"));
		putConstant("\\YAML_BOOL_TAG", operator.createSymbol("tag:yaml.org,2002:bool"));
		putConstant("\\YAML_STR_TAG", operator.createSymbol("tag:yaml.org,2002:str"));
		putConstant("\\YAML_INT_TAG", operator.createSymbol("tag:yaml.org,2002:int"));
		putConstant("\\YAML_FLOAT_TAG", operator.createSymbol("tag:yaml.org,2002:float"));
		putConstant("\\YAML_TIMESTAMP_TAG", operator.createSymbol("tag:yaml.org,2002:timestamp"));
		putConstant("\\YAML_SEQ_TAG", operator.createSymbol("tag:yaml.org,2002:seq"));
		putConstant("\\YAML_MAP_TAG", operator.createSymbol("tag:yaml.org,2002:map"));
		putConstant("\\YAML_PHP_TAG", operator.createSymbol("!php/object"));
		putConstant("\\YAML_MERGE_TAG", operator.createSymbol("tag:yaml.org,2002:merge"));
		putConstant("\\YAML_BINARY_TAG", operator.createSymbol("tag:yaml.org,2002:binary"));
		putConstant("\\YAML_ANY_ENCODING", operator.createSymbol(0));
		putConstant("\\YAML_UTF8_ENCODING", operator.createSymbol(1));
		putConstant("\\YAML_UTF16LE_ENCODING", operator.createSymbol(2));
		putConstant("\\YAML_UTF16BE_ENCODING", operator.createSymbol(3));
		putConstant("\\YAML_ANY_BREAK", operator.createSymbol(0));
		putConstant("\\YAML_CR_BREAK", operator.createSymbol(1));
		putConstant("\\YAML_LN_BREAK", operator.createSymbol(2));
		putConstant("\\YAML_CRLN_BREAK", operator.createSymbol(3));
	}

	// function yaml_parse ($input, $pos = null, &$ndocs = null, array $callbacks = null) {}
	public static class yaml_parse implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object(SymbolUtils.getArgument(ip, 0));
		}
	}

	// function yaml_parse_file ($filename, $pos = null, &$ndocs = null, array $callbacks = null) {}
	public static class yaml_parse_file implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function yaml_parse_url ($url, $pos = null, &$ndocs = null, array $callbacks = null) {}
	public static class yaml_parse_url implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.object();
		}
	}

	// function yaml_emit ($data, $encoding = null, $linebreak = null, array $callbacks = null) {}
	public static class yaml_emit implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return serialize(ip, SymbolUtils.getArgument(ip, 0));
		}
	}

	// function yaml_emit_file ($filename, $data, $encoding = null, $linebreak = null, array $callbacks = null) {}
	public static class yaml_emit_file implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.bool();
		}
	}

}
