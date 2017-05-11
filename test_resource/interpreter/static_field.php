<?php
class A {
	public static $a = "A";
}
class B extends A {
	public static $a = "B";
	public function test() {
		// echo perent::$a; // syntax error
		echo self::$a; // B
		echo static::$a; // B
		echo $this::$a; // B
	}
}

$b = new B ();
$b->test ();

?>