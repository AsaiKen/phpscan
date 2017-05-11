<?php
spl_autoload_register ();

$a = new $_GET ["TEST"] ();
$a->test (); // 1256
$a::test2 (); // 3478
?>