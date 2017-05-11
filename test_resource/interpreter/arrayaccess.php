<?php
class A {
	public $a = array ();
	public static $b = array ();
}

$a = new A ();
$a->a [] = 1;
echo $a->a [0]; // 1
$a->a [0] = 2;
echo $a->a [0]; // 2

$a::$b [] = 1;
echo $a::$b [0]; // 1
$a::$b [0] = 2;
echo $a::$b [0]; // 2
?>