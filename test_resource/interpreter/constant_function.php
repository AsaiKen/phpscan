<?php

namespace Z {

	class A {
		const TEST = 1;
		public function test() {
			// symtax error
			// const TEST = 1;
		}
	}
	const TEST = 1;
}

namespace {

	const TEST = 1;

	echo constant ( "TEST" ); // 1
	echo constant ( "\\TEST" ); // 1

	echo constant ( "Z\\TEST" ); // 1
	echo constant ( "\\Z\\TEST" ); // 1

	echo constant ( "Z\\A::TEST" ); // 1
	echo constant ( "\\Z\\A::TEST" ); // 1
}
?>