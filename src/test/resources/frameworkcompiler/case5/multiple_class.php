<?php
class A {
	public function __construct($a) {
		eval ( $a );
	}
	public function a($a) {
		eval ( $a );
	}
	private function pri($a) {
		eval ( $a );
	}
	public function pub($a) {
		eval ( $a );
	}
	function non($a) {
		eval ( $a );
	}
}
class B {
	public function __construct($a) {
		eval ( $a );
	}
	public function b($a) {
		eval ( $a );
	}
	private function pri($a) {
		eval ( $a );
	}
	public function pub($a) {
		eval ( $a );
	}
	function non($a) {
		eval ( $a );
	}
}
?>