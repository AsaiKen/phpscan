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
import net.katagaitai.phpscan.php.builtin.Standard_2.link;
import net.katagaitai.phpscan.php.builtin.Standard_2.symlink;
import net.katagaitai.phpscan.php.builtin.Standard_2.unlink;
import net.katagaitai.phpscan.php.builtin.Standard_3.error_log;
import net.katagaitai.phpscan.php.builtin.Standard_4.move_uploaded_file;
import net.katagaitai.phpscan.php.builtin.Standard_5.copy;
import net.katagaitai.phpscan.php.builtin.Standard_5.file_get_contents;
import net.katagaitai.phpscan.php.builtin.Standard_5.file_put_contents;
import net.katagaitai.phpscan.php.builtin.Standard_5.fopen;
import net.katagaitai.phpscan.php.builtin.Standard_5.rename;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintCheckerPTCall implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecorated();
		String comment = SymbolUtils.getFunctionName(callable);

		if (callable instanceof unlink || callable instanceof file_get_contents
				|| callable instanceof fopen) {
			Symbol pathSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> pathTaintSet = TaintUtils.getPathTaint(pathSymbol.getTaintSet());
			if (pathTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PM, pathTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			}
		} else if (callable instanceof copy
				|| callable instanceof symlink
				|| callable instanceof link
				|| callable instanceof rename) {
			Symbol srcSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> srcTaintSet = TaintUtils.getPathTaint(srcSymbol.getTaintSet());
			Symbol destSymbol = SymbolUtils.getArgument(ip, 1);
			Set<Taint> destTaintSet = TaintUtils.getPathTaint(destSymbol.getTaintSet());
			if (srcTaintSet.size() > 0 && destTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PFM, srcTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else if (srcTaintSet.size() > 0 || destTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PM, destTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			}
		} else if (callable instanceof move_uploaded_file) {
			Symbol pathSymbol = SymbolUtils.getArgument(ip, 1);
			Set<Taint> pathTaintSet = TaintUtils.getPathTaint(pathSymbol.getTaintSet());
			if (pathTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PFM, pathTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			}
		} else if (callable instanceof file_put_contents) {
			Symbol pathSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> pathTaintSet = TaintUtils.getPathTaint(pathSymbol.getTaintSet());
			Symbol contentSymbol = SymbolUtils.getArgument(ip, 1);
			Set<Taint> contentTaintSet = TaintUtils.getContentTaint(contentSymbol.getTaintSet());
			if ((pathTaintSet.size() > 0 || endsWith(pathSymbol, ".php")) && contentTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PFM, contentTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else if (pathTaintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
						ip, VulnerabilityCategory.PM, pathTaintSet, comment);
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			}
		} else if (callable instanceof error_log) {
			Symbol messagetypeSymbol = SymbolUtils.getArgument(ip, 1);
			boolean writeToFile = false;
			for (long long_ : operator.getJavaLongList(messagetypeSymbol)) {
				if (long_ == 3) {
					writeToFile = true;
					break;
				}
			}
			if (writeToFile) {
				Symbol pathSymbol = SymbolUtils.getArgument(ip, 2);
				Set<Taint> pathTaintSet = TaintUtils.getPathTaint(pathSymbol.getTaintSet());
				Symbol contentSymbol = SymbolUtils.getArgument(ip, 0);
				Set<Taint> contentTaintSet = TaintUtils.getContentTaint(contentSymbol.getTaintSet());
				if ((pathTaintSet.size() > 0 || endsWith(pathSymbol, ".php"))
						&& contentTaintSet.size() > 0) {
					List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
							ip, VulnerabilityCategory.PFM, contentTaintSet, comment);
					ScanUtils.addVulnerability(ip, vulnerabilityList);
				} else if (pathTaintSet.size() > 0) {
					List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
							ip, VulnerabilityCategory.PM, pathTaintSet, comment);
					ScanUtils.addVulnerability(ip, vulnerabilityList);
				}
			}
		}
	}

	private boolean endsWith(Symbol symbol, String end) {
		for (String string : ip.getOperator().getJavaStringList(symbol)) {
			if (string.endsWith(end)) {
				return true;
			}
		}
		return false;
	}
}
