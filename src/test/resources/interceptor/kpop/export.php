<?php
include ('data.php');
$exported = Porter::exportData ( User::getLyrics () );
?>
<html>
<head>
<title>The Plague's KPop Fan Page - Export Songs</title>
</head>
<body>
	<p>
		The text below are your exported songs. Just have your friend
		copy-paste said text into the <a href="import.php">import page</a>!
	</p>
	<p><?php echo "<pre>" . $exported . "</pre>" ?></p>
</body>
</html>
