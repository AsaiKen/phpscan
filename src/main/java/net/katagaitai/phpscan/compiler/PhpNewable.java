package net.katagaitai.phpscan.compiler;

import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;

import java.util.List;
import java.util.Map;

public interface PhpNewable {
    void initialize(Interpreter interpreter);

    void __construct(Interpreter interpreter);

    PhpCallable getStaticMethod(String name);

    PhpCallable getMethod(String name);

    Symbol getStaticField(Interpreter ip, Symbol nameSymbol);

    //	PhpCallable getAnyMethod(String name);

    String getAbsoulteClassName();

    String getAbsoluteParentClassName();

    void __destruct(Interpreter interpreter, PhpObject phpObject);

    Symbol __call(Interpreter interpreter, PhpObject phpObject, Symbol methodSymbol, List<Symbol> argumentSymbolList);

    Symbol __callStatic(Interpreter interpreter, Symbol methodSymbol, List<Symbol> argumentSymbolList);

    Symbol __get(Interpreter interpreter, PhpObject phpObject, Symbol keySymbol);

    void __set(Interpreter interpreter, PhpObject phpObject, Symbol keySymbol, Symbol valueSymbol);

    Symbol __isset(Interpreter interpreter, PhpObject phpObject, Symbol keySymbol);

    void __unset(Interpreter interpreter, PhpObject phpObject, Symbol keySymbol);

    Symbol __sleep(Interpreter interpreter, PhpObject phpObject);

    void __wakeup(Interpreter interpreter, PhpObject phpObject);

    Symbol __toString(Interpreter interpreter, PhpObject phpObject);

    Symbol __invoke(Interpreter interpreter, PhpObject phpObject, List<Symbol> argumentSymbolList);

    Symbol __debugInfo(Interpreter interpreter, PhpObject phpObject);

    Symbol __set_state(Interpreter interpreter, PhpObject phpObject);

    Symbol __clone(Interpreter interpreter, PhpObject phpObject);

    Map<String, PhpCallable> getMethodMap();

    Map<String, PhpCallable> getStaticMethodMap();
}
