package net.katagaitai.phpscan.interceptor;

import net.katagaitai.phpscan.interpreter.CallDecorator;

public interface CallInterceptor extends Interceptor {
    void intercept(CallDecorator decorator);
}
