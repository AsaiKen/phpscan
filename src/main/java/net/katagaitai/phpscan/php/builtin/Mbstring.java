package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.List;

public class Mbstring extends BuiltinBase {
    public Mbstring(Interpreter ip) {
        super(ip);
        SymbolOperator operator = ip.getOperator();
        putConstant("\\MB_OVERLOAD_MAIL", operator.createSymbol(1));
        putConstant("\\MB_OVERLOAD_STRING", operator.createSymbol(2));
        putConstant("\\MB_OVERLOAD_REGEX", operator.createSymbol(4));
        putConstant("\\MB_CASE_UPPER", operator.createSymbol(0));
        putConstant("\\MB_CASE_LOWER", operator.createSymbol(1));
        putConstant("\\MB_CASE_TITLE", operator.createSymbol(2));
    }

    // function mb_convert_case ($str, $mode, $encoding = null) {}
    public static class mb_convert_case implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strtoupper ($str, $encoding = null) {}
    public static class mb_strtoupper implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strtolower ($str, $encoding = null) {}
    public static class mb_strtolower implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_language ($language = null) {}
    public static class mb_language implements PhpCallable {
        private static final String KEY = "language";

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol languageSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            if (operator.isNull(languageSymbol)) {
                return operator.getArrayValue(symbol, KEY);
            } else {
                operator.putArrayValue(symbol, KEY, languageSymbol);
                return operator.bool();
            }
        }
    }

    // function mb_internal_encoding ($encoding = null) {}
    public static class mb_internal_encoding implements PhpCallable {
        private static final String KEY = "internal_encoding";

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol encodingSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            if (operator.isNull(encodingSymbol)) {
                String string = KEY;
                return operator.getArrayValue(symbol, string);
            } else {
                operator.putArrayValue(symbol, KEY, encodingSymbol);
                return operator.bool();
            }
        }
    }

    // function mb_http_input ($type = null) {}
    public static class mb_http_input implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mb_http_output ($encoding = null) {}
    public static class mb_http_output implements PhpCallable {
        private static final String KEY = "http_output";

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol encodingSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            if (operator.isNull(encodingSymbol)) {
                return operator.getArrayValue(symbol, KEY);
            } else {
                operator.putArrayValue(symbol, KEY, encodingSymbol);
                return operator.bool();
            }
        }
    }

    // function mb_detect_order ($encoding_list = null) {}
    public static class mb_detect_order implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mb_substitute_character ($substrchar = null) {}
    public static class mb_substitute_character implements PhpCallable {
        private static final String KEY = "substitute_character";

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol encodingSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            if (operator.isNull(encodingSymbol)) {
                return operator.getArrayValue(symbol, KEY);
            } else {
                operator.putArrayValue(symbol, KEY, encodingSymbol);
                return operator.bool();
            }
        }
    }

    // function mb_parse_str ($encoded_string, array &$result = null) {}
    // 配列result またはグローバル配列に設定します。
    public static class mb_parse_str implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            int size = SymbolUtils.getArgumentSize(ip);
            Symbol encodedstringSymbol = SymbolUtils.getArgument(ip, 0);
            if (size >= 2) {
                Symbol arrSymbol = SymbolUtils.getArgumentRef(ip, 1);
                operator.assign(arrSymbol, operator.array(encodedstringSymbol));
            } else {
                for (String string : operator.getJavaStringList(encodedstringSymbol)) {
                    String[] params = string.split("&");
                    for (String param : params) {
                        String[] kv = param.split("=", 2);
                        String key = kv[0];
                        String value = "";
                        if (kv.length >= 2) {
                            value = kv[1];
                        }
                        Symbol valueSymbol = operator.createSymbol(new PhpString(value));
                        operator.addNewTaintSet(valueSymbol, encodedstringSymbol.getTaintSet());
                        if (key.matches(".*\\[[^\\]]*\\]")) {
                            // a[b]=1
                            int start = key.indexOf("[");
                            int end = key.lastIndexOf("]");
                            String array = key.substring(0, start);
                            Symbol arraySymbol = ip.getGlobalScope().getOrCreate("$" + array);
                            if (start + 1 == end) {
                                // a[] = 1
                                Symbol nextKeySymbol = operator.getNextIndex(arraySymbol);
                                operator.putArrayValue(arraySymbol, nextKeySymbol, valueSymbol);
                            } else {
                                // a[b] = 1
                                String arrayKey = key.substring(start + 1, end);
                                Symbol keySymbol = operator.createSymbol(new PhpString(arrayKey));
                                operator.addNewTaintSet(keySymbol, encodedstringSymbol.getTaintSet());
                                operator.putArrayValue(arraySymbol, keySymbol, valueSymbol);
                            }
                        } else {
                            // a=1
                            Symbol symbol = ip.getGlobalScope().getOrCreate("$" + key);
                            operator.merge(symbol, valueSymbol);
                        }
                    }
                }
            }
            return operator.bool();
        }
    }

    // function mb_output_handler ($contents, $status) {}
    public static class mb_output_handler implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_preferred_mime_name ($encoding) {}
    public static class mb_preferred_mime_name implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mb_strlen ($str, $encoding = null) {}
    public static class mb_strlen implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_strpos ($haystack, $needle, $offset = null, $encoding = null) {}
    public static class mb_strpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_strrpos ($haystack, $needle, $offset = null, $encoding = null) {}
    public static class mb_strrpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_stripos ($haystack, $needle, $offset = null, $encoding = null) {}
    public static class mb_stripos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_strripos ($haystack, $needle, $offset = null, $encoding = null) {}
    public static class mb_strripos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_strstr ($haystack, $needle, $part = null, $encoding = null) {}
    public static class mb_strstr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strrchr ($haystack, $needle, $part = null, $encoding = null) {}
    public static class mb_strrchr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_stristr ($haystack, $needle, $part = null, $encoding = null) {}
    public static class mb_stristr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strrichr ($haystack, $needle, $part = null, $encoding = null) {}
    public static class mb_strrichr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_substr_count ($haystack, $needle, $encoding = null) {}
    public static class mb_substr_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_substr ($str, $start, $length = null, $encoding = null) {}
    public static class mb_substr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strcut ($str, $start, $length = null, $encoding = null) {}
    public static class mb_strcut implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_strwidth ($str, $encoding = null) {}
    public static class mb_strwidth implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_strimwidth ($str, $start, $width, $trimmarker = null, $encoding = null) {}
    public static class mb_strimwidth implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_convert_encoding ($str, $to_encoding, $from_encoding = null) {}
    public static class mb_convert_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_detect_encoding ($str, $encoding_list = null, $strict = null) {}
    public static class mb_detect_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mb_list_encodings () {}
    public static class mb_list_encodings implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mb_encoding_aliases ($encoding) {}
    public static class mb_encoding_aliases implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function mb_convert_kana ($str, $option = null, $encoding = null) {}
    public static class mb_convert_kana implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_encode_mimeheader ($str, $charset = null, $transfer_encoding = null, $linefeed = null, $indent = null) {}
    public static class mb_encode_mimeheader implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_decode_mimeheader ($str) {}
    public static class mb_decode_mimeheader implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_convert_variables ($to_encoding, $from_encoding, &$vars, &$_ = null) {}
    public static class mb_convert_variables implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mb_encode_numericentity ($str, array $convmap, $encoding = null) {}
    public static class mb_encode_numericentity implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_decode_numericentity ($str, array $convmap, $encoding = null) {}
    public static class mb_decode_numericentity implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            return resultSymbol;
        }
    }

    // function mb_send_mail ($to, $subject, $message, $additional_headers = null, $additional_parameter = null) {}
    public static class mb_send_mail implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mb_get_info ($type = null) {}
    public static class mb_get_info implements PhpCallable {
        //		array(14) {
        //			  ["internal_encoding"]=>
        //			  string(10) "ISO-8859-1"
        //			  ["http_output"]=>
        //			  string(4) "pass"
        //			  ["http_output_conv_mimetypes"]=>
        //			  string(31) "^(text/|application/xhtml\+xml)"
        //			  ["func_overload"]=>
        //			  int(0)
        //			  ["func_overload_list"]=>
        //			  string(11) "no overload"
        //			  ["mail_charset"]=>
        //			  string(5) "UTF-8"
        //			  ["mail_header_encoding"]=>
        //			  string(6) "BASE64"
        //			  ["mail_body_encoding"]=>
        //			  string(6) "BASE64"
        //			  ["illegal_chars"]=>
        //			  int(0)
        //			  ["encoding_translation"]=>
        //			  string(3) "Off"
        //			  ["language"]=>
        //			  string(7) "neutral"
        //			  ["detect_order"]=>
        //			  array(2) {
        //			    [0]=>
        //			    string(5) "ASCII"
        //			    [1]=>
        //			    string(5) "UTF-8"
        //			  }
        //			  ["substitute_character"]=>
        //			  int(63)
        //			  ["strict_detection"]=>
        //			  string(3) "Off"
        //			}
        @Override
        public Symbol call(Interpreter ip) {
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            return symbol;
        }
    }

    // function mb_check_encoding ($var = null, $encoding = null) {}
    public static class mb_check_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mb_regex_encoding ($encoding = null) {}
    public static class mb_regex_encoding implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function mb_regex_set_options ($options = null) {}
    public static class mb_regex_set_options implements PhpCallable {
        private static final String KEY = "regex_options";

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol encodingSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_INFO_ARRAY_VARIABLE);
            if (operator.isNull(encodingSymbol)) {
                return operator.getArrayValue(symbol, KEY);
            } else {
                operator.putArrayValue(symbol, KEY, encodingSymbol);
                return operator.bool();
            }
        }
    }

    // function mb_ereg ($pattern, $string, array $regs = null) {}
    public static class mb_ereg implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_eregi ($pattern, $string, array $regs = null) {}
    public static class mb_eregi implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_ereg_replace ($pattern, $replacement, $string, $option = null) {}
    public static class mb_ereg_replace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol replacementSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addNewTaintSet(stringSymbol, replacementSymbol.getTaintSet());
            return stringSymbol;
        }
    }

    // function mb_ereg_replace_callback ($pattern, callable $callback, $string, $option = "msr") {}
    public static class mb_ereg_replace_callback implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol callbackSymbol = SymbolUtils.getArgument(ip, 1);
            List<Symbol> list = Lists.newArrayList();
            list.add(operator.array());
            Symbol replacementSymbol = SymbolUtils.callCallable(ip, callbackSymbol, list);
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addNewTaintSet(stringSymbol, replacementSymbol.getTaintSet());
            return stringSymbol;
        }

    }

    // function mb_eregi_replace ($pattern, $replace, $string, $option = null) {}
    public static class mb_eregi_replace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol replaceSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addNewTaintSet(stringSymbol, replaceSymbol.getTaintSet());
            return stringSymbol;
        }
    }

    // function mb_split ($pattern, $string, $limit = null) {}
    public static class mb_split implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
            return operator.createSymbol(operator.createPhpArrayDummy(stringSymbol));
        }
    }

    // function mb_ereg_match ($pattern, $string, $option = null) {}
    public static class mb_ereg_match implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mb_ereg_search ($pattern = null, $option = null) {}
    public static class mb_ereg_search implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mb_ereg_search_pos ($pattern = null, $option = null) {}
    public static class mb_ereg_search_pos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function mb_ereg_search_regs ($pattern = null, $option = null) {}
    public static class mb_ereg_search_regs implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_EREG_SERACH_STRING_VARIABLE);
            return symbol;
        }
    }

    // function mb_ereg_search_init ($string, $pattern = null, $option = null) {}
    public static class mb_ereg_search_init implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_EREG_SERACH_STRING_VARIABLE);
            operator.assign(symbol, SymbolUtils.getArgument(ip, 0));
            return operator.bool();
        }
    }

    // function mb_ereg_search_getregs () {}
    public static class mb_ereg_search_getregs implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.MBSTRING_EREG_SERACH_STRING_VARIABLE);
            return operator.array(symbol);
        }
    }

    // function mb_ereg_search_getpos () {}
    public static class mb_ereg_search_getpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function mb_ereg_search_setpos ($position) {}
    public static class mb_ereg_search_setpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function mbregex_encoding ($encoding) {}
    public static class mbregex_encoding extends mb_regex_encoding {
    }

    // function mbereg ($pattern, $string, &$registers) {}
    public static class mbereg extends mb_ereg {
    }

    // function mberegi ($pattern, $string, &$registers) {}
    public static class mberegi extends mb_eregi {
    }

    // function mbereg_replace ($pattern, $replacement, $string, $option) {}
    public static class mbereg_replace extends mb_ereg_replace {
    }

    // function mberegi_replace ($pattern, $replacement, $string) {}
    public static class mberegi_replace extends mb_eregi_replace {
    }

    // function mbsplit ($pattern, $string, $limit) {}
    public static class mbsplit extends mb_split {
    }

    // function mbereg_match ($pattern, $string, $option) {}
    public static class mbereg_match extends mb_ereg_match {
    }

    // function mbereg_search ($pattern, $option) {}
    public static class mbereg_search extends mb_ereg_search {
    }

    // function mbereg_search_pos ($pattern, $option) {}
    public static class mbereg_search_pos extends mb_ereg_search_pos {
    }

    // function mbereg_search_regs ($pattern, $option) {}
    public static class mbereg_search_regs extends mb_ereg_search_regs {
    }

    // function mbereg_search_init ($string, $pattern, $option) {}
    public static class mbereg_search_init extends mb_ereg_search_init {
    }

    // function mbereg_search_getregs () {}
    public static class mbereg_search_getregs extends mb_ereg_search_getregs {
    }

    // function mbereg_search_getpos () {}
    public static class mbereg_search_getpos extends mb_ereg_search_getpos {
    }

    // function mbereg_search_setpos ($position) {}
    public static class mbereg_search_setpos extends mb_ereg_search_setpos {
    }
}
