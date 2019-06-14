<?php
class A {
	const TEST = 1;
	public function test() {
		// symtax error
		// const TEST = 1;
	}
}
const TEST = 1;

echo TEST;
echo A::TEST;
?>