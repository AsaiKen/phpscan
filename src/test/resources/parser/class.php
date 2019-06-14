<?php

namespace TEST {

	class A {
		// symtax error
		// class B {}
	}
	$a = new A ();
	class B extends \TEST\A {
	}
	class B extends TEST\A {
	}
	$b = new \TEST\B();
	$b = new TEST\B();
	$b = new namespace\B();
}

?>