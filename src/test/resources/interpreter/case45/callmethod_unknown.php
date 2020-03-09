<?php
class A {
	public $a = "2";
	public function test() {
		echo "1";
	}
}
class B {
	public $a2 = "4";
	public function test2() {
		echo "3";
	}
}

// $a = new A();
$a->test (); // 1
echo $a->a; // 2
?>