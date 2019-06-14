package net.katagaitai.phpscan.taint;

public enum EncodeTag {
    HTML_ESCAPE, URL_ENCODE, BASE64_ENCODE,
    //	ENCRYPT,
    SHELL_ENCODE, SQL_ESCAPE, STRIP_TAGS, BASENAME, JSON_ENCODE,
    REQUEST_HEADER,
}
