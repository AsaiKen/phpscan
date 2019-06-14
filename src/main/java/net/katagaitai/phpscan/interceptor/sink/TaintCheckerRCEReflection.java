package net.katagaitai.phpscan.interceptor.sink;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interpreter.CallDecorator;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Reflection.ReflectionFunction.invoke;
import net.katagaitai.phpscan.php.builtin.Reflection.ReflectionFunction.invokeArgs;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.SymbolUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class TaintCheckerRCEReflection implements CallInterceptor {
    private final Interpreter ip;

    @Override
    public void intercept(CallDecorator decorator) {
        SymbolOperator operator = ip.getOperator();
        PhpCallable callable = decorator.getDecorated();
        if (callable instanceof invoke) {
            Symbol thisSymbol = ip.getContext().getThisSymbol();
            Symbol nameSymbol = operator.getFieldValue(thisSymbol, "name");
            Symbol arg0Symbol = SymbolUtils.getArgument(ip, 0);
            if (TaintUtils.getFunctionNameTaintSet(nameSymbol.getTaintSet()).size() > 0
                    && TaintUtils.getShellCommandTaintSet(arg0Symbol.getTaintSet()).size() > 0) {
                String comment = SymbolUtils.getFunctionName(callable);
                Set<Taint> taintSet = TaintUtils.getShellCommandTaintSet(nameSymbol.getTaintSet());
                List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                        ip, VulnerabilityCategory.RCE, taintSet, comment);
                ScanUtils.addVulnerability(ip, vulnerabilityList);
            }
        } else if (callable instanceof invokeArgs) {
            Symbol thisSymbol = ip.getContext().getThisSymbol();
            Symbol nameSymbol = operator.getFieldValue(thisSymbol, "name");
            Symbol arraySymbol = SymbolUtils.getArgument(ip, 0);
            Symbol arg0Symbol = operator.getArrayValue(arraySymbol, operator.createSymbol(0));
            if (TaintUtils.getFunctionNameTaintSet(nameSymbol.getTaintSet()).size() > 0
                    && TaintUtils.getShellCommandTaintSet(arg0Symbol.getTaintSet()).size() > 0) {
                String comment = SymbolUtils.getFunctionName(callable);
                Set<Taint> taintSet = TaintUtils.getShellCommandTaintSet(nameSymbol.getTaintSet());
                List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                        ip, VulnerabilityCategory.RCE, taintSet, comment);
                ScanUtils.addVulnerability(ip, vulnerabilityList);
            }
        }
    }

}
