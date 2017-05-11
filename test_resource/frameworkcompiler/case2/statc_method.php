<?php
class A {
	private static function pri($a) {
		eval ( $a );
	}
	public static function pub($a) {
		eval ( $a );
	}
	static function non($a) {
		eval ( $a );
	}
}
?>