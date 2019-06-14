package net.katagaitai.phpscan.interpreter;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpFunction;
import net.katagaitai.phpscan.interceptor.BeforeCallInterceptor;
import net.katagaitai.phpscan.interceptor.CallInterceptor;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.LinkedList;

@Log4j2
@RequiredArgsConstructor
public class CallDecorator implements PhpCallable {
    @Getter
    private final PhpCallable decorated;
    @Getter
    @Setter
    private Symbol result;

    @Override
    public Symbol call(Interpreter ip) {
        SymbolOperator operator = ip.getOperator();

        for (Interceptor interceptor : ip.getInterceptorList()) {
            if (interceptor instanceof BeforeCallInterceptor) {
                ((BeforeCallInterceptor) interceptor).intercept(this);
            }
        }
        if (result != null) {
            return result;
        }

        // callableStack
        LinkedList<PhpCallable> callableStack = ip.getStorage().getCallableStack();
        if (callableStack.contains(decorated)) {
            String string = SymbolUtils.getFunctionName(decorated);
            log.info("再帰：" + string + " [" + ip.getSourcePosition() + "]");
            return operator.createNull();
        }

        // callCountMap
        if (SymbolUtils.hasTaint(ip, ip.getContext().getArgumentSymbolList())) {
            // 引数にテイントがあれば必ず実行する
        } else if (decorated instanceof PhpFunction) {
            String className = "\\";
            if (ip.getContext().getThisObject() != null
                    && ip.getContext().getThisObject().getPhpClass() != null) {
                className = ip.getContext().getThisObject().getPhpClass().getAbsoulteClassName();
            }
            if (ip.getCallCountMap().get(className) == null) {
                ip.getCallCountMap().put(className, Maps.newHashMap());
            }
            if (ip.getCallCountMap().get(className).get(decorated) == null) {
                ip.getCallCountMap().get(className).put(decorated, 0);
            }
            int count = ip.getCallCountMap().get(className).get(decorated);
            if (count >= Constants.MAX_CALL_COUNT) {
                // ユーザ定義関数かつN回以上呼んでいる場合は高速化のために実行をスキップする
                String string = SymbolUtils.getFunctionName(decorated);
                log.info("省略：" + string + " [" + ip.getSourcePosition() + "]");
                return operator.createNull();
            }
        }

        SourcePosition savedSourcePosition = ip.getSourcePosition();
        callableStack.push(decorated);
        // 戻り値
        Symbol returnValueSymbol = operator.createSymbol();
        operator.incrementReference(returnValueSymbol);
        ip.getContext().setReturnValueSymbol(returnValueSymbol);
        try {
            result = decorated.call(ip);
            if (operator.getTypeSet(result).size() == 0) {
                result = operator.createNull();
            }
            return result;
        } finally {
            ip.setSourcePosition(savedSourcePosition);
            callableStack.pop();
            if (decorated instanceof PhpFunction) {
                String className = "\\";
                if (ip.getContext().getThisObject() != null
                        && ip.getContext().getThisObject().getPhpClass() != null) {
                    className = ip.getContext().getThisObject().getPhpClass().getAbsoulteClassName();
                }
                if (ip.getCallCountMap().get(className) == null) {
                    ip.getCallCountMap().put(className, Maps.newHashMap());
                }
                if (ip.getCallCountMap().get(className).get(decorated) == null) {
                    ip.getCallCountMap().get(className).put(decorated, 0);
                }
                int count = ip.getCallCountMap().get(className).get(decorated);
                ip.getCallCountMap().get(className).put(decorated, count + 1);
            }
            for (Interceptor interceptor : ip.getInterceptorList()) {
                if (interceptor instanceof CallInterceptor) {
                    ((CallInterceptor) interceptor).intercept(this);
                }
            }
        }
    }

}
