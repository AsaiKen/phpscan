<?php

namespace A {

	$newfunc = create_function ( '$a', 'return $a;' );
	echo $newfunc ( 1 ); // 1
}

namespace {

	print $newfunc ( 1 ); // 1
}
?>