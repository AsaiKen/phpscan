<?php
class C {
	static function bb() {
		echo "C";
		echo static::bb ();
	}
}
class B extends C {
	public static $bb = "B";
	static function bb() {
		echo "B";
	}
	static function aa() {
		static::bb ();
		echo "B";
		self::bb ();
		parent::bb ();
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
// ABBCA
?>