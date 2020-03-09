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
import net.katagaitai.phpscan.php.builtin.Curl.curl_init;
import net.katagaitai.phpscan.php.builtin.Standard_7.fsockopen;
import net.katagaitai.phpscan.php.builtin.Standard_7.pfsockopen;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintCheckerSSRFCall implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		PhpCallable callable = decorator.getDecorated();
		String comment = SymbolUtils.getFunctionName(callable);

		if (callable instanceof fsockopen || callable instanceof pfsockopen) {
			Symbol hostnameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getHostnameTaint(hostnameSymbol.getTaintSet());
			if (taintSet.size() == 0) {
				return;
			}
			List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
					ip, VulnerabilityCategory.SSRF, taintSet, comment);
			ScanUtils.addVulnerability(ip, vulnerabilityList);
		} else if (callable instanceof curl_init) {
			Symbol urlSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getUrlTaint(urlSymbol.getTaintSet());
			if (taintSet.size() == 0) {
				return;
			}
			List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
					ip, VulnerabilityCategory.SSRF, taintSet, comment);
			ScanUtils.addVulnerability(ip, vulnerabilityList);
		}
	}

}
