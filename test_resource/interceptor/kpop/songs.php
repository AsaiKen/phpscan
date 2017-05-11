<?php
include ('data.php');
?>
<html>
<head>
<title>The Plague's KPop Fan Page - Your Songs</title>
</head>
<body>
	<p>
		Your songs should be listed below. In order to avoid claims of
		copyright infringement, your songs are stored locally. If you would
		like to import songs, please go <a href="import.php">here</a>. If you
		would like to add your own song, please go <a href="add_song.php">here</a>.
		If you would like to export your songs so that others may import them,
		please go <a href="export.php">here</a>. Currently, we have no way to
		delete songs. Sorry!
	</p>
	<p></p>
<?php
$lyrics = $db->getLyrics ( User::getLyrics () );
foreach ( $lyrics as $obj ) {
	echo $obj->shortForm ();
}
?>
  </body>
</html>
