<?php

namespace {

	const AA = 1; // \AA
	const BB = 2; // \BB
}

namespace TEST {

	const AA = 3;
	class A {
		const AA = 4; // \TEST\A::AA
		public function test() {
			echo AA; // 3
			echo \TEST\A::AA; // 4
			echo BB; // 1
		}
	}

	$a = new A ();
	$a->test ();
}
?>