<?php
$a = $_GET ['a'];
switch ($a) {
	case 1 :
		$b = 1;
		break;
	case 2 :
		$b = 2;
		break;
	case 3 :
		$b = 3;
		break;
	default :
		$b = 4;
}

echo $b;

?>