<?php

namespace {

	function not_called() {
		echo 1;
	}
	function not_called2($a) {
		echo 2;
	}
}

namespace test {

	function not_called() {
		echo 2;
	}
	function not_called2($a) {
		echo 2;
	}
}
?>