<?php

namespace Foobar;

class Foo {
	static public function test() {
		print "1";
	}
}

call_user_func ( __NAMESPACE__ . '\Foo::test' ); // PHP 5.3.0 以降
call_user_func ( array (
		__NAMESPACE__ . '\Foo',
		'test'
) ); // PHP 5.3.0 以降

?>