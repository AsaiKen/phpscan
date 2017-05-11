<?php
static $a = 1;
echo $a; // 1
$a ++;
echo $a; // 2
function test() {
	static $a = 0;
	$a ++;
	echo $a;
}
test (); // 1
test (); // 2
?>