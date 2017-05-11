<?php
$a = 1;
$b = &$a;
$b += 1;
echo $a;
?>