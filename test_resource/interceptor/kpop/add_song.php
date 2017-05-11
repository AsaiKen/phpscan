<?php
include ('data.php');
if (isset ( $_POST ['name'] )) {
	$name = $_POST ['name'];
	$group = $_POST ['group'];
	$url = $_POST ['url'];
	$lyrics = $_POST ['lyrics'];
	$song = new Song ( $name, $group, $url );
	$lyricO = new Lyrics ( $lyrics, $song );
	$res = $db->addLyrics ( array (
			$lyricO
	) );
	User::addLyrics ( $res );
	?>
<html>
<head>
<title>The Plague's KPop Fan Page - Added Song</title>
</head>
<body>
	<p>
		Your songs has been added! Go back to the <a href="songs.php">songs</a>
		page to see it!
	</p>
</body>
</html>
<?php
} else {
	?>
<html>
<head>
<title>The Plague's KPop Fan Page - Add Song</title>
</head>
<body>
	<p>Want to add a song? Just put the data below!</p>
	<p>


	<form name="input" action="add_song.php" method="post">
		Song name: <input type="text" name="name" /><br /> Group: <input
			type="text" name="group" /><br /> Video URL: <input type="text"
			name="url" /><br /> Lyrics:
		<textarea name="lyrics" rows="40" cols="50"></textarea>
		<br /> <input type="submit" value="Submit">
	</form>
	</p>
</body>
</html>
<?php } ?>
