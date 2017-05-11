<?php
class A {
	public function __call() {
		echo "12";
	}
	static public function __callstatic() {
		echo "34";
	}
}

?>