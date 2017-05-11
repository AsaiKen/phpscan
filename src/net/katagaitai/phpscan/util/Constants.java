package net.katagaitai.phpscan.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

import lombok.extern.log4j.Log4j2;

import com.google.common.collect.Sets;

@Log4j2
public class Constants {
	public static final String DEFAULT_CHARSET_STRING = "UTF-8";
	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_STRING);
	public static final String APP_NAME = "phpscan";
	public static final String PREFIX = "__" + APP_NAME + "_";
	public static final String MAIN_FUNCTION_NAME = PREFIX + "main";
	public static final String PREFIX_STRING_LITERAL = PREFIX + "String_";
	public static final String PREFIX_INTEGER_LITERAL = PREFIX + "Integer_";
	public static final String PREFIX_FLOAT_LITERAL = PREFIX + "Float_";
	public static final String PREFIX_BOOLEAN_LITERAL = PREFIX + "Boolean_";
	public static final String NULL_LITERAL = PREFIX + "Null";
	public static final String EXAMPLE_URL =
			"http://example.com/index.html?query_name=query_value#fragment_value";
	public static final String EXAMPLE_COOKIE = "cookie_name=cookie_value";
	public static final String EXAMPLE_USERNAME = "username";
	public static final String EXAMPLE_PASSWORD = "passowrd";
	public static final String INPUTABLE_CHAR_STRING =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

	public static URI EXAMPLE_URL_OBJECT;
	static {
		try {
			EXAMPLE_URL_OBJECT = new URI(EXAMPLE_URL);
		} catch (URISyntaxException e) {
			// ここには来ない
			log.warn("", e);
		}
	}

	public static final String SESSION_ID_VARIABLE = "$" + PREFIX + "session_id";
	// 以下はマージ対象なのでPREFIXをつけない
	public static final String STRTOK_STRING_VARIABLE = "$" + APP_NAME + "_strtok_string";
	public static final String MBSTRING_EREG_SERACH_STRING_VARIABLE = "$" + APP_NAME + "_mbstring_ereg_search_string";
	public static final String MBSTRING_INFO_ARRAY_VARIABLE = "$" + APP_NAME + "_mbstring_info_array";
	public static final String ICONV_ENCODING_ARRAY_VARIABLE = "$" + APP_NAME + "_iconv_encoding_array";
	public static final String DBA_HANDLER_ARRAY_VARIABLE = "$" + APP_NAME + "_dba_handler_array";
	public static final String SESSION_CALLBACK_ARRAY_VARIABLE = "$" + APP_NAME + "_session_callback_array";
	public static final String SESSION_HANDLER_VARIABLE = "$" + APP_NAME + "_session_handler";

	public static final String[] INTERNAL_VARIABLE_NAME = new String[] {
			STRTOK_STRING_VARIABLE,
			MBSTRING_EREG_SERACH_STRING_VARIABLE,
			MBSTRING_INFO_ARRAY_VARIABLE,
			ICONV_ENCODING_ARRAY_VARIABLE,
			DBA_HANDLER_ARRAY_VARIABLE,
			SESSION_CALLBACK_ARRAY_VARIABLE,
			SESSION_HANDLER_VARIABLE
	};

	public static final Set<String> SPL_DEFAULT_EXTENSION_SET =
			Collections.unmodifiableSet(Sets.newHashSet(".inc", ".php"));
	public static String[] SUPERGLOBAL_NAMES = new String[] {
			"$GLOBALS", "$_COOKIE", "$_ENV", "$_FILES", "$_GET", "$_POST", "$_REQUEST",
			"$_SERVER", "$_SESSION", "$argc", "$argv", "$HTTP_RAW_POST_DATA",
			"$http_response_header", "$php_errormsg" };
	public static final int DUMMY_ARRAY_SIZE = 10;
	// ダミー値を特徴のある値にしてマーカーとして利用する
	public static final String DUMMY_STRING = PREFIX + "dummy";
	public static final String DUMMY_ANY_STRING = PREFIX + "dummy_any";
	public static final long DUMMY_INTEGER = 1337;
	public static final String[] DEFAULT_PHP_FILE_EXTENSIONS = new String[] { "php", "inc" };

	public static final int MAX_TYPESET_SIZE = 100;
	public static final int MAX_TAINTSET_SIZE = 10;
	public static final int MAX_CALL_COUNT = 10;
	public static final int MAX_CALL_STACK_SIZE = 5;
	public static final long TIMEOUT = 3 * 60 * 1000;
	public static final int VULN_SIZE_PER_PAGE = 15;
}
