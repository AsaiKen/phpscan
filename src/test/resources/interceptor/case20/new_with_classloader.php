<?php
spl_autoload_register ();
$a = new $_GET ["TEST"] ();
$a->test (); // 12
$a::test (); // 34
?>