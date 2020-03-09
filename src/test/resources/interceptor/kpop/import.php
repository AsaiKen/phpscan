<?php
include ('data.php');
if (isset ( $_POST ['data'] )) {
	$newperms = array ();
	$data = unserialize ( base64_decode ( $_POST ['data'] ) );
	foreach ( $data as $d ) {
		if (is_int ( $d ))
			$newperms [] = $d;
	}
	User::addLyrics ( $newperms );
	?>
<html>
<head>
<title>The Plague's KPop Fan Page - Imported Songs</title>
</head>
<body>
	<p>
		Your songs have been imported! Go back to the <a href="songs.php">songs</a>
		page to see them!
	</p>
</body>
</html>
<?php
} else {
	?>
<html>
<head>
<title>The Plague's KPop Fan Page - Import Songs</title>
</head>
<body>
	<p>Have songs to import? Put the exported data into the field below!</p>
	<p>


	<form name="input" action="import.php" method="post">
		Data to import: <input type="text" name="data"><br />
	</form>
	</p>
</body>
</html>
<?php } ?>
