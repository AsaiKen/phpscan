package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Sets;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpArray;
import net.katagaitai.phpscan.php.types.PhpString;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.Set;

public class Standard_1 extends BuiltinBase {
    public Standard_1(Interpreter ip) {
        super(ip);
    }

    // function strtoupper ($string) {}
    public static class strtoupper implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strtolower ($str) {}
    public static class strtolower implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strpos ($haystack, $needle, $offset = 0) {}
    public static class strpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function stripos ($haystack, $needle, $offset = null) {}
    public static class stripos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strrpos ($haystack, $needle, $offset = 0) {}
    public static class strrpos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strripos ($haystack, $needle, $offset = null) {}
    public static class strripos implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strrev ($string) {}
    public static class strrev implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function hebrev ($hebrew_text, $max_chars_per_line = null) {}
    public static class hebrev implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function hebrevc ($hebrew_text, $max_chars_per_line = null) {}
    public static class hebrevc implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function nl2br ($string, $is_xhtml = null) {}
    public static class nl2br implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function basename ($path, $suffix = null) {}
    public static class basename implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol pathSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            Set<PhpAnyType> tSet = Sets.newHashSet();
            Symbol suffixSymbol = null;
            if (ip.getContext().getArgumentSymbolList().size() >= 2) {
                suffixSymbol = SymbolUtils.getArgument(ip, 1);
            }
            for (String pathString : operator.getJavaStringList(pathSymbol)) {
                pathString = pathString.replace("\\", "/");
                while (pathString.length() > 0 && pathString.endsWith("/")) {
                    pathString = pathString.substring(0, pathString.length() - 1);
                }
                int j = pathString.lastIndexOf("/");
                if (j == -1) {
                    tSet.add(new PhpString(""));
                    continue;
                }
                String name = pathString.substring(j + 1);
                if (suffixSymbol == null) {
                    tSet.add(new PhpString(name));
                } else {
                    for (String suffixString : operator.getJavaStringList(suffixSymbol)) {
                        if (name.endsWith(suffixString)) {
                            int i = name.lastIndexOf(suffixString);
                            name = name.substring(0, i);
                        }
                        tSet.add(new PhpString(name));
                    }
                }
            }
            operator.setTypeSet(resultSymbol, tSet);
            return resultSymbol;
        }
    }

    // function dirname ($path) {}
    public static class dirname implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol pathSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol resultSymbol = SymbolUtils.getArgument(ip, 0);
            Set<PhpAnyType> tSet = Sets.newHashSet();
            for (String pathString : operator.getJavaStringList(pathSymbol)) {
                pathString = pathString.replace("\\", "/");
                while (pathString.length() > 0 && pathString.endsWith("/")) {
                    pathString = pathString.substring(0, pathString.length() - 1);
                }
                int j = pathString.lastIndexOf("/");
                if (j == -1) {
                    tSet.add(new PhpString("/"));
                    continue;
                }
                String name = pathString.substring(0, j);
                tSet.add(new PhpString(name));
            }
            operator.setTypeSet(resultSymbol, tSet);
            return resultSymbol;
        }
    }

    // function pathinfo ($path, $options = null) {}
    // 次の要素を含む連想配列を返します。 dirname、basename、 extension (存在すれば)、そして filename。
    // options指定がある場合は文字列を返す
    public static class pathinfo implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol pathSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol optionsSymbol = SymbolUtils.getArgument(ip, 1);
            if (operator.isNull(optionsSymbol)) {
                Symbol dirnameSymbol = SymbolUtils.getArgument(ip, 0);
                Set<PhpAnyType> dirnameTypeSet = Sets.newHashSet();
                Symbol basenameSymbol = SymbolUtils.getArgument(ip, 0);
                Set<PhpAnyType> basenameTypeSet = Sets.newHashSet();
                Symbol extensionSymbol = SymbolUtils.getArgument(ip, 0);
                Set<PhpAnyType> extensionTypeSet = Sets.newHashSet();
                Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
                Set<PhpAnyType> filenameTypeSet = Sets.newHashSet();
                for (String pathString : operator.getJavaStringList(pathSymbol)) {
                    // basename, dirname
                    String string = pathString;
                    string = string.replace("\\", "/");
                    while (string.length() > 0 && string.endsWith("/")) {
                        string = string.substring(0, string.length() - 1);
                    }
                    int j = string.lastIndexOf("/");
                    if (j >= 0 && j < string.length() - 1) {
                        basenameTypeSet.add(new PhpString(string.substring(j + 1)));
                        dirnameTypeSet.add(new PhpString(string.substring(0, j)));
                    } else {
                        basenameTypeSet.add(new PhpString("/"));
                        dirnameTypeSet.add(new PhpString(""));
                    }
                    // extension, filename
                    string = pathString;
                    int k = string.lastIndexOf(".");
                    if (k >= 0 && k < string.length() - 1) {
                        extensionTypeSet.add(new PhpString(string.substring(k + 1)));
                        filenameTypeSet.add(new PhpString(string.substring(0, k)));
                    } else {
                        filenameTypeSet.add(new PhpString(string));
                    }
                }
                // taintSetはそのままにしてtypeSetを入れ替え
                operator.setTypeSet(dirnameSymbol, dirnameTypeSet);
                operator.setTypeSet(basenameSymbol, basenameTypeSet);
                operator.setTypeSet(extensionSymbol, extensionTypeSet);
                operator.setTypeSet(filenameSymbol, filenameTypeSet);
                PhpArray phpArray = operator.createPhpArray();
                operator.putArrayValue(phpArray, "dirname", dirnameSymbol);
                operator.putArrayValue(phpArray, "basename", basenameSymbol);
                operator.putArrayValue(phpArray, "extension", extensionSymbol);
                operator.putArrayValue(phpArray, "filename", filenameSymbol);
                return operator.createSymbol(phpArray);
            } else {
                return pathSymbol;
            }
        }
    }

    // function stripslashes ($str) {}
    public static class stripslashes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function stripcslashes ($str) {}
    public static class stripcslashes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strstr ($haystack, $needle, $before_needle = null) {}
    public static class strstr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function stristr ($haystack, $needle, $before_needle = null) {}
    public static class stristr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strrchr ($haystack, $needle) {}
    public static class strrchr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function str_shuffle ($str) {}
    public static class str_shuffle implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function str_word_count ($string, $format = null, $charlist = null) {}
    public static class str_word_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(SymbolUtils.getArgument(ip, 0));
        }
    }

    // function str_split ($string, $split_length = 1) {}
    public static class str_split implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(SymbolUtils.getArgument(ip, 0));
        }
    }

    // function strpbrk ($haystack, $char_list) {}
    public static class strpbrk implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function substr_compare ($main_str, $str, $offset, $length = null, $case_insensitivity = null) {}
    public static class substr_compare implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strcoll ($str1, $str2) {}
    public static class strcoll implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function money_format ($format, $number) {}
    public static class money_format implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function substr ($string, $start, $length = null) {}
    public static class substr implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function substr_replace ($string, $replacement, $start, $length = null) {}
    public static class substr_replace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol replacementSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addNewTaintSet(stringSymbol, replacementSymbol.getTaintSet());
            return stringSymbol;
        }
    }

    // function quotemeta ($str) {}
    public static class quotemeta implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function ucfirst ($str) {}
    public static class ucfirst implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function lcfirst ($str) {}
    public static class lcfirst implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function ucwords ($str, $delimiters = " \t\r\n\f\v") {}
    public static class ucwords implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strtr ($str, $from, $to) {}

    /**
     * @See {@link _Standard_manual.strtr}
     */

    // function addslashes ($str) {}
    public static class addslashes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function addcslashes ($str, $charlist) {}
    public static class addcslashes implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function rtrim ($str, $charlist = " \t\n\r\0\x0B") {}
    public static class rtrim implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function str_replace ($search, $replace, $subject, &$count = null) {}
    public static class str_replace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol replaceSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol subjectSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addNewTaintSet(subjectSymbol, replaceSymbol.getTaintSet());
            return subjectSymbol;
        }
    }

    // function str_ireplace ($search, $replace, $subject, &$count = null) {}
    public static class str_ireplace implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol replaceSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol subjectSymbol = SymbolUtils.getArgument(ip, 2);
            operator.addNewTaintSet(subjectSymbol, replaceSymbol.getTaintSet());
            return subjectSymbol;
        }
    }

    // function str_repeat ($input, $multiplier) {}
    public static class str_repeat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function count_chars ($string, $mode = null) {}
    public static class count_chars implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(SymbolUtils.getArgument(ip, 0));
        }
    }

    // function chunk_split ($body, $chunklen = null, $end = null) {}
    public static class chunk_split implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function trim ($str, $charlist = " \t\n\r\0\x0B") {}
    public static class trim implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function ltrim ($str, $charlist = " \t\n\r\0\x0B") {}
    public static class ltrim implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function strip_tags ($str, $allowable_tags = null) {}
    public static class strip_tags implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function similar_text ($first, $second, &$percent = null) {}
    public static class similar_text implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function explode ($delimiter, $string, $limit = null) {}
    public static class explode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(SymbolUtils.getArgument(ip, 1));
        }
    }

    // function implode ($glue, array $pieces) {}
    public static class implode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol glueSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol piecesValueSymbol = operator.getMergedArrayValueSymbol(SymbolUtils.getArgument(ip, 1));
            operator.addNewTaintSet(piecesValueSymbol, glueSymbol.getTaintSet());
            return piecesValueSymbol;
        }
    }

    // function join ($glue, $pieces) {}
    //  * &Alias; <function>implode</function>
    public static class join extends implode {
    }

    // function setlocale ($category, $locale, $_ = null) {}
    public static class setlocale implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function localeconv () {}
    public static class localeconv implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

}
