<?php
$a = 1;
$b = 2;
$c = 3;
$aa = &$a;
$aa = &$b;
$aa = &$c;
echo $a; // 1
echo $b; // 2
echo $c; // 3
?>