package net.katagaitai.phpscan.interceptor.source;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Standard_2.fprintf;
import net.katagaitai.phpscan.php.builtin.Standard_5;
import net.katagaitai.phpscan.php.builtin.Standard_5.file_get_contents;
import net.katagaitai.phpscan.php.builtin.Standard_5.fopen;
import net.katagaitai.phpscan.php.builtin.Standard_5.fputs;
import net.katagaitai.phpscan.php.builtin.Standard_5.fwrite;
import net.katagaitai.phpscan.php.builtin.Standard_6.fputcsv;
import net.katagaitai.phpscan.php.builtin.Standard_7.fsockopen;
import net.katagaitai.phpscan.php.builtin.Standard_7.pfsockopen;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

@Log4j2
@RequiredArgsConstructor
public class TaintInitializerFileAPI implements CallInterceptor {
	private final Interpreter ip;

	@Override
	public void intercept(CallDecorator decorator) {
		SymbolOperator operator = ip.getOperator();
		PhpCallable callable = decorator.getDecorated();
		if (callable instanceof fopen) {
			Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getPathTaint(filenameSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				// OK
			} else {
				return;
			}
			Symbol resultSymbol = decorator.getResult();
			for (PhpResource phpResource : operator.extractPhpResource(resultSymbol)) {
				Symbol contentSymbol =
						operator.getResourceValue(phpResource, Standard_5.OPEN_CONTENT);
				operator.addNewTaintSet(contentSymbol, taintSet);
				log.debug("テイント：" + contentSymbol);
			}
		} else if (callable instanceof fsockopen || callable instanceof pfsockopen) {
			Symbol hostnameSymbol = SymbolUtils.getArgument(ip, 0);
			Set<Taint> taintSet = TaintUtils.getHostnameTaint(hostnameSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				// OK
			} else {
				return;
			}
			Symbol resultSymbol = decorator.getResult();
			for (PhpResource phpResource : operator.extractPhpResource(resultSymbol)) {
				Symbol contentSymbol =
						operator.getResourceValue(phpResource, Standard_5.OPEN_CONTENT);
				operator.addNewTaintSet(contentSymbol, taintSet);
				log.debug("テイント：" + contentSymbol);
			}
		} else if (callable instanceof file_get_contents) {
			Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
			for (String filenameString : operator.getJavaStringList(filenameSymbol)) {
				if (filenameString.equals("php://input")) {
					operator.addNewTaint(decorator.getResult(), "php://input");
					log.debug("テイント：" + decorator.getResult());
				}
			}
			Set<Taint> taintSet = TaintUtils.getPathTaint(filenameSymbol.getTaintSet());
			if (taintSet.size() > 0) {
				// ファイルの中身にもテイントを追加
				Symbol resultSymbol = decorator.getResult();
				operator.addNewTaintSet(resultSymbol, taintSet);
				log.debug("テイント：" + resultSymbol);
			} else {
				return;
			}
		} else if (callable instanceof fwrite || callable instanceof fputs) {
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
			Set<Taint> contentTaintSet = TaintUtils.getContentTaint(stringSymbol.getTaintSet());
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				Symbol contentSymbol = operator.getResourceValue(phpResource, Standard_5.OPEN_CONTENT);
				for (Entry<String, Symbol> entry : ip.getStorage().getFilenameContentMap().entrySet()) {
					if (entry.getValue().equals(contentSymbol)) {
						String filenameString = entry.getKey();
						if (filenameString.endsWith(".php")) {
							List<Vulnerability> vulnerabilityList =
									ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.PFM, contentTaintSet,
											SymbolUtils.getFunctionName(callable));
							ScanUtils.addVulnerability(ip, vulnerabilityList);
							operator.addNewTaintSet(contentSymbol, contentTaintSet);
						}
					}
				}
			}
		} else if (callable instanceof fprintf) {
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol tmpSymbol = operator.createSymbol();
			for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
				operator.merge(tmpSymbol, SymbolUtils.getArgument(ip, i));
			}
			Set<Taint> contentTaintSet = TaintUtils.getContentTaint(tmpSymbol.getTaintSet());
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				Symbol contentSymbol = operator.getResourceValue(phpResource, Standard_5.OPEN_CONTENT);
				for (Entry<String, Symbol> entry : ip.getStorage().getFilenameContentMap().entrySet()) {
					if (entry.getValue().equals(contentSymbol)) {
						String filenameString = entry.getKey();
						if (filenameString.endsWith(".php")) {
							List<Vulnerability> vulnerabilityList =
									ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.PFM, contentTaintSet,
											SymbolUtils.getFunctionName(callable));
							ScanUtils.addVulnerability(ip, vulnerabilityList);
							operator.addNewTaintSet(contentSymbol, contentTaintSet);
						}
					}
				}
			}
		} else if (callable instanceof fputcsv) {
			Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
			Symbol fieldsSymbol = SymbolUtils.getArgument(ip, 1);
			Symbol tmpSymbol = operator.createSymbol();
			operator.merge(tmpSymbol, operator.getMergedArrayKeySymbol(fieldsSymbol));
			operator.merge(tmpSymbol, operator.getMergedArrayValueSymbol(fieldsSymbol));
			Set<Taint> contentTaintSet = TaintUtils.getContentTaint(tmpSymbol.getTaintSet());
			for (PhpResource phpResource : operator.extractPhpResource(handleSymbol)) {
				Symbol contentSymbol = operator.getResourceValue(phpResource, Standard_5.OPEN_CONTENT);
				for (Entry<String, Symbol> entry : ip.getStorage().getFilenameContentMap().entrySet()) {
					if (entry.getValue().equals(contentSymbol)) {
						String filenameString = entry.getKey();
						if (filenameString.endsWith(".php")) {
							List<Vulnerability> vulnerabilityList =
									ScanUtils.getVulnerabilityList(ip, VulnerabilityCategory.PFM, contentTaintSet,
											SymbolUtils.getFunctionName(callable));
							ScanUtils.addVulnerability(ip, vulnerabilityList);
							operator.addNewTaintSet(contentSymbol, contentTaintSet);
						}
					}
				}
			}
		}
	}

}
