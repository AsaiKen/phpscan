<?php

namespace {

	const B = 3;
}

namespace Z {

	const B = 1;
	class A {
		const B = 2;
		function test() {
			echo B; // 1
			echo A::B; // 2
		}
	}
}

namespace {

	$a = new Z\A ();
	$a->test ();
}
?>