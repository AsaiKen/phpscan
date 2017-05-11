<?php
$a = "A";
if (TRUE) {
	if (TRUE) {
		$a = "B";
	}
} else {
	if (TRUE) {
		$a = "C";
	}
}
echo $a;
?>