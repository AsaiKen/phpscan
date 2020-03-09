package net.katagaitai.phpscan.interceptor.sink;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.Echo;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintCheckerXSSCommand implements CommandInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(Command command) {
		Symbol stringSymbol;
		if (command instanceof Echo) {
			stringSymbol = ip.getSymbolOrCreate(((Echo) command).getArgument());
		} else {
			return;
		}
		Set<Taint> htmlTaintSet = TaintUtils.getHtmlTaint(stringSymbol.getTaintSet());
		if (htmlTaintSet.size() == 0) {
			return;
		}
		List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
				ip, VulnerabilityCategory.XSS, htmlTaintSet, "echo");
		ScanUtils.addVulnerability(ip, vulnerabilityList);
	}
}
