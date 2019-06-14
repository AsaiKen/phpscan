<?php
echo $_FILES ['hoge'] ['name']; // VULN
echo $_FILES ['hoge'] ['type']; // VULN
echo $_FILES ['hoge'] ['tmp_name'];
echo $_FILES ['hoge'] ['error'];
echo $_FILES ['hoge'] ['size'];
echo $_FILES ['hoge'] ['hoge']; // VULN
?>