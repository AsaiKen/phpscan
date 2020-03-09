<?php
class A {
	var $a, $b;
	public $a = 3;
	private static final $var;
}
$a = new A();
echo $a->a;
?>