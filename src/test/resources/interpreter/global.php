<?php
class A {
	public function test() {
		global $global;
		echo $global;
	}
}
$a = new A();
$global = 1;
$a->test();
?>