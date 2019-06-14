package net.katagaitai.phpscan.php.builtin;

import com.google.common.collect.Lists;
import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.builtin.Standard_0.Directory;
import net.katagaitai.phpscan.php.types.PhpAnyType;
import net.katagaitai.phpscan.php.types.PhpBoolean;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

import java.io.File;

public class Standard_7 extends BuiltinBase {
    public Standard_7(Interpreter ip) {
        super(ip);
    }

    // function fsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}
    public static class fsockopen implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            operator.putResourceValue(phpResource, Standard_5.OPEN_CONTENT, operator.string());
            return operator.createSymbol(phpResource);
        }
    }

    // function pfsockopen ($hostname, $port = null, &$errno = null, &$errstr = null, $timeout = null) {}
    public static class pfsockopen implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            operator.putResourceValue(phpResource, Standard_5.OPEN_CONTENT, operator.string());
            return operator.createSymbol(phpResource);
        }
    }

    // function pack ($format, $args = null, $_ = null) {}
    public static class pack implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function unpack ($format, $data) {}
    public static class unpack implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function get_browser ($user_agent = null, $return_array = null) {}
    public static class get_browser implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            if (SymbolUtils.getArgumentSize(ip) > 1) {
                Symbol resultSymbol = operator.createSymbol();
                Symbol returnarraySymbol = SymbolUtils.getArgument(ip, 1);
                for (PhpAnyType type : operator.getTypeSet(returnarraySymbol)) {
                    if (type instanceof PhpBoolean) {
                        if (((PhpBoolean) type).isBool()) {
                            operator.merge(resultSymbol, operator.array());
                        } else {
                            operator.merge(resultSymbol, operator.object());
                        }
                    }
                }
                return resultSymbol;
            } else {
                return operator.object();
            }
        }
    }

    // function crypt ($str, $salt = null) {}
    public static class crypt implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function opendir ($path, $context = null) {}
    public static class opendir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            return operator.createSymbol(phpResource);
        }
    }

    // function closedir ($dir_handle = null) {}
    public static class closedir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function chdir ($directory) {}
    public static class chdir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function chroot ($directory) {}
    public static class chroot implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function getcwd () {}
    public static class getcwd implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            File file = ip.getFile();
            if (file == null) {
                return operator.string();
            } else {
                return operator.createSymbol(file.getParentFile().getAbsolutePath());
            }
        }
    }

    // function rewinddir ($dir_handle = null) {}
    public static class rewinddir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function readdir ($dir_handle = null) {}
    public static class readdir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function dir ($directory, $context) {}
    public static class dir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Directory clazz = new Directory(ip);
            return SymbolUtils.new_(ip, clazz, Lists.newArrayList());
        }
    }

    // function scandir ($directory, $sorting_order = null, $context = null) {}
    public static class scandir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function glob ($pattern, $flags = null) {}
    public static class glob implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function fileatime ($filename) {}
    public static class fileatime implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function filectime ($filename) {}
    public static class filectime implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function filegroup ($filename) {}
    public static class filegroup implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function fileinode ($filename) {}
    public static class fileinode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function filemtime ($filename) {}
    public static class filemtime implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function fileowner ($filename) {}
    public static class fileowner implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function fileperms ($filename) {}
    public static class fileperms implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function filesize ($filename) {}
    public static class filesize implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function filetype ($filename) {}
    public static class filetype implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function file_exists ($filename) {}
    public static class file_exists implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_writable ($filename) {}
    public static class is_writable implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_writeable ($filename) {}
    public static class is_writeable implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_readable ($filename) {}
    public static class is_readable implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_executable ($filename) {}
    public static class is_executable implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_file ($filename) {}
    public static class is_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_dir ($filename) {}
    public static class is_dir implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function is_link ($filename) {}
    public static class is_link implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function stat ($filename) {}
    public static class stat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function lstat ($filename) {}
    public static class lstat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function chown ($filename, $user) {}
    public static class chown implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function chgrp ($filename, $group) {}
    public static class chgrp implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function lchown ($filename, $user) {}
    public static class lchown implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function lchgrp ($filename, $group) {}
    public static class lchgrp implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function chmod ($filename, $mode) {}
    public static class chmod implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function touch ($filename, $time = null, $atime = null) {}
    public static class touch implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function clearstatcache ($clear_realpath_cache = null, $filename = null) {}
    public static class clearstatcache implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function disk_total_space ($directory) {}
    public static class disk_total_space implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function disk_free_space ($directory) {}
    public static class disk_free_space implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function diskfreespace ($path) {}
    //  * &Alias; <function>disk_free_space</function>
    public static class diskfreespace extends disk_free_space {
    }

    // function mail ($to, $subject, $message, $additional_headers = null, $additional_parameters = null) {}
    public static class mail implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function ezmlm_hash ($addr) {}
    public static class ezmlm_hash implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function openlog ($ident, $option, $facility) {}
    public static class openlog implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }
}
