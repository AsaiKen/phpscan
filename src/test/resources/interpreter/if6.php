<?php
$a = "A";
if (TRUE) {
	if (TRUE) {
	} else {
		$a = "B";
	}
} else {
	if (TRUE) {
	} else {
		$a = "C";
	}
}
echo $a;
?>