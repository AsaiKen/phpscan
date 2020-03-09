<?php
$ch = curl_init ( $_GET ['test'] );
unserialize ( curl_exec ( $ch ) );
?>