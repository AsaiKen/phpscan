package net.katagaitai.phpscan.interceptor.sink;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.command.Include;
import net.katagaitai.phpscan.command.IncludeOnce;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class TaintCheckerLFICommand implements CommandInterceptor {
    private final Interpreter ip;

    @Override
    public void intercept(Command command) {
        SymbolOperator operator = ip.getOperator();
        Symbol pathSymbol;
        if (command instanceof Include) {
            pathSymbol = ip.getSymbolOrCreate(((Include) command).getPath());
        } else if (command instanceof IncludeOnce) {
            pathSymbol = ip.getSymbolOrCreate(((IncludeOnce) command).getPath());
        } else {
            return;
        }

        // パスを操作できる場合
        Set<Taint> pathTaintSet = TaintUtils.getPathTaint(pathSymbol.getTaintSet());
        List<Vulnerability> vulnerabilityList = ScanUtils.getVulnerabilityList(
                ip, VulnerabilityCategory.LFI, pathTaintSet);
        ScanUtils.addVulnerability(ip, vulnerabilityList);

        // ファイルの中身を操作できる場合
        Map<String, Symbol> filenameContentMap = ip.getStorage().getFilenameContentMap();
        for (String pathString : operator.getJavaStringList(pathSymbol)) {
            Symbol contentSymbol = filenameContentMap.get(pathString);
            if (contentSymbol != null) {
                Set<Taint> contentTaintSet = TaintUtils.getContentTaint(contentSymbol.getTaintSet());
                List<Vulnerability> vulnerabilityList2 = ScanUtils.getVulnerabilityList(
                        ip, VulnerabilityCategory.LFI, contentTaintSet);
                ScanUtils.addVulnerability(ip, vulnerabilityList2);
            }
        }
    }
}
