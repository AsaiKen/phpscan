<?php
class B {
	public static $bb = "B";
	static function bb() {
		echo "B";
	}
	static function aa() {
		static::bb ();
		echo "B";
		// $this->bb();
		self::bb ();
		// $this::cc();
		// bb(); // Fatal Error
	}
}
class A extends B {
	public static $bb = "A";
	static function cc() {
		echo "A";
	}
	static function bb() {
		echo "A";
	}
	static function aa() {
		parent::aa ();
	}
}

A::aa ();
// ABB
?>