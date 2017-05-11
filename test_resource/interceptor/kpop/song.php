<?php
include ('data.php');
$name = $_GET ['name'];
?>
<html>
<head>
<title>The Plague's KPop Fan Page - Your Song</title>
</head>
<body>
	<p>
		Your song should be below, if you have it. To go back to your list of
		songs, go <a href="songs.php">here</a>.
	</p>
<?php
$lyrics = $db->getLyrics ( User::getLyrics () );
foreach ( $lyrics as $obj ) {
	if ($obj->name_is ( $name )) {
		echo $obj;
	}
}
?>
  </body>
</html>
