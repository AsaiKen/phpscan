<?php
function test() {
	if (true) {
		return "1";
	}
	return true ? "2" : "3";
}

echo test ();
?>