<?php
class A {
	public static $a = "2";
	static public function test() {
		echo "1";
	}
}
class B {
	public static $a = "4";
	static public function test() {
		echo "3";
	}
}

$_GET ["TEST"]::test (); // UNKNOWN
echo $_GET ["TEST"]::$a; // UNKNOWN
?>