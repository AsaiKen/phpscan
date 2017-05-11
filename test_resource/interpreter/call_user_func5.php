<?php
class myclass {
	function say_hello() {
		echo "1";
	}
}

$myobject = new myclass ();

call_user_func ( array (
		$myobject,
		'say_hello'
) );

?>