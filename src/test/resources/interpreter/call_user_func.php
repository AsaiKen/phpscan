<?php
// http://php.net/manual/ja/function.call-user-func.php
// call_user_func() のパラメータは 参照渡しではない
error_reporting ( E_ALL );

$b = 0;
function increment($var) {
	global $b;
	$var ++;
	echo $var; // 1
	$b += $var;
}

$a = 0;
call_user_func ( 'increment', $a );
echo $a; // 0
echo $b; // 1

?>