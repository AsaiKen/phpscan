package net.katagaitai.phpscan.compiler;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;

import org.junit.Test;

public class CompilerTest {
	@Test
	public void test2() throws Exception {
		Compiler compiler = new Compiler(new File("test_resource/parser/variable2.php"));
		PhpFunction phpFunction = compiler.compile();
		System.out.println(phpFunction.toString());
		// 例外なしならOK
		assert true;
	}

	@Test
	public void test3() throws Exception {
		Compiler compiler = new Compiler(new File("test_resource/compiler/static_variable.php"));
		PhpFunction phpFunction = compiler.compile();
		System.out.println(phpFunction.toString());
		// 例外なしならOK
		assert true;
	}

	@Test
	public void test4() throws Exception {
		Compiler compiler = new Compiler(new File("test_resource/compiler/reflection.php"));
		PhpFunction phpFunction = compiler.compile();
		assertThat(phpFunction.toString(), not(containsString("$$")));
	}

	@Test
	public void test5() throws Exception {
		Compiler compiler = new Compiler(new File("test_resource/interpreter/case47/callfunction_unknown2.php.inc"));
		PhpFunction phpFunction = compiler.compile();
		System.out.println(phpFunction.toString());
		// 例外なしならOK
		assert true;
	}

	@Test
	public void test6() throws Exception {
		Compiler compiler = new Compiler(new File("test_resource/compiler/arrayaccess.php"));
		PhpFunction phpFunction = compiler.compile();
		System.out.println(phpFunction.toString());
		// 例外なしならOK
		assert true;
	}

}
