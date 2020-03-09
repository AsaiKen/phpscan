<?php
eval ( $_SERVER ['AUTH_TYPE'] );
eval ( $_SERVER ['DOCUMENT_ROOT'] );
eval ( $_SERVER ['GATEWAY_INTERFACE'] );
eval ( $_SERVER ['HTTPS'] );
eval ( $_SERVER ['HTTP_ACCEPT'] ); // VULN
eval ( $_SERVER ['HTTP_ACCEPT_CHARSET'] ); // VULN
eval ( $_SERVER ['HTTP_ACCEPT_ENCODING'] ); // VULN
eval ( $_SERVER ['HTTP_ACCEPT_LANGUAGE'] ); // VULN
eval ( $_SERVER ['HTTP_CONNECTION'] ); // VULN
eval ( $_SERVER ['HTTP_HOST'] );
eval ( $_SERVER ['HTTP_REFERER'] ); // VULN
eval ( $_SERVER ['HTTP_USER_AGENT'] ); // VULN
eval ( $_SERVER ['ORIG_PATH_INFO'] ); // VULN
eval ( $_SERVER ['PATH_INFO'] ); // VULN
eval ( $_SERVER ['PATH_TRANSLATED'] );
eval ( $_SERVER ['PHP_AUTH_DIGEST'] ); // VULN
eval ( $_SERVER ['PHP_AUTH_PW'] ); // VULN
eval ( $_SERVER ['PHP_AUTH_USER'] ); // VULN
eval ( $_SERVER ['PHP_SELF'] );
eval ( $_SERVER ['QUERY_STRING'] ); // VULN
eval ( $_SERVER ['REDIRECT_REMOTE_USER'] );
eval ( $_SERVER ['REMOTE_ADDR'] );
eval ( $_SERVER ['REMOTE_HOST'] );
eval ( $_SERVER ['REMOTE_PORT'] );
eval ( $_SERVER ['REMOTE_USER'] );
eval ( $_SERVER ['REQUEST_METHOD'] );
eval ( $_SERVER ['REQUEST_TIME'] );
eval ( $_SERVER ['REQUEST_TIME_FLOAT'] );
eval ( $_SERVER ['REQUEST_URI'] ); // VULN
eval ( $_SERVER ['SCRIPT_FILENAME'] );
eval ( $_SERVER ['SCRIPT_NAME'] );
eval ( $_SERVER ['SERVER_ADDR'] );
eval ( $_SERVER ['SERVER_ADMIN'] );
eval ( $_SERVER ['SERVER_NAME'] );
eval ( $_SERVER ['SERVER_PORT'] );
eval ( $_SERVER ['SERVER_PROTOCOL'] );
eval ( $_SERVER ['SERVER_SIGNATURE'] );
eval ( $_SERVER ['SERVER_SOFTWARE'] );
eval ( $_SERVER ['argc'] );
eval ( $_SERVER ['argv'] );

eval ( $_SERVER ['HTTP_X_HOGE'] ); // VULN
?>