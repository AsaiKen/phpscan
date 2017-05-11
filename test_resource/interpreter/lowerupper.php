<?php
class B {
	function testB() {
		echo "2";
	}
}
class A extends B {
	function test() {
		SELF::TESTA ();
		PARENT::TESTB ();
	}
	function testA() {
		echo "1";
	}
}

$a = new a();
$a->TEST(); // 12

?>