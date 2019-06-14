package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.command.CastType;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.util.Map;

public class Standard_5 extends BuiltinBase {
    public Standard_5(Interpreter ip) {
        super(ip);
    }

    // function boolval($var) {}
    public static class boolval implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function intval ($var, $base = null) {}
    public static class intval implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function floatval ($var) {}
    public static class floatval implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function doubleval ($var) {}
    public static class doubleval implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strval ($var) {}
    public static class strval implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function gettype ($var) {}
    public static class gettype implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function settype (&$var, $type) {}
    public static class settype implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol varSymbol = SymbolUtils.getArgumentRef(ip, 0);
            Symbol typeSymbol = SymbolUtils.getArgument(ip, 1);
            Symbol resultSymbol = operator.createSymbol();
            for (String type : operator.getJavaStringList(typeSymbol)) {
                CastType castType;
                if (type.equals("bool")) {
                    castType = CastType.BOOL;
                } else if (type.equals("int")) {
                    castType = CastType.INT;
                } else if (type.equals("double")) {
                    castType = CastType.REAL;
                } else if (type.equals("string")) {
                    castType = CastType.STRING;
                } else if (type.equals("array")) {
                    castType = CastType.ARRAY;
                } else if (type.equals("object")) {
                    castType = CastType.OBJECT;
                } else {
                    continue;
                }
                operator.merge(resultSymbol, operator.cast(varSymbol, castType));
            }
            return resultSymbol;
        }
    }

    // function is_null ($var) {}
    public static class is_null implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_resource ($var) {}
    public static class is_resource implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_bool ($var) {}
    public static class is_bool implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_long ($var) {}
    public static class is_long implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_float ($var) {}
    public static class is_float implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_int ($var) {}
    public static class is_int implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_integer ($var) {}
    public static class is_integer implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_double ($var) {}
    public static class is_double implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_real ($var) {}
    public static class is_real implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_numeric ($var) {}
    public static class is_numeric implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_string ($var) {}
    public static class is_string implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_array ($var) {}
    public static class is_array implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_object ($var) {}
    public static class is_object implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_scalar ($var) {}
    public static class is_scalar implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_callable ($name, $syntax_only = null, &$callable_name = null) {}
    public static class is_callable implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function pclose ($handle) {}
    public static class pclose implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    public static final String OPEN_CONTENT = "OPEN_CONTENT";

    // function popen ($command, $mode) {}
    public static class popen implements PhpCallable {

        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            operator.putResourceValue(phpResource, OPEN_CONTENT, operator.string());
            return operator.createSymbol(phpResource);
        }
    }

    // function readfile ($filename, $use_include_path = null, $context = null) {}
    public static class readfile implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function rewind ($handle) {}
    public static class rewind implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function rmdir ($dirname, $context = null) {}
    public static class rmdir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function umask ($mask = null) {}
    public static class umask implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function fclose ($handle) {}
    public static class fclose implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function feof ($handle) {}
    public static class feof implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function fgetc ($handle) {}
    public static class fgetc implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getResourceValue(handleSymbol, OPEN_CONTENT);
        }
    }

    // function fgets ($handle, $length = null) {}
    public static class fgets implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getResourceValue(handleSymbol, OPEN_CONTENT);
        }
    }

    // function fgetss ($handle, $length = null, $allowable_tags = null) {}
    public static class fgetss implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getResourceValue(handleSymbol, OPEN_CONTENT);
        }
    }

    // function fread ($handle, $length) {}
    public static class fread implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getResourceValue(handleSymbol, OPEN_CONTENT);
        }
    }

    // function fopen ($filename, $mode, $use_include_path = null, $context = null) {}
    public static class fopen implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            Symbol filenameSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol contentSymbol = operator.string();
            Map<String, Symbol> filenameContentMap = ip.getStorage().getFilenameContentMap();
            for (String filenameString : operator.getJavaStringList(filenameSymbol)) {
                if (filenameString.isEmpty()
                        || filenameString.equals(Constants.DUMMY_STRING)
                        || filenameString.equals(Constants.DUMMY_ANY_STRING)) {
                    continue;
                }
                // TODO マージに非対応
                operator.incrementReference(contentSymbol);
                filenameContentMap.put(filenameString, contentSymbol);
            }
            operator.putResourceValue(phpResource, OPEN_CONTENT, contentSymbol);
            return operator.createSymbol(phpResource);
        }
    }

    // function fpassthru ($handle) {}
    public static class fpassthru implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function ftruncate ($handle, $size) {}
    public static class ftruncate implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function fstat ($handle) {}
    public static class fstat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function fseek ($handle, $offset, $whence = SEEK_SET) {}
    public static class fseek implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function ftell ($handle) {}
    public static class ftell implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function fflush ($handle) {}
    public static class fflush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function fwrite ($handle, $string, $length = null) {}
    public static class fwrite implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(handleSymbol, OPEN_CONTENT, stringSymbol);
            return operator.integer();
        }
    }

    // function fputs ($handle, $string, $length) {}
    public static class fputs implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol handleSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol stringSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addResourceValue(handleSymbol, OPEN_CONTENT, stringSymbol);
            return operator.integer();
        }
    }

    // function mkdir ($pathname, $mode = 0777, $recursive = false, $context = null) {}
    public static class mkdir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function rename ($oldname, $newname, $context = null) {}
    public static class rename implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function copy ($source, $dest, $context = null) {}
    public static class copy implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function tempnam ($dir, $prefix) {}
    public static class tempnam implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol dirSymbol = SymbolUtils.getArgument(ip, 0);
            Symbol prefixSymbol = SymbolUtils.getArgument(ip, 1);
            operator.addNewTaintSet(dirSymbol, prefixSymbol.getTaintSet());
            return dirSymbol;
        }
    }

    // function tmpfile () {}
    public static class tmpfile implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createSymbol(operator.createPhpResource());
        }
    }

    // function file ($filename, $flags = null, $context = null) {}
    public static class file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function file_get_contents ($filename, $flags = null, $context = null, $offset = null, $maxlen = null) {}
    public static class file_get_contents implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function file_put_contents ($filename, $data, $flags = null, $context = null) {}
    public static class file_put_contents implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }
}
