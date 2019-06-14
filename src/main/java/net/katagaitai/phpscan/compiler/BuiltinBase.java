package net.katagaitai.phpscan.compiler;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.*;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class BuiltinBase {
    // グローバルスコープ
    // 絶対クラス名：クラス
    @Getter
    protected Map<String, PhpNewable> classMap = Maps.newHashMap();
    // 絶対関数名名：関数
    @Getter
    protected Map<String, PhpCallable> functionMap = Maps.newHashMap();
    // 名前：定数
    @Getter
    protected Map<String, Symbol> constantMap = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public BuiltinBase(Interpreter ip) {
        try {
            for (Class<?> clazz : getClass().getDeclaredClasses()) {
                if (ClassUtils.getAllInterfaces(clazz).contains(PhpNewable.class)) {
                    // クラス
                    Class<?>[] types = {Interpreter.class};
                    Constructor<PhpClassBase> constructor =
                            (Constructor<PhpClassBase>) clazz.getConstructor(types);
                    Object[] args = {ip};
                    PhpClassBase instance = constructor.newInstance(args);
                    putClass(instance);
                } else if (ClassUtils.getAllInterfaces(clazz).contains(PhpCallable.class)) {
                    // 関数
                    PhpCallable instance = (PhpCallable) clazz.newInstance();
                    putFunction(instance);
                }
            }
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected void putClass(PhpNewable newable) {
        classMap.put(newable.getAbsoulteClassName().toLowerCase(), newable);
    }

    protected void putFunction(PhpCallable callable) {
        functionMap.put(getAbsoluteName(callable).toLowerCase(), callable);
    }

    protected void putConstant(String absolute, Symbol symbol) {
        constantMap.put(absolute, symbol);
    }

    protected String getAbsoluteName(Object object) {
        String name = object.getClass().getName();
        return SymbolUtils.getAbsoluteNameFromJavaClassName(name);
    }

    public static Symbol serialize(Interpreter ip, Symbol symbol) {
        if (symbol == null) {
            return null;
        }
        SymbolOperator operator = ip.getOperator();
        List<SymbolId> serializeList = ip.getStorage().getSerializeSymbolList();
        if (serializeList.contains(symbol.getId())) {
            return null;
        }
        serializeList.add(symbol.getId());
        Symbol resultSymbol = operator.createSymbol();
        for (PhpAnyType type : operator.getTypeSet(symbol)) {
            if (type instanceof PhpArray) {
                PhpArray phpArray = (PhpArray) type;
                if (phpArray instanceof PhpArrayAny) {
                    Symbol stringSymbol = operator.string();
                    operator.addNewTaintSet(stringSymbol, ((PhpArrayAny) phpArray).getTaintSet());
                    operator.merge(resultSymbol, stringSymbol);
                    continue;
                }
                List<PhpArray> serializeArrayList = ip.getStorage().getSerializeArrayList();
                if (serializeArrayList.contains(phpArray)) {
                    continue;
                }
                serializeArrayList.add(phpArray);
                operator.merge(resultSymbol, serialize(ip, operator.getMergedArrayKeySymbol(phpArray)));
                operator.merge(resultSymbol, serialize(ip, operator.getMergedArrayValueSymbol(phpArray)));
            } else if (type instanceof PhpObject) {
                PhpObject phpObject = (PhpObject) type;
                if (phpObject instanceof PhpObjectAny) {
                    Symbol stringSymbol = operator.string();
                    operator.addNewTaintSet(stringSymbol, ((PhpObjectAny) phpObject).getTaintSet());
                    operator.merge(resultSymbol, stringSymbol);
                    continue;
                }
                List<PhpObject> serializeObjectList = ip.getStorage().getSerializeObjectList();
                if (serializeObjectList.contains(phpObject)) {
                    continue;
                }
                serializeObjectList.add(phpObject);
                operator.merge(resultSymbol, serialize(ip, operator.getMergedFieldKeySymbol(phpObject)));
                operator.merge(resultSymbol, serialize(ip, operator.getMergedFieldValueSymbol(phpObject)));
            } else if (type instanceof PhpString) {
                operator.addTypeSet(resultSymbol, Sets.newHashSet(type));
                operator.addNewTaintSet(resultSymbol, symbol.getTaintSet());
            }
        }
        return resultSymbol;
    }

}
