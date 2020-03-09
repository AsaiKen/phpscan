<?php

namespace A {

	class B {
		const C = 1; // \A\B\C
	}
	const D = 1; // \A\D
	             // define ( 'E', 1 ); // \E
	             // define ( 'A\F', 1 ); // \A\F
	             // define ( '\A\G', 1 ); // ignore?
	function test() {
		// syntax error
		// const G = 1;
	}
	echo D; // \A\D
	        // echo A\D; // undefined
	echo \A\D;
	// echo E; // \A\E
	// echo A\F; // undefined
	// echo \A\F;
	// echo \A\G; // undefined
}

namespace {

	echo A\B::C; // 1
	echo A\D; // 1
		          // echo E; // 1
		          // echo A\F; // 1
}

?>