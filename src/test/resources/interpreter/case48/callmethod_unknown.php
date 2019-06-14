<?php
class A {
	public $a = "2";
	public function test() {
		echo "1";
	}
}
class B {
	public $a = "4";
	public function test() {
		echo "3";
	}
}

// $a = new A();
$a->test (); // UNKNOWN
echo $a->a; // UNKNOWN
?>