<?php
class MyDestructableClass {
	function __construct() {
		echo "In constructor\n";
		$this->name = "MyDestructableClass";
	}
	function __destruct() {
		echo "Destroying " . $this->name . "\n";
	}
}

$obj = new MyDestructableClass ();
?>