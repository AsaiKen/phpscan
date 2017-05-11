<?php
class A {
	public $a = 0;
}
$a = new A ();
$b = $a;
$b->a = 1;
echo $a->a;
// 1
$a = new A ();
$b = &$a;
$b->a = 1;
echo $a->a;
// 1
?>