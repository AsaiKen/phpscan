<?php

// Start of json v.1.2.1

interface JsonSerializable  {

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed Return data which should be serialized by json_encode.
	 */
	abstract public function jsonSerialize () {}

}

/**
 * Returns the JSON representation of a value
 * @link http://www.php.net/manual/en/function.json-encode.php
 * @param value mixed <p>
 * The value being encoded. Can be any type except
 * a resource.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded data.
 * </p>
 * @param options int[optional] <p>
 * Bitmask consisting of JSON_HEX_QUOT,
 * JSON_HEX_TAG,
 * JSON_HEX_AMP,
 * JSON_HEX_APOS,
 * JSON_NUMERIC_CHECK,
 * JSON_BIGINT_AS_STRING,
 * JSON_PRETTY_PRINT,
 * JSON_UNESCAPED_SLASHES,
 * JSON_FORCE_OBJECT,
 * JSON_UNESCAPED_UNICODE.
 * </p>
 * @return string a JSON encoded string on success.
 */
function json_encode ($value, $options = null) {}

/**
 * Decodes a JSON string
 * @link http://www.php.net/manual/en/function.json-decode.php
 * @param json string <p>
 * The json string being decoded.
 * </p>
 * <p>
 * This function only works with UTF-8 encoded data.
 * </p>
 * @param assoc bool[optional] <p>
 * When true, returned objects will be converted into
 * associative arrays.
 * </p>
 * @param depth int[optional] <p>
 * User specified recursion depth.
 * </p>
 * @param options int[optional] <p>
 * Bitmask of JSON decode options. Currently only
 * JSON_BIGINT_AS_STRING
 * is supported (default is to cast large integers as floats)
 * </p>
 * @return mixed the value encoded in json in appropriate
 * PHP type. Values true, false and
 * null (case-insensitive) are returned as true, false
 * and &null; respectively. &null; is returned if the
 * json cannot be decoded or if the encoded
 * data is deeper than the recursion limit.
 */
function json_decode ($json, $assoc = null, $depth = null, $options = null) {}

/**
 * Returns the last error occurred
 * @link http://www.php.net/manual/en/function.json-last-error.php
 * @return int an integer, the value can be one of the following 
 * constants:
 */
function json_last_error () {}


/**
 * All &lt; and &gt; are converted to \u003C and \u003E.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_TAG', 1);

/**
 * All &amp;s are converted to \u0026.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_AMP', 2);

/**
 * All ' are converted to \u0027.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_APOS', 4);

/**
 * All " are converted to \u0022.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_HEX_QUOT', 8);

/**
 * Outputs an object rather than an array when a non-associative array is
 * used. Especially useful when the recipient of the output is expecting
 * an object and the array is empty.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_FORCE_OBJECT', 16);

/**
 * Encodes numeric strings as numbers.
 * Available since PHP 5.3.3.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_NUMERIC_CHECK', 32);

/**
 * Don't escape /.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_SLASHES', 64);

/**
 * Use whitespace in returned data to format it.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_PRETTY_PRINT', 128);

/**
 * Encode multibyte Unicode characters literally (default is to escape as \uXXXX).
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_UNESCAPED_UNICODE', 256);

/**
 * No error has occurred.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_NONE', 0);

/**
 * The maximum stack depth has been exceeded.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_DEPTH', 1);

/**
 * Occurs with underflow or with the modes mismatch.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_STATE_MISMATCH', 2);

/**
 * Control character error, possibly incorrectly encoded.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_CTRL_CHAR', 3);

/**
 * Syntax error.
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_SYNTAX', 4);

/**
 * Malformed UTF-8 characters, possibly incorrectly encoded. This 
 * constant is available as of PHP 5.3.1.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_ERROR_UTF8', 5);
define ('JSON_OBJECT_AS_ARRAY', 1);

/**
 * Encodes large integers as their original string value.
 * Available since PHP 5.4.0.
 * @link http://www.php.net/manual/en/json.constants.php
 */
define ('JSON_BIGINT_AS_STRING', 2);

// End of json v.1.2.1
?>
