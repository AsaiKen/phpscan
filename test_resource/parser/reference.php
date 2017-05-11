<?php
function &test($a, &$b) {
	return array ();
}

$a = &test(1,2);

?>