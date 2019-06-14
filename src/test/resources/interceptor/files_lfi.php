<?php
include $_FILES ['hoge'] ['name']; // maybe VULN
include $_FILES ['hoge'] ['type']; // VULN
include $_FILES ['hoge'] ['tmp_name'];
include $_FILES ['hoge'] ['error'];
include $_FILES ['hoge'] ['size'];
include $_FILES ['hoge'] ['hoge']; // VULN
?>