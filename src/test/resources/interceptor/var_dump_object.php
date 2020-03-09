<?php
class A {
	public $key;
	function __construct() {
		$this->key = $_GET ['test'];
	}
}
$obj = new A ();
var_dump ( $obj->key );
?>