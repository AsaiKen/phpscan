package net.katagaitai.phpscan.interpreter;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;

import net.katagaitai.phpscan.TestUtils;
import net.katagaitai.phpscan.compiler.PhpProject;
import net.katagaitai.phpscan.compiler.ProjectCompiler;
import net.katagaitai.phpscan.interceptor.Debugger;

import org.ini4j.Ini;
import org.junit.Test;

import com.google.common.collect.Lists;

public class InterpreterTest {
	@Test
	public void test() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/method.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("ABABA"));
	}

	@Test
	public void test2() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/static_method.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("ABBCA"));
	}

	@Test
	public void test3() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("AB"));
	}

	@Test
	public void test4() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("ABCD"));
	}

	@Test
	public void test5() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("BCEF"));
	}

	@Test
	public void test6() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if4.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("AD"));
	}

	@Test
	public void test7() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if5.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("ABC"));
	}

	@Test
	public void test8() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/if6.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("ABC"));
	}

	@Test
	public void test9() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reference.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("2"));
	}

	@Test
	public void test10() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reference2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("3"));
	}

	@Test
	public void test11() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/dynamic_field.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("111"));
	}

	@Test
	public void test12() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/destruct.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), containsString("In constructor"));
		assertThat(new String(baos.toByteArray()), containsString("Destroying MyDestructableClass"));
	}

	@Test
	public void test13() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/argument_reference.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("01"));
	}

	@Test
	public void test14() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/assign_reference.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("01"));
	}

	@Test
	public void test15() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/assign_reference2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("11"));
	}

	@Test
	public void test16() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/constant.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1111"));
	}

	@Test
	public void test17() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/static_field.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("BBB"));
	}

	@Test
	public void test18() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/foreach.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12345"));
	}

	@Test
	public void test19() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/constant_function.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("111111"));
	}

	@Test
	public void test20() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/constant2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test21() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/constant3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test22() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/create_function.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("11"));
	}

	@Test
	public void test23() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("101"));
	}

	@Test
	public void test24() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("11"));
	}

	@Test
	public void test25() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("111"));
	}

	@Test
	public void test26() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func4.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test27() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func_array.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test28() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/call_user_func5.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test29() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/dirname.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is(Paths.get("..").toFile().getCanonicalPath()));
	}

	@Test
	public void test30() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/global.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test33() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/arrayaccess.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1212"));
	}

	@Test
	public void test34() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/standard_defines.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("/"));
	}

	@Test
	public void test35() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/constant4.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("342"));
	}

	@Test
	public void test36() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/static_variable.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1212"));
	}

	@Test
	public void test37() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/compiler/static_variable.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("11"));
	}

	@Test
	public void test38() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reflection.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("2"));
	}

	@Test
	public void test39() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reflection2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("2"));
	}

	@Test
	public void test40() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/switch.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1234"));
	}

	@Test
	public void test41() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reference3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("123"));
	}

	@Test
	public void test42() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reference4.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test43() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/reference5.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test44() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/lowerupper.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test46() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case46/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case46/callfunction_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test47() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case47/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case47/callfunction_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is(""));
	}

	@Test
	public void test45() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case45/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case45/callmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test48() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case48/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case48/callmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is(""));
	}

	@Test
	public void test49() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case49/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case49/callmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("34"));
	}

	@Test
	public void test50() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case50/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case50/callmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is(""));
	}

	@Test
	public void test51() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case51/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case51/callstatucmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test52() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case52/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case52/callstatucmethod_unknown.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is(""));
	}

	@Test
	public void test57() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case57/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case57/magic_method.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("12"));
	}

	@Test
	public void test58() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interpreter/case58/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/case58/magic_method.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("34"));
	}

	@Test
	public void test59() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/deeparray.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(new String(baos.toByteArray()), is("1"));
	}

	@Test
	public void test60() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/inline_branch.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("123"));
	}

	@Test
	public void test61() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/inline_and_or.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12"));
	}

	@Test
	public void test62() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/inline_and_or2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12"));
	}

	@Test
	public void test63() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/inline_and_or3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12"));
	}

	@Test
	public void test64() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/inline_and_or4.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12"));
	}

	@Test
	public void test65() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/equal_equal.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("111"));
	}

	@Test
	public void test66() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interpreter/undefined_value.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		ip.execute(Lists.newArrayList(new Debugger(ip)));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("012"));
	}

	//	@Test
	//	public void test67() throws Exception {
	//		Interpreter ip = new Interpreter(
	//				new File("test_resource/interpreter/not_called_root_function.php"),
	//				new Ini(new File("sample_php.ini")));
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		PrintStream out = new PrintStream(baos);
	//		System.setOut(out);
	//		ip.execute(Lists.newArrayList(new Debugger(ip)));
	//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("1"));
	//	}

}
