package net.katagaitai.phpscan.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.command.AssignArgument;
import net.katagaitai.phpscan.command.AssignArgumentReference;
import net.katagaitai.phpscan.command.Cast;
import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.Mix;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.interceptor.pass.TaintPasser;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerLFICommand;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerPTCall;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerRCECall;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerRCEReflection;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerSQLICall;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerSSRFCall;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerXSSCall;
import net.katagaitai.phpscan.interceptor.sink.TaintCheckerXSSCommand;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerCallUserXxx;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerCurlAPI;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerFileAPI;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerParseStr;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerReflection;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerSuperGlobals;
import net.katagaitai.phpscan.interceptor.source.TaintInitializerUnserialize;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.SourcePosition;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.EncodeTag;
import net.katagaitai.phpscan.taint.Taint;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TaintUtils {
	public static String[] DANGEROUS_SUPERGLOBAL_NAMES = new String[] {
			"$_COOKIE", "$_GET", "$_POST", "$_REQUEST",
			"$HTTP_RAW_POST_DATA" };

	public static void applyInitialTaintRecursive(Interpreter ip, Symbol symbol, String comment) {
		SymbolOperator operator = ip.getOperator();
		LinkedList<SymbolId> applyTaintStack = ip.getStorage().getApplyTaintStack();
		if (applyTaintStack.contains(symbol.getId())) {
			return;
		}
		applyTaintStack.push(symbol.getId());
		try {
			for (PhpAnyType type : operator.getTypeSet(symbol)) {
				if (type instanceof PhpArray) {
					PhpArray phpArray = (PhpArray) type;
					for (Entry<SymbolId, SymbolId> entry : phpArray.getArray().entrySet()) {
						Symbol keySymbol = operator.getSymbol(entry.getKey());
						Symbol valueSymbol = operator.getSymbol(entry.getValue());
						if (keySymbol != null) {
							applyInitialTaintRecursive(ip, keySymbol, comment);
						}
						if (valueSymbol != null) {
							applyInitialTaintRecursive(ip, valueSymbol, comment);
						}
					}
				} else if (type instanceof PhpObject) {
					PhpObject phpObject = (PhpObject) type;
					for (Entry<SymbolId, SymbolId> entry : phpObject.getFieldMap().entrySet()) {
						Symbol keySymbol = operator.getSymbol(entry.getKey());
						Symbol valueSymbol = operator.getSymbol(entry.getValue());
						if (keySymbol != null) {
							applyInitialTaintRecursive(ip, keySymbol, comment);
						}
						if (valueSymbol != null) {
							applyInitialTaintRecursive(ip, valueSymbol, comment);
						}
					}
				} else if (type instanceof PhpString) {
					operator.addNewTaint(symbol, comment);
				}
			}
		} finally {
			applyTaintStack.pop();
		}
	}

	public static void applyChildTaintRecursive(Interpreter ip, Symbol symbol, Taint taint) {
		SymbolOperator operator = ip.getOperator();
		LinkedList<SymbolId> applyTaintStack = ip.getStorage().getApplyTaintStack();
		if (applyTaintStack.contains(symbol.getId())) {
			return;
		}
		applyTaintStack.push(symbol.getId());
		try {
			for (PhpAnyType type : operator.getTypeSet(symbol)) {
				if (type instanceof PhpArray) {
					PhpArray phpArray = (PhpArray) type;
					for (Entry<SymbolId, SymbolId> entry : phpArray.getArray().entrySet()) {
						Symbol keySymbol = operator.getSymbol(entry.getKey());
						Symbol valueSymbol = operator.getSymbol(entry.getValue());
						if (keySymbol != null) {
							applyChildTaintRecursive(ip, keySymbol, taint);
						}
						if (valueSymbol != null) {
							applyChildTaintRecursive(ip, valueSymbol, taint);
						}
					}
				} else if (type instanceof PhpObject) {
					PhpObject phpObject = (PhpObject) type;
					for (Entry<SymbolId, SymbolId> entry : phpObject.getFieldMap().entrySet()) {
						Symbol keySymbol = operator.getSymbol(entry.getKey());
						Symbol valueSymbol = operator.getSymbol(entry.getValue());
						if (keySymbol != null) {
							applyChildTaintRecursive(ip, keySymbol, taint);
						}
						if (valueSymbol != null) {
							applyChildTaintRecursive(ip, valueSymbol, taint);
						}
					}
				} else if (type instanceof PhpString) {
					operator.addNewTaint(symbol, taint);
				}
			}
		} finally {
			applyTaintStack.pop();
		}
	}

	public static List<Interceptor> getTaintInterceptorList(Interpreter ip) {
		return Lists.newArrayList(
				new TaintPasser(ip),
				new TaintInitializerCallUserXxx(ip),
				new TaintInitializerCurlAPI(ip),
				//				new TaintInitializerDbAPI(ip),
				new TaintInitializerFileAPI(ip),
				//				new TaintInitializerInclude(ip),
				new TaintInitializerParseStr(ip),
				new TaintInitializerReflection(ip),
				new TaintInitializerSuperGlobals(ip),
				new TaintInitializerUnserialize(ip),
				new TaintCheckerLFICommand(ip),
				new TaintCheckerPTCall(ip),
				new TaintCheckerRCECall(ip),
				new TaintCheckerRCEReflection(ip),
				new TaintCheckerSQLICall(ip),
				new TaintCheckerSSRFCall(ip),
				new TaintCheckerXSSCall(ip),
				new TaintCheckerXSSCommand(ip)
				);
	}

	public static Set<Taint> getSQLTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)
					|| taint.getEncodeTagStack().contains(EncodeTag.SQL_ESCAPE))) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	private static boolean isString(Taint taint) {
		for (Command command : taint.getCommandList()) {
			if (command instanceof Cast) {
				CastType type = ((Cast) command).getType();
				if (type == CastType.BOOL
						|| type == CastType.INT
						|| type == CastType.REAL) {
					return false;
				}
			}
		}
		return true;
	}

	public static Set<Taint> getPhpScriptTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getPatternTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getShellCommandTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)
					|| taint.getEncodeTagStack().contains(EncodeTag.SHELL_ENCODE))) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getPathTaint(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)
					//					|| taint.getEncodeTagStack().contains(EncodeTag.BASENAME)
					)) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static class MiniTaint {
		private final List<SourcePosition> positionList;
		private final LinkedList<EncodeTag> encodeTagStack;
	}

	public static Set<Taint> trim(Set<Taint> taintSet) {
		Map<MiniTaint, Taint> map = Maps.newHashMap();
		for (Taint taint : taintSet) {
			SourcePosition start = null;
			SourcePosition end = null;
			List<SourcePosition> positionList = taint.getPositionList();
			if (positionList.size() >= 2) {
				start = positionList.get(0);
				end = positionList.get(positionList.size() - 1);
			} else if (positionList.size() == 1) {
				start = positionList.get(0);
			}
			MiniTaint key = new MiniTaint(Lists.newArrayList(start, end), taint.getEncodeTagStack());
			if (map.get(key) == null) {
				map.put(key, taint);
			} else if (taint.getPositionList().size() < map.get(key).getPositionList().size()) {
				map.put(key, taint);
			}
		}
		return Sets.newHashSet(map.values());
	}

	public static Set<Taint> getFunctionNameTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			boolean hasMix = false;
			for (Command command : taint.getCommandList()) {
				if (command instanceof Mix) {
					hasMix = true;
					break;
				}
			}
			if (hasMix) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getClassNameTaintSet(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Taint createTaint(Interpreter ip, Taint taint) {
		Command command = ip.getContext().getCommand();
		SourcePosition sourcePosition = ip.getSourcePosition();
		if (command instanceof AssignArgument
				|| command instanceof AssignArgumentReference) {
			sourcePosition = ip.getCallStack().peek();
		}

		List<Command> commandList = Lists.newArrayList(taint.getCommandList());
		if (command != null) {
			commandList.add(command);
		}
		List<SourcePosition> positionList = Lists.newArrayList(taint.getPositionList());
		if (sourcePosition != null) {
			if (positionList.size() == 0) {
				positionList.add(sourcePosition);
			} else if (positionList.get(positionList.size() - 1).equals(sourcePosition)) {
				// pass
			} else {
				positionList.add(sourcePosition);
			}
		}
		LinkedList<EncodeTag> encodeStack = Lists.newLinkedList(taint.getEncodeTagStack());
		ArrayList<String> commentList = Lists.newArrayList(taint.getCommentList());
		Taint newTaint = new Taint(
				commandList,
				positionList,
				encodeStack,
				commentList);
		return newTaint;

	}

	public static Taint createTaint(Interpreter ip, Taint taint, String comment) {
		Taint newTaint = createTaint(ip, taint);
		if (comment != null) {
			newTaint.getCommentList().add(comment);
		}
		return newTaint;

	}

	public static Taint createTaint(Interpreter ip, String comment) {
		List<Command> commandList = Lists.newArrayList();
		List<SourcePosition> positionList = Lists.newArrayList();
		LinkedList<EncodeTag> encodeStack = Lists.newLinkedList();
		ArrayList<String> commentList = Lists.newArrayList();
		if (comment != null) {
			commentList.add(comment);
		}
		Taint taint = new Taint(
				commandList,
				positionList,
				encodeStack,
				commentList);
		return taint;
	}

	public static Set<Taint> getContentTaint(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)
					|| taint.getEncodeTagStack().contains(EncodeTag.STRIP_TAGS))) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getUrlTaint(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE))) {
				continue;
			}
			boolean hasPrefix = false;
			for (Command command : taint.getCommandList()) {
				if (command instanceof Mix
						&& ((Mix) command).getLeft().startsWith(Constants.PREFIX_STRING_LITERAL)) {
					// $url = 'http://example.com/' . $input;
					hasPrefix = true;
					break;
				}
			}
			if (hasPrefix) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getHtmlTaint(Set<Taint> taintSet) {
		Set<Taint> result = Sets.newHashSet();
		for (Taint taint : taintSet) {
			if (taint.getCommentList().contains("$_COOKIE")) {
				// クッキーはXSSに利用できない。
				continue;
			}
			if (isString(taint)) {
				// OK
			} else {
				continue;
			}
			if (taint.getEncodeTagStack().size() > 0
					&& (taint.getEncodeTagStack().contains(EncodeTag.URL_ENCODE)
							|| taint.getEncodeTagStack().contains(EncodeTag.HTML_ESCAPE)
							|| taint.getEncodeTagStack().contains(EncodeTag.BASE64_ENCODE)
							// リクエストヘッダはXSSでは利用できない
							|| taint.getEncodeTagStack().contains(EncodeTag.REQUEST_HEADER)
							// jsonデータを返す場合は大抵Content-Typeがtext/htmlでない。そのためXSSにならない。
							|| taint.getEncodeTagStack().contains(EncodeTag.JSON_ENCODE))) {
				continue;
			}
			result.add(taint);
		}
		return result;
	}

	public static Set<Taint> getHostnameTaint(Set<Taint> taintSet) {
		return taintSet;
	}
}
