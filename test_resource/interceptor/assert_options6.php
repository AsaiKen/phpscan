<?php
assert_options ( ASSERT_ACTIVE, "true" );
assert ( $_GET ['p'] ); // not vuln

assert_options ( ASSERT_ACTIVE, "false" );
assert ( $_GET ['p'] ); // not vuln

assert_options ( ASSERT_ACTIVE, "0" );
assert ( $_GET ['p'] ); // not vuln

assert_options ( ASSERT_ACTIVE, 0 );
assert ( $_GET ['p'] ); // not vuln

assert_options ( ASSERT_ACTIVE, null );
assert ( $_GET ['p'] ); // not vuln

assert_options ( ASSERT_ACTIVE, "hoge" );
assert ( $_GET ['p'] ); // not vuln

?>