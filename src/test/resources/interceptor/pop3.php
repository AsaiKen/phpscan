<?php
class File {
	public function shutdown() {
		fclose ( $this->h ); // harmless
		$this->close ();
	}
	public function close() {
	}
}
class TempFile extends File {
	public function close() {
		include $this->filename; // !!
	}
}
class Database {
	public function __destruct() {
		$this->handle->shutdown ();
	}
}
$data = unserialize ( $_COOKIE [data] );

?>