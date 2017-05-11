<?php
$a = 1;
$b = &$a;
$b += 1;
$b = 3;
echo $a;
?>