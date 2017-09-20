<?php
// 挙動を見る限り、assert_optionsのvalueは以下の仕様。
// 1. 文字列に変換
// 2. intに変換、
// 3. その数値が0以外ならenable
assert_options ( ASSERT_ACTIVE, - 1 );
assert ( $_GET ['p'] ); // vuln

assert_options ( ASSERT_ACTIVE, "-1" );
assert ( $_GET ['p'] ); // vuln

assert_options ( ASSERT_ACTIVE, 1e-100 );
assert ( $_GET ['p'] ); // vuln

assert_options ( ASSERT_ACTIVE, "1e-100" );
assert ( $_GET ['p'] ); // vuln

?>