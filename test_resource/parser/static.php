<?php
class A {
	// FieldsDeclaration
	static public $a = 1;
	public function test() {
		// StaticStatement
		static $a = 1;
		echo $a;
	}
}
// StaticStatement
static $a = 1;

echo $a;
// StaticFieldAccess
echo A::$a;

echo self::$a;
echo parent::$a;
echo static::$a;

?>