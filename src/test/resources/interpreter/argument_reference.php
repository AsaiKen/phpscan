<?php
function test($a) {
	$a [0] = 1;
}
function test2(&$a) {
	$a [0] = 1;
}
$b = array (
		0 => 0 
);
test ( $b );
echo $b [0];
test2 ( $b );
echo $b [0];
?>