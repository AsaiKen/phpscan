<?php
class A {
	public $key;
	function __construct() {
		$this->key = $_GET ['value'];
	}
}
$obj = new A ();
// TODO 検知できない
$obj->$_GET ['key'] = 'value';
var_dump ( $obj );
?>