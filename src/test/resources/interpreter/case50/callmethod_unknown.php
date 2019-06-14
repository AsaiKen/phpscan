<?php
class A {
	public $a = "2";
	public function test($arg1, $arg2 = null) {
		echo "1";
	}
}
class B {
	public $a = "4";
	public function test($arg1) {
		echo "3";
	}
}

// $a = new B();
$a->test ( "1" ); // UNKNOWN
echo $a->a; // UNKNOWN
?>