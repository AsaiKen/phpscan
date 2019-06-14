package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpNewable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpBoolean;
import net.katagaitai.phpscan.php.types.PhpObject;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.List;

public class Standard_4 extends BuiltinBase {
    public Standard_4(Interpreter ip) {
        super(ip);
    }

    // function error_get_last () {}
    public static class error_get_last implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function call_user_func ($function, $parameter = null, $_ = null) {}
    public static class call_user_func implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
            // 引数の特定
            List<Symbol> list = Lists.newArrayList();
            if (SymbolUtils.getArgumentSize(ip) > 1) {
                list = ip.getContext().getArgumentSymbolList()
                        .subList(1, SymbolUtils.getArgumentSize(ip));
            }
            operator.merge(resultSymbol, SymbolUtils.callCallable(ip, functionSymbol, list));
            return resultSymbol;
        }
    }

    // function call_user_func_array ($function, array $param_arr) {}
    public static class call_user_func_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
            // 引数の特定
            Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 1);
            for (PhpArray phpArray : operator.extractPhpArray(paramarrSymbol)) {
                List<Symbol> list = Lists.newArrayList();
                for (int i = 0; i < 100; i++) { // 無限ループ対策
                    Symbol argSymbol = operator.getArrayValue(phpArray, i);
                    if (operator.isNull(argSymbol)) {
                        break;
                    } else {
                        list.add(argSymbol);
                    }
                }
                operator.merge(resultSymbol, SymbolUtils.callCallable(ip, functionSymbol, list));
            }
            return resultSymbol;
        }
    }

    // function call_user_method ($method_name, &$obj, $parameter = null, $_ = null) {}
    public static class call_user_method implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol methodnameSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol objectSymbol = SymbolUtils.getArgumentRef(ip, 1);

            // 引数の特定
            List<Symbol> list = Lists.newArrayList();
            if (SymbolUtils.getArgumentSize(ip) > 2) {
                list = ip.getContext().getArgumentSymbolList()
                        .subList(2, SymbolUtils.getArgumentSize(ip));
            }

            for (PhpObject phpObject : operator.extractPhpObject(objectSymbol)) {
                PhpNewable newable = phpObject.getPhpClass();
                List<PhpCallable> callableList = Lists.newArrayList();
                for (String method : operator.getJavaStringList(methodnameSymbol)) {
                    PhpCallable callable = newable.getMethod(method);
                    if (callable != null) {
                        callableList.add(callable);
                    }
                    PhpCallable callable2 = newable.getStaticMethod(method);
                    if (callable2 != null) {
                        callableList.add(callable2);
                    }
                }
                for (PhpCallable callable : callableList) {
                    // 静的メソッドの場合もこれでOK
                    operator.merge(resultSymbol, SymbolUtils.callMethod(ip, phpObject, callable, list));
                }
            }

            return resultSymbol;
        }
    }

    // function call_user_method_array ($method_name, &$obj, array $params) {}
    public static class call_user_method_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol methodnameSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol objSymbol = SymbolUtils.getArgumentRef(ip, 1);
            // 引数の特定
            Symbol paramarrSymbol = SymbolUtils.getArgument(ip, 2);
            for (PhpArray phpArray : operator.extractPhpArray(paramarrSymbol)) {
                List<Symbol> list = Lists.newArrayList();
                for (int i = 0; i < 100; i++) { // 無限ループ対策
                    Symbol argSymbol = operator.getArrayValue(phpArray, i);
                    if (operator.isNull(argSymbol)) {
                        break;
                    } else {
                        list.add(argSymbol);
                    }
                }

                for (PhpObject phpObject : operator.extractPhpObject(objSymbol)) {
                    PhpNewable newable = phpObject.getPhpClass();
                    if (newable == null) {
                        continue;
                    }
                    List<PhpCallable> callableList = Lists.newArrayList();
                    for (String method : operator.getJavaStringList(methodnameSymbol)) {
                        PhpCallable callable = newable.getMethod(method);
                        if (callable != null) {
                            callableList.add(callable);
                        }
                        PhpCallable callable2 = newable.getStaticMethod(method);
                        if (callable2 != null) {
                            callableList.add(callable2);
                        }
                    }
                    for (PhpCallable callable : callableList) {
                        // 静的メソッドの場合もこれでOK
                        operator.merge(resultSymbol, SymbolUtils.callMethod(ip, phpObject, callable, list));
                    }
                }

            }
            return resultSymbol;
        }
    }

    // function forward_static_call ($function, $parameter = null, $_ = null) {}
    public static class forward_static_call implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);

            // 引数の特定
            List<Symbol> list = Lists.newArrayList();
            if (SymbolUtils.getArgumentSize(ip) > 1) {
                list = ip.getContext().getArgumentSymbolList()
                        .subList(1, SymbolUtils.getArgumentSize(ip));
            }

            List<PhpCallable> callableList = SymbolUtils.getStaticMethodList(ip, functionSymbol, list.size());
            for (PhpCallable callable : callableList) {
                operator.merge(resultSymbol, SymbolUtils.callStaticMethod(ip, callable, list));
            }

            return resultSymbol;
        }
    }

    // function forward_static_call_array ($function, array $parameters = null) {}
    public static class forward_static_call_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol parametersSymbol = SymbolUtils.getArgument(ip, 1);
            for (PhpArray phpArray : operator.extractPhpArray(parametersSymbol)) {
                List<Symbol> list = Lists.newArrayList();
                for (int i = 0; i < 100; i++) { // 無限ループ対策
                    Symbol argSymbol = operator.getArrayValue(phpArray, i);
                    if (operator.isNull(argSymbol)) {
                        break;
                    } else {
                        list.add(argSymbol);
                    }
                }
                List<PhpCallable> callableList = SymbolUtils.getStaticMethodList(ip, functionSymbol, list.size());
                for (PhpCallable callable : callableList) {
                    operator.merge(resultSymbol, SymbolUtils.callStaticMethod(ip, callable, list));
                }
            }
            return resultSymbol;
        }
    }

    // function serialize ($value) {}
    public static class serialize implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol valueSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = serialize(ip, valueSymbol);
            for (PhpObject phpObject : operator.extractPhpObject(valueSymbol)) {
                operator.merge(resultSymbol,
                        serialize(ip, phpObject.getPhpClass().__sleep(ip, phpObject)));
            }
            return resultSymbol;
        }
    }

    // function unserialize ($str) {}
    public static class unserialize implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createSymbol();
        }
    }

    // function var_dump ($expression, $_ = null) {}
    public static class var_dump implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            if (ip.isUseEcho()) {
                Symbol expressionSymbol = SymbolUtils.getArgumentRef(ip, 0);
                for (PhpObject phpObject : operator.extractPhpObject(expressionSymbol)) {
                    phpObject.getPhpClass().__debugInfo(ip, phpObject);
                }
                System.out.println("\n[var_dump] " + expressionSymbol.toString()
                        + " in " + ip.getSourcePosition());
            }
            return operator.null_();
        }
    }

    // function var_export ($expression, $return = null) {}
    public static class var_export implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol expressionSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = serialize(ip, expressionSymbol);
            for (PhpObject phpObject : operator.extractPhpObject(expressionSymbol)) {
                operator.merge(resultSymbol, phpObject.getPhpClass().__set_state(ip, phpObject));
            }
            Symbol returnSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(returnSymbol)) {
                return operator.null_();
            }
            for (PhpAnyType type : operator.getTypeSet(returnSymbol)) {
                if (type instanceof PhpBoolean) {
                    if (((PhpBoolean) type).isBool()) {
                        return resultSymbol;
                    }
                }
            }
            return operator.null_();
        }
    }

    // function debug_zval_dump ($variable) {}
    public static class debug_zval_dump implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function print_r ($expression, $return = null) {}
    public static class print_r implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol expressionSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = serialize(ip, expressionSymbol);
            Symbol returnSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(returnSymbol)) {
                return operator.bool();
            }
            for (PhpAnyType type : operator.getTypeSet(returnSymbol)) {
                if (type instanceof PhpBoolean) {
                    if (((PhpBoolean) type).isBool()) {
                        return resultSymbol;
                    }
                }
            }
            return operator.bool();
        }
    }

    // function memory_get_usage ($real_usage = null) {}
    public static class memory_get_usage implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function memory_get_peak_usage ($real_usage = null) {}
    public static class memory_get_peak_usage implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function register_shutdown_function ($function, $parameter = null, $_ = null) {}
    public static class register_shutdown_function implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
            List<Symbol> list = Lists.newArrayList();
            for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
                list.add(SymbolUtils.getArgument(ip, i));
            }
            SymbolUtils.callCallable(ip, functionSymbol, list);
            return operator.null_();
        }
    }

    // function register_tick_function ($function, $arg = null, $_ = null) {}
    public static class register_tick_function implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol functionSymbol = SymbolUtils.getArgument(ip, 0);
            List<Symbol> list = Lists.newArrayList();
            for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
                list.add(SymbolUtils.getArgument(ip, i));
            }
            SymbolUtils.callCallable(ip, functionSymbol, list);
            return operator.null_();
        }
    }

    // function unregister_tick_function ($function_name) {}
    public static class unregister_tick_function implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function highlight_file ($filename, $return = null) {}
    public static class highlight_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol returnSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(returnSymbol)) {
                return operator.bool();
            }
            for (PhpAnyType type : operator.getTypeSet(returnSymbol)) {
                if (type instanceof PhpBoolean) {
                    if (((PhpBoolean) type).isBool()) {
                        return operator.string();
                    }
                }
            }
            return operator.bool();
        }
    }

    // function show_source ($file_name, $return) {}
    // TODO file_nameがテイントなら出力もテイントにする
    public static class show_source implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol returnSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(returnSymbol)) {
                return operator.bool();
            }
            for (PhpAnyType type : operator.getTypeSet(returnSymbol)) {
                if (type instanceof PhpBoolean) {
                    if (((PhpBoolean) type).isBool()) {
                        return operator.string();
                    }
                }
            }
            return operator.bool();
        }
    }

    // function highlight_string ($str, $return = null) {}
    public static class highlight_string implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol returnSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(returnSymbol)) {
                return operator.bool();
            }
            for (PhpAnyType type : operator.getTypeSet(returnSymbol)) {
                if (type instanceof PhpBoolean) {
                    if (((PhpBoolean) type).isBool()) {
                        return SymbolUtils.getArgument(ip, 0);
                    }
                }
            }
            return operator.bool();
        }
    }

    // function php_strip_whitespace ($filename) {}
    public static class php_strip_whitespace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ini_get ($varname) {}
    public static class ini_get implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ini_get_all ($extension = null, $details = null) {}
    public static class ini_get_all implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function ini_set ($varname, $newvalue) {}
    public static class ini_set implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ini_alter ($varname, $newvalue) {}
    //  * &Alias; <function>ini_set</function>
    public static class ini_alter extends ini_set {
    }

    // function ini_restore ($varname) {}
    public static class ini_restore implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function get_include_path () {}
    public static class get_include_path implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function set_include_path ($new_include_path) {}
    public static class set_include_path implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function restore_include_path () {}
    public static class restore_include_path implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function setcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}
    public static class setcookie implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function setrawcookie ($name, $value = null, $expire = null, $path = null, $domain = null, $secure = null, $httponly = null) {}
    public static class setrawcookie implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function header ($string, $replace = true, $http_response_code = null) {}
    public static class header implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function header_remove ($name = null) {}
    public static class header_remove implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function headers_sent (&$file = null, &$line = null) {}
    public static class headers_sent implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function headers_list () {}
    public static class headers_list implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function connection_aborted () {}
    public static class connection_aborted implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function connection_status () {}
    public static class connection_status implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function ignore_user_abort ($value = null) {}
    public static class ignore_user_abort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function parse_ini_file ($filename, $process_sections = null, $scanner_mode = null) {}
    public static class parse_ini_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function parse_ini_string ($ini, $process_sections = null, $scanner_mode = null) {}
    public static class parse_ini_string implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function is_uploaded_file ($filename) {}
    public static class is_uploaded_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function move_uploaded_file ($filename, $destination) {}
    public static class move_uploaded_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function gethostbyaddr ($ip_address) {}
    public static class gethostbyaddr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function gethostbyname ($hostname) {}
    public static class gethostbyname implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function gethostbynamel ($hostname) {}
    public static class gethostbynamel implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function gethostname () {}
    public static class gethostname implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function dns_check_record ($host, $type) {}
    public static class dns_check_record implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function checkdnsrr ($host, $type = null) {}
    public static class checkdnsrr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function dns_get_mx ($hostname, &$mxhosts, &$weight) {}
    //  * &Alias; <function>getmxrr</function>
    public static class dns_get_mx extends getmxrr {
    }

    // function getmxrr ($hostname, array &$mxhosts, array &$weight = null) {}
    public static class getmxrr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function dns_get_record ($hostname, $type = null, array &$authns = null, array &$addtl = null) {}
    public static class dns_get_record implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }
}
