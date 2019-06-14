package net.katagaitai.phpscan.interceptor.sink;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Simplexml.simplexml_load_file;
import net.katagaitai.phpscan.php.builtin.Simplexml.simplexml_load_string;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TaintCheckerXXECall implements CallInterceptor {
    private final Interpreter ip;

    // [脆弱性のあるAPIs]
    //
    // - DOM
    //	 	- load(path)
    //	 	- loadXML(string)
    // - SimpleXML
    //	 	- new SimpleXMLElement(string)
    //	 	- new SimpleXMLElement(path, null, true)
    // - XMLReader
    //	 	- xml(string)
    //	 		- with SUBST_ENTITIES
    // - simplexml_load_string(string)
    // - simplexml_load_file(path)
    //
    // [対策]
    //
    // - libxml_disable_entity_loader(true)

    @Override
    public void intercept(CallDecorator decorator) {
        PhpCallable callable = decorator.getDecorated();
        String comment = SymbolUtils.getFunctionName(callable);

        if (callable instanceof simplexml_load_string) {
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 0);
            Set<Taint> contentTaintSet = TaintUtils.getContentTaint(stringSymbol.getTaintSet());
            if (contentTaintSet.size() == 0) {
                return;
            }
            List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                    ip, VulnerabilityCategory.XXE, contentTaintSet, comment);
            ScanUtils.addVulnerability(ip, vulnerabilityList);
        } else if (callable instanceof simplexml_load_file) {
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 0);
            Set<Taint> pathTaintSet = TaintUtils.getPathTaint(stringSymbol.getTaintSet());
            if (pathTaintSet.size() == 0) {
                return;
            }
            List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                    ip, VulnerabilityCategory.XXE, pathTaintSet, comment);
            ScanUtils.addVulnerability(ip, vulnerabilityList);
        }

    }

}
