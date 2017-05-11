<?php
$ch = curl_init ( 'http://example.com/' . $_GET ['test'] );
unserialize ( curl_exec ( $ch ) );
?>