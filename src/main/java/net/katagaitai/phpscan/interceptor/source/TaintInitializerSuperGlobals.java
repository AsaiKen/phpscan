package net.katagaitai.phpscan.interceptor.source;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.taint.EncodeTag;
import net.katagaitai.phpscan.taint.Taint;
import net.katagaitai.phpscan.util.TaintUtils;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class TaintInitializerSuperGlobals implements CommandInterceptor {
    private final Interpreter ip;
    private boolean applied;

    @Override
    public void intercept(Command command) {
        if (applied) {
            return;
        }
        applied = true;

        List<String> nameList = Lists.newArrayList(TaintUtils.DANGEROUS_SUPERGLOBAL_NAMES);
        for (String name : nameList) {
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(name);
            // GetArrayValue時にトレースできるように自身にも空のテイントを付与する
            symbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                    Lists.newLinkedList(), Lists.newArrayList()));
            log.debug("テイント初期化：" + symbol);
            TaintUtils.applyInitialTaintRecursive(ip, symbol, name);
        }

        // $_FILES
        initFILES();
        // $_SERVER
        initSERVER();

    }

    private void initSERVER() {
        String name = "$_SERVER";
        SymbolOperator operator = ip.getOperator();
        // GetArrayValueをトレースするためのダミーのテイント
        Symbol symbol = ip.getGlobalScope().getOrPhpNull(name);
        symbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                Lists.newLinkedList(), Lists.newArrayList()));
        // 実際のテイント
        PhpArray serverArray = ip.getServerArray();
        List<String> requestHeaderList = Lists.newArrayList(
                "HTTP_ACCEPT", "HTTP_ACCEPT_CHARSET", "HTTP_ACCEPT_ENCODING",
                "HTTP_ACCEPT_LANGUAGE", "HTTP_CONNECTION", "HTTP_USER_AGENT",
                "PHP_AUTH_DIGEST", "PHP_AUTH_PW", "PHP_AUTH_USER");
        for (String key : requestHeaderList) {
            Symbol fileNameSymbol = operator.getArrayValue(serverArray, key);
            fileNameSymbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                    Lists.newLinkedList(Lists.newArrayList(EncodeTag.REQUEST_HEADER)),
                    Lists.newArrayList(name)));
        }
        List<String> uriList = Lists.newArrayList(
                "HTTP_REFERER", "ORIG_PATH_INFO", "PATH_INFO",
                "QUERY_STRING", "REQUEST_URI");
        for (String key : uriList) {
            Symbol fileNameSymbol = operator.getArrayValue(serverArray, key);
            fileNameSymbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                    Lists.newLinkedList(), Lists.newArrayList(name)));
        }

    }

    private void initFILES() {
        String name = "$_FILES";
        SymbolOperator operator = ip.getOperator();
        // GetArrayValueをトレースするためのダミーのテイント
        Symbol symbol = ip.getGlobalScope().getOrPhpNull(name);
        symbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                Lists.newLinkedList(), Lists.newArrayList()));
        // 実際のテイント
        PhpArray fileArray = ip.getFileArray();
        Symbol fileNameSymbol = operator.getArrayValue(fileArray, "name");
        fileNameSymbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                Lists.newLinkedList(Lists.newArrayList(EncodeTag.BASENAME)), Lists.newArrayList(name)));
        Symbol fileTypeSymbol = operator.getArrayValue(fileArray, "type");
        fileTypeSymbol.getTaintSet().add(new Taint(Lists.newArrayList(), Lists.newArrayList(),
                Lists.newLinkedList(), Lists.newArrayList(name)));
    }

}
