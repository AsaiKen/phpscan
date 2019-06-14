package net.katagaitai.phpscan.php.builtin;

import net.katagaitai.phpscan.compiler.BuiltinBase;
import net.katagaitai.phpscan.compiler.PhpCallable;
import net.katagaitai.phpscan.compiler.PhpClassBase;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.php.types.PhpResource;
import net.katagaitai.phpscan.symbol.Symbol;
import net.katagaitai.phpscan.symbol.SymbolOperator;
import net.katagaitai.phpscan.util.SymbolUtils;

public class Curl extends BuiltinBase {
    public Curl(Interpreter ip) {
        super(ip);
    }

    public static class CURLFile extends PhpClassBase {
        public CURLFile(Interpreter ip) {
            super(ip);
        }

        @Override
        public void initialize(Interpreter interpreter) {
            super.initialize(interpreter);
            //		    public $name;
            //		    public $mime;
            //		    public $postname;
            SymbolOperator operator = interpreter.getOperator();
            Symbol thisSymbol = interpreter.getContext().getThisSymbol();
            operator.putFieldValue(thisSymbol, "name", operator.string());
            operator.putFieldValue(thisSymbol, "mime", operator.string());
            operator.putFieldValue(thisSymbol, "postname", operator.string());
        }

        //			function __construct($filename, $mimetype, $postname) {
        //			public function getFilename() {
        //			public function getMimeType() {
        //			public function getPostFilename() {
        //			public function setMimeType($mime) {
        //			public function setPostFilename($postname) {
        //			public function __wakeup() {
    }

    public static final String CURL_CONTENT = "CURL_CONTENT";

    //	function curl_init ($url = null) {}
    public static class curl_init implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            PhpResource phpResource = operator.createPhpResource();
            operator.putResourceValue(phpResource, CURL_CONTENT, operator.string());
            return operator.createSymbol(phpResource);
        }
    }

    //	function curl_copy_handle ($ch) {}
    //	function curl_version ($age = null) {}
    //	function curl_setopt ($ch, $option, $value) {}
    //	function curl_setopt_array ($ch, array $options) {}
    //	function curl_share_close ($sh) {}
    //	function curl_share_init () {}
    //	function curl_share_setopt ($sh, $option, $value ) {}
    //	function curl_strerror ($errornum ) {}
    //	function  curl_unescape ($ch, $str)  {}
    //	function curl_exec ($ch) {}
    public static class curl_exec implements PhpCallable {
        @Override
        public Symbol call(Interpreter ip) {
            SymbolOperator operator = ip.getOperator();
            Symbol chSymbol = SymbolUtils.getArgument(ip, 0);
            return operator.getResourceValue(chSymbol, CURL_CONTENT);
        }
    }

    //	function curl_getinfo ($ch, $opt = null) {}
    //	function curl_error ($ch) {}
    //	function curl_errno ($ch) {}
    //	function curl_escape($ch, $str) {}
    //	function curl_file_create($filename, $mimetype, $postname) {}
    //	function curl_close ($ch) {}
    //	function curl_multi_init () {}
    //	function curl_multi_add_handle ($mh, $ch) {}
    //	function curl_multi_remove_handle ($mh, $ch) {}
    //	function curl_multi_select ($mh, $timeout = null) {}
    //	function curl_multi_setopt ($mh, $option, $value) {}
    //	function curl_multi_strerror ($errornum) {}
    //	function curl_pause ($ch, $bitmask ) {}
    //	function curl_reset ($ch) {}
    //	function curl_multi_exec ($mh, &$still_running) {}
    //	function curl_multi_getcontent ($ch) {}
    //	function curl_multi_info_read ($mh, &$msgs_in_queue = null) {}
    //	function curl_multi_close ($mh) {}

}
