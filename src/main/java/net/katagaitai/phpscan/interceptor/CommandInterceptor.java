package net.katagaitai.phpscan.interceptor;

import net.katagaitai.phpscan.command.Command;

public interface CommandInterceptor extends Interceptor {
    void intercept(Command command);
}
