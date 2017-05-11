<?php
$a = 1;
$aa = 'a';
list ( , ${$aa} ) = array (
		0,
		2
);
echo $a; // 2
?>