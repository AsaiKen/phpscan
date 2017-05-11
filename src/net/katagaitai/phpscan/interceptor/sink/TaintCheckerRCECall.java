package net.katagaitai.phpscan.interceptor.sink;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Core.create_function;
import net.katagaitai.phpscan.php.builtin.Pcre.preg_replace;
import net.katagaitai.phpscan.php.builtin.Standard_2.exec;
import net.katagaitai.phpscan.php.builtin.Standard_2.passthru;
import net.katagaitai.phpscan.php.builtin.Standard_2.proc_open;
import net.katagaitai.phpscan.php.builtin.Standard_2.shell_exec;
import net.katagaitai.phpscan.php.builtin.Standard_2.system;
import net.katagaitai.phpscan.php.builtin.Standard_5.popen;
import net.katagaitai.phpscan.php.builtin._Types.eval;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintCheckerRCECall implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecrated();
		int argIndex;
		String comment = SymbolUtils.getFunctionName(callable);
		boolean evalPhp = false;
		boolean execShell = false;
		if (callable instanceof eval) {
			evalPhp = true;
			argIndex = 0;
		} else if (callable instanceof shell_exec
				|| callable instanceof exec || callable instanceof passthru
				|| callable instanceof proc_open || callable instanceof popen
				|| callable instanceof system) {
			execShell = true;
			argIndex = 0;
		} else if (callable instanceof create_function) {
			evalPhp = true;
			argIndex = 1;
		} else if (callable instanceof preg_replace) {
			Symbol patternSymbol = SymbolUtils.getArgument(ip, 0);
			boolean hasE = false;
			for (String patternString : operator.getJavaStringList(patternSymbol)) {
				if (patternString.matches(".*\\w*e\\w*")) {
					hasE = true;
					break;
				}
			}
			if (hasE) {
				evalPhp = true;
				argIndex = 2;
			} else {
				// e無しは無視
				return;
			}
		} else {
			return;
		}
		Symbol symbol = SymbolUtils.getArgument(ip, argIndex);
		if (evalPhp) {
			Set<Taint> taintSet = TaintUtils.getPhpScriptTaintSet(symbol.getTaintSet());
			List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
					ip, VulnerabilityCategory.RCE, taintSet, comment);
			ScanUtils.addVulnerability(ip, vulnerabilityList);
		} else if (execShell) {
			Set<Taint> taintSet = TaintUtils.getShellCommandTaintSet(symbol.getTaintSet());
			List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
					ip, VulnerabilityCategory.RCE, taintSet, comment);
			ScanUtils.addVulnerability(ip, vulnerabilityList);
		}
	}

}
