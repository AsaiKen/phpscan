package net.katagaitai.phpscan.php.builtin;

import java.util.List;

import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import com.google.common.collect.Lists;

public class Pcre extends BuiltinBase {
	public Pcre(Interpreter ip) {
		super(ip);
		SymbolOperator operator = ip.getOperator();
		putConstant("\\PREG_PATTERN_ORDER", operator.createSymbol(1));
		putConstant("\\PREG_JIT_STACKLIMIT_ERROR", operator.createSymbol(6));
		putConstant("\\PREG_SET_ORDER", operator.createSymbol(2));
		putConstant("\\PREG_OFFSET_CAPTURE", operator.createSymbol(256));
		putConstant("\\PREG_SPLIT_NO_EMPTY", operator.createSymbol(1));
		putConstant("\\PREG_SPLIT_DELIM_CAPTURE", operator.createSymbol(2));
		putConstant("\\PREG_SPLIT_OFFSET_CAPTURE", operator.createSymbol(4));
		putConstant("\\PREG_GREP_INVERT", operator.createSymbol(1));
		putConstant("\\PREG_NO_ERROR", operator.createSymbol(0));
		putConstant("\\PREG_INTERNAL_ERROR", operator.createSymbol(1));
		putConstant("\\PREG_BACKTRACK_LIMIT_ERROR", operator.createSymbol(2));
		putConstant("\\PREG_RECURSION_LIMIT_ERROR", operator.createSymbol(3));
		putConstant("\\PREG_BAD_UTF8_ERROR", operator.createSymbol(4));
		putConstant("\\PREG_BAD_UTF8_OFFSET_ERROR", operator.createSymbol(5));
		putConstant("\\PCRE_VERSION", operator.createSymbol("8.31 2012-07-06"));
	}

	// function preg_match ($pattern, $subject, array &$matches = null, $flags = 0, $offset = 0) {}
	public static class preg_match implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol matchesSymbol = SymbolUtils.getArgumentRef(ip, 2);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 1);
			operator.merge(matchesSymbol, operator.createSymbol(operator.createPhpArrayDummy(subjectSymbol)));
			return operator.integer();
		}
	}

	// function preg_match_all ($pattern, $subject, array &$matches = null, $flags = PREG_PATTERN_ORDER, $offset = 0) {}
	public static class preg_match_all implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol matchesSymbol = SymbolUtils.getArgumentRef(ip, 2);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 1);
			operator.merge(matchesSymbol, operator.createSymbol(operator.createPhpArrayDummy(subjectSymbol)));
			return operator.integer();
		}
	}

	// function preg_replace ($pattern, $replacement, $subject, $limit = -1, &$count = null) {}
	// http://php.net/manual/ja/function.preg-replace.php
	// 非推奨の e 修飾子を使用する際に、 この関数は後方参照を置換する文字列のうちの特定の文字 (具体的には '、"、 \ および NULL) をエスケープします。
	// これは、後方参照をシングルクォートやダブルクォートを共用した場合 (たとえば 'strlen(\'$1\')+strlen("$2")') に構文エラーが発生しないようにするためのものです。
	public static class preg_replace implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol replacementSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 2);
			operator.addNewTaintSet(subjectSymbol, replacementSymbol.getTaintSet());
			return subjectSymbol;
		}
	}

	// function preg_replace_callback ($pattern, callable $callback, $subject, $limit = -1, &$count = null) {}
	public static class preg_replace_callback implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 2);
			Symbol replacementSymbol = operator.createSymbol();
			for (PhpAnyType type : operator.getTypeSet(subjectSymbol)) {
				List<Symbol> list = Lists.newArrayList();
				if (type instanceof PhpArray) {
					PhpArray phpArray = (PhpArray) type;
					list.add(operator.array(operator.createSymbol(phpArray)));
				} else {
					list.add(operator.array(operator.createSymbol(operator.cast(type, CastType.STRING))));
				}
				operator.merge(replacementSymbol, SymbolUtils.callCallable(ip, callbackSymbol, list));
			}
			operator.addNewTaintSet(subjectSymbol, replacementSymbol.getTaintSet());
			return subjectSymbol;
		}
	}

	// function preg_replace_callback_array ($patterns_and_callbacks, $subject , $limit = -1, &$count ) {}
	public static class preg_replace_callback_array implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol patternsandcallbacksSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol callbackSymbol = operator.getMergedArrayValueSymbol(patternsandcallbacksSymbol);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol replacementSymbol = operator.createSymbol();
			for (PhpAnyType type : operator.getTypeSet(subjectSymbol)) {
				List<Symbol> list = Lists.newArrayList();
				if (type instanceof PhpArray) {
					PhpArray phpArray = (PhpArray) type;
					list.add(operator.array(operator.createSymbol(phpArray)));
				} else {
					list.add(operator.array(operator.createSymbol(operator.cast(type, CastType.STRING))));
				}
				operator.merge(replacementSymbol, SymbolUtils.callCallable(ip, callbackSymbol, list));
			}
			operator.addNewTaintSet(subjectSymbol, replacementSymbol.getTaintSet());
			return subjectSymbol;
		}
	}

	// function preg_filter ($pattern, $replacement, $subject, $limit = -1, &$count = null) {}
	public static class preg_filter implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			Symbol replacementSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol subjectSymbol = SymbolUtils.getArgument(ip, 2);
			operator.addNewTaintSet(subjectSymbol, replacementSymbol.getTaintSet());
			return subjectSymbol;
		}
	}

	// function preg_split ($pattern, $subject, $limit = -1, $flags = 0) {}
	public static class preg_split implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.array(SymbolUtils.getArgument(ip, 1));
		}
	}

	// function preg_quote ($str, $delimiter = null) {}
	public static class preg_quote implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 0);
		}
	}

	// function preg_grep ($pattern, array $input, $flags = 0) {}
	public static class preg_grep implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			return SymbolUtils.getArgument(ip, 1);
		}
	}

	// function preg_last_error () {}
	public static class preg_last_error implements PhpCallable {
		@Override
		public Symbol call(Interpreter ip) {
			SymbolOperator operator = ip.getOperator();
			return operator.integer();
		}
	}

}
