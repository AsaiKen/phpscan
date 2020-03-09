package net.katagaitai.phpscan.interceptor.source;

import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Session.session_decode;
import net.katagaitai.phpscan.php.builtin.Standard_4.unserialize;
import net.katagaitai.phpscan.php.builtin.Yaml.yaml_parse;
import net.katagaitai.phpscan.php.builtin.Yaml.yaml_parse_file;
import net.katagaitai.phpscan.php.builtin.Yaml.yaml_parse_url;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@RequiredArgsConstructor
public class TaintInitializerUnserialize implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		if (decorator.getDecorated() instanceof unserialize
				|| decorator.getDecorated() instanceof session_decode
				|| decorator.getDecorated() instanceof yaml_parse) {
			Symbol symbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = symbol.getTaintSet();
			if (taintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList =
						ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.OI, taintSet,
								SymbolUtils.getFunctionName(decorator.getDecorated()));
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else {
				return;
			}
			//			Symbol stringSymbol = operator.string();
			//			operator.addNewTaintSet(stringSymbol, taintSet);
			//			operator.merge(decorator.getResult(), stringSymbol);
			//			List<PhpNewable> newableList = SymbolUtils.getUserDefinedPhpClassList(ip);
			//			for (PhpNewable newable : newableList) {
			//				PhpObjectAny phpObjectAny = operator.createPhpObjectAny(newable, taintSet);
			//				newable.__wakeup(ip, phpObjectAny);
			//				Symbol objSymbol = operator.createSymbol(phpObjectAny);
			//				operator.merge(decorator.getResult(), objSymbol);
			//			}
			//			operator.merge(decorator.getResult(),
			//					operator.createSymbol(operator.createPhpArrayAny(taintSet)));
		} else if (decorator.getDecorated() instanceof yaml_parse_file) {
			Symbol symbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getPathTaint(symbol.getTaintSet());
			if (taintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList =
						ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.OI, taintSet,
								SymbolUtils.getFunctionName(decorator.getDecorated()));
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else {
				return;
			}
		} else if (decorator.getDecorated() instanceof yaml_parse_url) {
			Symbol symbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getUrlTaint(symbol.getTaintSet());
			if (taintSet.size() > 0) {
				List<Vulnerability> vulnerabilityList =
						ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.OI, taintSet,
								SymbolUtils.getFunctionName(decorator.getDecorated()));
				ScanUtils.addVulnerability(ip, vulnerabilityList);
			} else {
				return;
			}
		}
	}
}
