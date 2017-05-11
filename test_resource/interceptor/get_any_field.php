<?php
class A {
	public $f1 = 1;
	public $f2 = 2;
	public $f3 = 3;
	public $f4 = 4;
	public $f5 = 5;
}
$a = new A ();
echo $a->$_GET ["TEST"]; // 12345
?>