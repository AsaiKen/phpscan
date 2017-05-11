<?php

namespace NAME1 {

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
}

namespace NAME2 {

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
}
?>