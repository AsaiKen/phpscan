<?php
// ConstantDeclaration
const a = 0;
// StaticStatement > Assignment > Variable
static $a = 0;
// Assignment > ArrayAccess
$a[] = 0;
// Assignment > List
list(, $a) = array(0, 0);
// Assignment > Variable
$a = 0;
// Assignment > ReflectionVariable > Variable
$$a = 0;
class A {
	// ConstantDeclaration
	const a = 0;
	// FieldsDeclaration
	static $a = 0;
	// FieldsDeclaration
	private $a = 0;
}
// Assignment > StaticFieldAccess
A::$a = 0;
$a = new A;
// Assignment > FieldAccess
$a->a = 0;
const A = 0;
// Scalar value='A'
$a = A;
?>