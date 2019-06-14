package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolId;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.LinkedList;
import java.util.Map.Entry;

public class Standard_8 extends BuiltinBase {
    public Standard_8(Interpreter ip) {
        super(ip);
    }

    // function syslog ($priority, $message) {}
    public static class syslog implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function closelog () {}
    public static class closelog implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function header_register_callback ( callable $callback ) {}
    public static class header_register_callback implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol callbackSymbol = SymbolUtils.getArgument(ip, 0);
            SymbolUtils.callCallable(ip, callbackSymbol, Lists.newArrayList());
            return operator.bool();
        }
    }

    // function getimagesizefromstring ($imagedata , array &$imageinfo) {}
    public static class getimagesizefromstring implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function stream_set_chunk_size ($fp , $chunk_size) {}
    public static class stream_set_chunk_size implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function socket_import_stream ($stream) {}
    public static class socket_import_stream implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function define_syslog_variables () {}
    public static class define_syslog_variables implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function lcg_value () {}
    public static class lcg_value implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function metaphone ($str, $phones = null) {}
    public static class metaphone implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function ob_start ($output_callback = null, $chunk_size = null, $erase = null) {}
    public static class ob_start implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol outputcallbackSymbol = SymbolUtils.getArgument(ip, 0);
            SymbolUtils.callCallable(ip, outputcallbackSymbol, Lists.newArrayList(operator.string()));
            return operator.bool();
        }
    }

    // function ob_flush () {}
    public static class ob_flush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function ob_clean () {}
    public static class ob_clean implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function ob_end_flush () {}
    public static class ob_end_flush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function ob_end_clean () {}
    public static class ob_end_clean implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function ob_get_flush () {}
    public static class ob_get_flush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ob_get_clean () {}
    public static class ob_get_clean implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ob_get_length () {}
    public static class ob_get_length implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function ob_get_level () {}
    public static class ob_get_level implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function ob_get_status ($full_status = null) {}
    public static class ob_get_status implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function ob_get_contents () {}
    public static class ob_get_contents implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function ob_implicit_flush ($flag = null) {}
    public static class ob_implicit_flush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function ob_list_handlers () {}
    public static class ob_list_handlers implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function ksort (array &$array, $sort_flags = null) {}
    public static class ksort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function krsort (array &$array, $sort_flags = null) {}
    public static class krsort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function natsort (array &$array) {}
    public static class natsort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function natcasesort (array &$array) {}
    public static class natcasesort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function asort (array &$array, $sort_flags = null) {}
    public static class asort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function arsort (array &$array, $sort_flags = null) {}
    public static class arsort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function sort (array &$array, $sort_flags = null) {}
    public static class sort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function rsort (array &$array, $sort_flags = null) {}
    public static class rsort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function usort (array &$array, $cmp_function) {}
    // ユーザー定義の比較関数を使用して、配列を値でソートする
    public static class usort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol arrayValueSymbol = operator.getMergedArrayValueSymbol(arraySymbol);
            SymbolUtils.callCallable(ip, cmpfunctionSymbol, Lists.newArrayList(arrayValueSymbol, arrayValueSymbol));
            return operator.bool();
        }
    }

    // function uasort (array &$array, $cmp_function) {}
    // ユーザー定義の比較関数で配列をソートし、連想インデックスを保持する
    public static class uasort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol arrayValueSymbol = operator.getMergedArrayValueSymbol(arraySymbol);
            SymbolUtils.callCallable(ip, cmpfunctionSymbol, Lists.newArrayList(arrayValueSymbol, arrayValueSymbol));
            return operator.bool();
        }
    }

    // function uksort (array &$array, $cmp_function) {}
    // ユーザー定義の比較関数を用いて、キーで配列をソートする
    public static class uksort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol cmpfunctionSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol arrayKeySymbol = operator.getMergedArrayKeySymbol(arraySymbol);
            SymbolUtils.callCallable(ip, cmpfunctionSymbol, Lists.newArrayList(arrayKeySymbol, arrayKeySymbol));
            return operator.bool();
        }
    }

    // function shuffle (array &$array) {}
    public static class shuffle implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function array_walk (array &$array, $funcname, $userdata = null) {}
    public static class array_walk implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol funcnameSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol userdataSymbol = SymbolUtils.getArgument(ip, 2);
            SymbolUtils.callCallable(
                    ip,
                    funcnameSymbol,
                    Lists.newArrayList(
                            operator.getMergedArrayValueSymbol(arraySymbol),
                            operator.getMergedArrayKeySymbol(arraySymbol), userdataSymbol));
            return operator.bool();
        }
    }

    // function array_walk_recursive (array &$input, $funcname, $userdata = null) {}
    public static class array_walk_recursive implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol funcnameSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol userdataSymbol = SymbolUtils.getArgument(ip, 2);
            recursive(ip, funcnameSymbol, operator.getMergedArrayKeySymbol(arraySymbol),
                    operator.getMergedArrayValueSymbol(arraySymbol), userdataSymbol);
            return operator.bool();
        }

        private void recursive(Interpreter ip, Symbol callbackSymbol, Symbol keySymbol, Symbol valueSymbol,
                               Symbol dataSymbol) {
            SymbolOperator operator = ip.getOperator();
            for (PhpAnyType type : operator.getTypeSet(valueSymbol)) {
                if (type instanceof PhpArray) {
                    PhpArray phpArray = (PhpArray) type;
                    LinkedList<PhpArray> arrayWalkRecursiveStack = ip
                            .getStorage().getArrayWalkRecursiveStack();
                    if (arrayWalkRecursiveStack.contains(phpArray)) {
                        return;
                    }
                    arrayWalkRecursiveStack.push(phpArray);
                    try {
                        recursive(ip, callbackSymbol,
                                operator.getMergedArrayKeySymbol(phpArray),
                                operator.getMergedArrayValueSymbol(phpArray),
                                dataSymbol);
                    } finally {
                        arrayWalkRecursiveStack.pop();
                    }
                } else {
                    SymbolUtils.callCallable(ip, callbackSymbol, Lists.newArrayList(
                            operator.createSymbol(type), keySymbol, dataSymbol));
                }
            }
        }
    }

    // function count ($var, $mode = COUNT_NORMAL) {}
    public static class count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function end (array &$array) {}
    public static class end implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function prev (array &$array) {}
    public static class prev implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function next (array &$array) {}
    public static class next implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function reset (array &$array) {}
    public static class reset implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function current (array &$array) {}
    public static class current implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function key (array &$array) {}
    public static class key implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayKeySymbol(arraySymbol);
        }
    }

    // function min (array $value1, $value2 = null, ...$values) {}
    public static class min implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function max (array $value1, $value2 = null, ...$values) {}
    public static class max implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function in_array ($needle, array $haystack, $strict = null) {}
    public static class in_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function array_search ($needle, array $haystack, $strict = null) {}
    // 指定した値を配列で検索し、見つかった場合に対応するキーを返す
    public static class array_search implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol haystackSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getMergedArrayKeySymbol(haystackSymbol);
        }
    }

    // function extract (array $var_array, $extract_type = null, $prefix = null) {}
    // 配列からシンボルテーブルに変数をインポートする
    public static class extract implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol vararraySymbol = SymbolUtils.getArgument(ip, 0);
            for (PhpArray phpArray : operator.extractPhpArray(vararraySymbol)) {
                for (Entry<SymbolId, SymbolId> entry : phpArray.getArray().entrySet()) {
                    // 追加順で取得できる
                    Symbol keySymbol = operator.getSymbol(entry.getKey());
                    Symbol valueSymbol = operator.getSymbol(entry.getValue());
                    if (keySymbol == null || valueSymbol == null) {
                        continue;
                    }
                    for (String keyString : operator.getJavaStringList(keySymbol)) {
                        ip.getScopeStack().peek().put(keyString, operator.copy(valueSymbol));
                    }
                }
            }
            return operator.integer();
        }
    }

    // function compact ($varname, $_ = null) {}
    // 変数名とその値から配列を作成する
    public static class compact implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpArray resultArray = operator.createPhpArray();
            for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol symbol = SymbolUtils.getArgument(ip, i);
                for (PhpAnyType type : operator.getTypeSet(symbol)) {
                    if (type instanceof PhpString) {
                        String string = ((PhpString) type).getString();
                        Symbol valueSymbol = ip.getSymbolOrCreate(string);
                        operator.putArrayValue(resultArray, string, valueSymbol);
                    } else if (type instanceof PhpArray) {
                        for (Entry<SymbolId, SymbolId> entry : ((PhpArray) type).getArray().entrySet()) {
                            Symbol valueSymbol = operator.getSymbol(entry.getValue());
                            if (valueSymbol == null) {
                                continue;
                            }
                            for (PhpAnyType type2 : operator.getTypeSet(valueSymbol)) {
                                if (type instanceof PhpString) {
                                    String string = ((PhpString) type2).getString();
                                    Symbol valueSymbol2 = ip.getSymbolOrCreate(string);
                                    operator.putArrayValue(resultArray, string, valueSymbol2);
                                }
                            }
                        }
                    }
                }
            }
            return operator.createSymbol(resultArray);
        }
    }

    // function array_fill ($start_index, $num, $value) {}
    public static class array_fill implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol valueSymbol = SymbolUtils.getArgument(ip, 2);
            return operator.array(valueSymbol);
        }
    }

    // function array_fill_keys (array $keys, $value) {}
    public static class array_fill_keys implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpArray phpArray = operator.createPhpArray();
            Symbol keysSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol valueSymbol = SymbolUtils.getArgument(ip, 1);
            operator.putArrayValue(phpArray, operator.getMergedArrayValueSymbol(keysSymbol), valueSymbol);
            return operator.createSymbol(phpArray);
        }
    }

    // function range ($low, $high, $step = null) {}
    public static class range implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function array_multisort (array &$arr, $arg = null, $arg = null, $_ = null) {}
    public static class array_multisort implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function array_push (array &$array, $var, $_ = null) {}
    public static class array_push implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol varSymbol = SymbolUtils.getArgument(ip, i);
                operator.putArrayValue(arraySymbol, operator.getNextIndex(arraySymbol), varSymbol);
            }
            return operator.integer();
        }
    }

    // function array_pop (array &$array) {}
    public static class array_pop implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function array_shift (array &$array) {}
    public static class array_shift implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            return operator.getMergedArrayValueSymbol(arraySymbol);
        }
    }

    // function array_unshift (array &$array, $var, $_ = null) {}
    public static class array_unshift implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol arraySymbol = SymbolUtils.getArgumentRef(ip, 0);
            for (int i = 1; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol varSymbol = SymbolUtils.getArgument(ip, i);
                operator.putArrayValue(arraySymbol, operator.getNextIndex(arraySymbol), varSymbol);
            }
            return operator.integer();
        }
    }

    // function array_splice (array &$input, $offset, $length = null, $replacement = null) {}
    public static class array_splice implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol inputSymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol resultSymbol = operator.copy(inputSymbol);
            if (SymbolUtils.getArgumentSize(ip) > 3) {
                Symbol replacementSymbol = SymbolUtils.getArgument(ip, 3);
                for (PhpAnyType type : operator.getTypeSet(replacementSymbol)) {
                    if (type instanceof PhpString) {
                        operator.putArrayValue(inputSymbol, operator.getNextIndex(inputSymbol), replacementSymbol);
                    } else if (type instanceof PhpArray) {
                        PhpArray phpArray = (PhpArray) type;
                        operator.putArrayValue(inputSymbol, operator.getNextIndex(inputSymbol),
                                operator.getMergedArrayValueSymbol(phpArray));
                    }
                }
            }
            return resultSymbol;
        }
    }

    // function array_slice (array $array, $offset, $length = null, $preserve_keys = null) {}
    public static class array_slice implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgumentRef(ip, 0);
        }
    }

    // function array_merge (array $array1, array $array2 = null, array $_ = null) {}
    public static class array_merge implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpArray phpArray = operator.createPhpArray();
            for (int i = 0; i < SymbolUtils.getArgumentSize(ip); i++) {
                Symbol arraySymbol = SymbolUtils.getArgument(ip, i);
                operator.putArrayValue(phpArray, operator.getMergedArrayKeySymbol(arraySymbol),
                        operator.getMergedArrayValueSymbol(arraySymbol));
            }
            return operator.createSymbol(phpArray);
        }
    }
}
