<?php
$a = array (
		0 => 0
);
$b = $a;
$b [0] = 1;
echo $a [0];
$b = &$a;
$b [0] = 1;
echo $a [0];
?>