package net.katagaitai.phpscan.interceptor.sink;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Builtin.print;
import net.katagaitai.phpscan.php.builtin.Standard_2.printf;
import net.katagaitai.phpscan.php.builtin.Standard_4.var_dump;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TaintCheckerXSSCall implements CallInterceptor {
    private final Interpreter ip;

    @Override
    public void intercept(CallDecorator decorator) {
        PhpCallable callable = decorator.getDecorated();
        String comment = SymbolUtils.getFunctionName(callable);

        if (callable instanceof print) {
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 0);
            Set<Taint> htmlTaintSet = TaintUtils.getHtmlTaint(stringSymbol.getTaintSet());
            if (htmlTaintSet.size() == 0) {
                return;
            }
            List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                    ip, VulnerabilityCategory.XSS, htmlTaintSet, comment);
            ScanUtils.addVulnerability(ip, vulnerabilityList);
        } else if (callable instanceof printf) {
            for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol stringSymbol = SymbolUtils.getArgument(ip, i);
                Set<Taint> htmlTaintSet = TaintUtils.getHtmlTaint(stringSymbol.getTaintSet());
                if (htmlTaintSet.size() == 0) {
                    continue;
                }
                List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                        ip, VulnerabilityCategory.XSS, htmlTaintSet, comment);
                ScanUtils.addVulnerability(ip, vulnerabilityList);
            }
        } else if (callable instanceof var_dump) {
            for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol stringSymbol = SymbolUtils.getArgument(ip, i);
                Set<Taint> htmlTaintSet = TaintUtils.getHtmlTaint(stringSymbol.getTaintSet());
                SymbolOperator operator = ip.getOperator();
                htmlTaintSet.addAll(TaintUtils.getHtmlTaint(
                        operator.getMergedArrayKeySymbol(stringSymbol).getTaintSet()));
                htmlTaintSet.addAll(TaintUtils.getHtmlTaint(
                        operator.getMergedArrayValueSymbol(stringSymbol).getTaintSet()));
                htmlTaintSet.addAll(TaintUtils.getHtmlTaint(
                        operator.getMergedFieldKeySymbol(stringSymbol).getTaintSet()));
                htmlTaintSet.addAll(TaintUtils.getHtmlTaint(
                        operator.getMergedFieldValueSymbol(stringSymbol).getTaintSet()));
                if (htmlTaintSet.size() == 0) {
                    continue;
                }
                List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                        ip, VulnerabilityCategory.XSS, htmlTaintSet, comment);
                ScanUtils.addVulnerability(ip, vulnerabilityList);
            }
        }
    }
}
