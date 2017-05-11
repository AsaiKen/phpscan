<?php
if (TRUE) {
	$a = "A";
	if (TRUE) {
		$a = "B";
	} else {
		$a = "C";
	}
} else {
	$a = "D";
	if (TRUE) {
		$a = "E";
	} else {
		$a = "F";
	}
}
echo $a;
?>