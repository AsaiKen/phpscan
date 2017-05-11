<?php
class A {
	public $a = array (
			array ()
	);
}

$a = new A ();
$a->a [1] [2] [] = 1;
echo $a->a [1] [2] [0];
?>