<?php
class myclass {
	static function say_hello() {
		echo "1";
	}
}

$classname = "myclass";

call_user_func ( array (
		$classname,
		'say_hello'
) );
call_user_func ( $classname . '::say_hello' ); // 5.2.3 以降

$myobject = new myclass ();

call_user_func ( array (
		$myobject,
		'say_hello'
) );

?>