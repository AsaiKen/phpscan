<?php
class A {
	public function __call() {
		echo "12";
	}
}
class B {
	public function test() {
		echo "34";
	}
}

$a = new A ();
$a->test (); // 12

?>