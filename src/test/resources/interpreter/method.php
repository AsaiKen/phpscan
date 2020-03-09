<?php
class B {
	public $bb = "B";

	function bb() {
		echo "B";
	}

	function aa() {
		static::bb();
		echo "B";
		$this->bb();
		self::bb();
		$this::cc();
	}
}

class A extends B {
	public $bb = "A";

	static function cc() {
		echo "A";
	}

	function bb() {
		echo "A";
	}
	function aa() {
		parent::aa();
	}
}

$a = new A();
$a->aa();
// ABABA
?>