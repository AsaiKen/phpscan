package net.katagaitai.phpscan.interceptor.source;

import lombok.RequiredArgsConstructor;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interceptor.CommandInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;

@RequiredArgsConstructor
public class TaintInitializerInclude implements CommandInterceptor {
    private final Interpreter ip;

    @Override
    public void intercept(Command command) {
        // nop
    }

}
