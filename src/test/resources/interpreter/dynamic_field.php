<?php
class A {
	public $a = 0;
	public static $b = 0;
	public $g = 0;
	function __construct($d, $e, $f) {
		$c = "g";
		$this->$d = 1;
		$this::$$e = 1;
		$this->$$f = 1;
	}
}

$a = new A ( "a", "b", "c" );
echo $a->a; // 1
echo A::$b; // 1
echo $a->g; // 1

?>