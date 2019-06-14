package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.Constants;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Standard_0 extends BuiltinBase {
    public Standard_0(Interpreter ip) {
        super(ip);
    }

    // http://php.net/manual/ja/class.php-user-filter.php
    // このクラスの子クラスを stream_filter_register() に渡します。
    public static class php_user_filter extends PhpClassBase {
        public php_user_filter(Interpreter ip) {
            super(ip);
        }

        private static final String FILTERNAME = "filtername";
        private static final String PARAMS = "params";

        @Override
        public void initialize(Interpreter interpreter) {
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            SymbolOperator operator = interpreter.getOperator();
            //        public $filtername;
            operator.putFieldValue(thisSymbol, FILTERNAME, operator.string());
            //        public $params;
            operator.putFieldValue(thisSymbol, PARAMS, operator.array());
        }

        //        public function filter ($in, $out, &$consumed, $closing) {}
        public static class filter implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return null;
            }
        }

        //        public function onCreate () {}
        public static class onCreate implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return null;
            }
        }

        //        public function onClose () {}
        public static class onClose implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                return null;
            }
        }
    }

    // created by calling the dir() function
    public static class Directory extends PhpClassBase {
        public Directory(Interpreter ip) {
            super(ip);
        }

        // public function close ( $dir_handle ) {}
        public static class close implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function rewind ( $dir_handle ) {}
        public static class rewind implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.null_();
            }
        }

        // public function read ( $dir_handle) { }
        public static class read implements PhpCallable {
            @Override
            public Symbol call(Interpreter ip) {
                SymbolOperator operator = ip.getOperator();
                return operator.string();
            }
        }
    }

    // function constant ($name) {}
    public static class constant implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol resultSymbol = operator.createSymbol();
            Symbol nameSymbol = SymbolUtils.getArgument(ip, 0);
            for (String string : operator.getJavaStringList(nameSymbol)) {
                Symbol symbol = SymbolUtils.getConstant(ip, string);
                if (symbol != null) {
                    operator.merge(resultSymbol, symbol);
                }
            }
            return resultSymbol;
        }
    }

    // function bin2hex ($str) {}
    public static class bin2hex implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function sleep ($seconds) {}
    public static class sleep implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function usleep ($micro_seconds) {}
    public static class usleep implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function time_nanosleep ($seconds, $nanoseconds) {}
    public static class time_nanosleep implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function time_sleep_until ($timestamp) {}
    public static class time_sleep_until implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function strptime ($date, $format) {}
    public static class strptime implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function flush () {}
    public static class flush implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.null_();
        }
    }

    // function wordwrap ($str, $width = 75, $break = "\n", $cut = false) {}
    public static class wordwrap implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol strSymbol = SymbolUtils.getArgument(ip, 0);
            if (ip.getContext().getArgumentSymbolList().size() >= 3) {
                Symbol breakSymbol = SymbolUtils.getArgument(ip, 2);
                operator.addNewTaintSet(strSymbol, breakSymbol.getTaintSet());
            }
            return strSymbol;
        }
    }

    // function htmlspecialchars ($string, $flags = ENT_COMPAT, $encoding = 'UTF-8', $double_encode = true) {}
    public static class htmlspecialchars implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function htmlentities ($string, $quote_style = null, $charset = null, $double_encode = null) {}
    public static class htmlentities implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function html_entity_decode ($string, $quote_style = null, $charset = null) {}
    public static class html_entity_decode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function htmlspecialchars_decode ($string, $quote_style = null) {}
    public static class htmlspecialchars_decode implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            return SymbolUtils.getArgument(ip, 0);
        }
    }

    // function get_html_translation_table ($table = null, $quote_style = null) {}
    public static class get_html_translation_table implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function sha1 ($str, $raw_output = null) {}
    public static class sha1 implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function sha1_file ($filename, $raw_output = null) {}
    public static class sha1_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function md5 ($str, $raw_output = null) {}
    public static class md5 implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function md5_file ($filename, $raw_output = null) {}
    public static class md5_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function crc32 ($str) {}
    public static class crc32 implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function iptcparse ($iptcblock) {}
    public static class iptcparse implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function iptcembed ($iptcdata, $jpeg_file_name, $spool = null) {}
    public static class iptcembed implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function getimagesize ($filename, array &$imageinfo = null) {}
    public static class getimagesize implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array();
        }
    }

    // function imageaffine($image, $affine, $clip = null) {}
    public static class imageaffine implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createSymbol(operator.createPhpResource());
        }
    }

    // function imageaffinematrixconcat(array $m1, array $m2) {}
    public static class imageaffinematrixconcat implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function imageaffinematrixget ($type, $options = null) {}
    public static class imageaffinematrixget implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function imagecrop ($image, $rect) {}
    public static class imagecrop implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.array(operator.integer());
        }
    }

    // function imagecropauto ($image, $mode = -1, $threshold = .5, $color = -1) {}
    public static class imagecropauto implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createSymbol(operator.createPhpResource());
        }
    }

    // function imageflip ($image, $mode) {}
    public static class imageflip implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function imagepalettetotruecolor ($image) {}
    public static class imagepalettetotruecolor implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function imagescale ($image, $new_width, $new_height = -1, $mode = IMG_BILINEAR_FIXED) {}
    public static class imagescale implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.createSymbol(operator.createPhpResource());
        }
    }

    // function imagesetinterpolation ($image, $method = IMG_BILINEAR_FIXED) {}
    public static class imagesetinterpolation implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function image_type_to_mime_type ($imagetype) {}
    public static class image_type_to_mime_type implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function image_type_to_extension ($imagetype, $include_dot = null) {}
    public static class image_type_to_extension implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function phpinfo ($what = null) {}
    public static class phpinfo implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function phpversion ($extension = null) {}
    public static class phpversion implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function phpcredits ($flag = null) {}
    public static class phpcredits implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.bool();
        }
    }

    // function php_logo_guid () {}
    public static class php_logo_guid implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_real_logo_guid () {}
    public static class php_real_logo_guid implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_egg_logo_guid () {}
    public static class php_egg_logo_guid implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function zend_logo_guid () {}
    public static class zend_logo_guid implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_sapi_name () {}
    public static class php_sapi_name implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_uname ($mode = null) {}
    public static class php_uname implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_ini_scanned_files () {}
    public static class php_ini_scanned_files implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function php_ini_loaded_file () {}
    public static class php_ini_loaded_file implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.string();
        }
    }

    // function strnatcmp ($str1, $str2) {}
    public static class strnatcmp implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strnatcasecmp ($str1, $str2) {}
    public static class strnatcasecmp implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function substr_count ($haystack, $needle, $offset = null, $length = null) {}
    public static class substr_count implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strspn ($subject, $mask, $start = null, $length = null) {}
    public static class strspn implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    // function strcspn ($str1, $str2, $start = null, $length = null) {}
    public static class strcspn implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            return operator.integer();
        }
    }

    public static class strtok implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            Symbol symbol = ip.getGlobalScope().getOrPhpNull(Constants.STRTOK_STRING_VARIABLE);
            if (ip.getContext().getArgumentSymbolList().size() > 0) {
                ip.getOperator().assign(symbol, SymbolUtils.getArgument(ip, 0));
            }
            return symbol;
        }
    }

}
