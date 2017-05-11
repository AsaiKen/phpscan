<?php
class A {
	public static $a = "2";
	static public function test() {
		echo "1";
	}
}
class B {
	public static $a2 = "4";
	static public function test2() {
		echo "3";
	}
}

$_GET ["TEST"]::test (); // 1
echo $_GET ["TEST"]::$a; // 2
?>