<?php
var_dump ( $_POST );
if (func_num_args () != 2 or empty ( $parname ) or empty ( $type )) {
	var_dump ( $_POST );
	throw new coding_exception ( 'required_param() requires $parname and $type to be specified (parameter: ' . $parname . ')' );
}
var_dump ( $_POST );
if (isset ( $_POST [$parname] )) { // POST has precedence
	var_dump ( $_POST );
	$param = $_POST [$parname];
	var_dump ( $param );
} else if (isset ( $_GET [$parname] )) {
	var_dump ( $_GET );
	$param = $_GET [$parname];
	var_dump ( $param );
} else {
	print_error ( 'missingparam', '', '', $parname );
}
var_dump ( $param );
if (var_dump ( $param ) && is_array ( $param ) && var_dump ( $param )) {
	debugging ( 'Invalid array parameter detected in required_param(): ' . $parname );
	// TODO: switch to fatal error in Moodle 2.3
	// print_error('missingparam', '', '', $parname);
	var_dump ( $param );
	return required_param_array ( $parname, $type );
} else {
	var_dump ( $param );
}
eval ( $param );
return clean_param ( $param, $type );
?>