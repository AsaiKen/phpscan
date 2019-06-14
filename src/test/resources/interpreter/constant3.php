<?php

namespace A {

	const A = 1;
}

namespace {

	class A {
		const A = 2;
	}
}

namespace {

	echo A\A; // 1
	echo A::A; // 2
}
?>