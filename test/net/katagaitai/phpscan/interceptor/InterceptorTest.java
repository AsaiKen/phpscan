package net.katagaitai.phpscan.interceptor;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.TestUtils;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.compiler.PhpProject;
import net.katagaitai.phpscan.compiler.ProjectCompiler;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.TaintUtils;

import org.hamcrest.Matchers;
import org.ini4j.Ini;
import org.junit.Test;

import com.google.common.collect.Lists;

@Log4j2
public class InterceptorTest {
	@Test
	public void test2() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/eval.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test3() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/eval2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test4() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/eval3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test5() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/reflection.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test6() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/if.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test7() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/reflection2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test8() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/get_any_field.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12345"));
	}

	//	@Test
	//	public void test9() throws Exception {
	//		Interpreter ip = new Interpreter(
	//				new File("test_resource/interceptor/get_any_field2.php"),
	//				new Ini(new File("sample_php.ini")));
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		PrintStream out = new PrintStream(baos);
	//		System.setOut(out);
	//		List<Interceptor> interceptorList = Lists.newArrayList();
	//		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
	//		interceptorList.add(new Debugger(ip));
	//		ip.execute(interceptorList);
	//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	//	}

	@Test
	public void test10() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/get_any_value.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12345"));
	}

	//	@Test
	//	public void test11() throws Exception {
	//		Interpreter ip = new Interpreter(
	//				new File("test_resource/interceptor/get_any_value2.php"),
	//				new Ini(new File("sample_php.ini")));
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		PrintStream out = new PrintStream(baos);
	//		System.setOut(out);
	//		List<Interceptor> interceptorList = Lists.newArrayList();
	//		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
	//		interceptorList.add(new Debugger(ip));
	//		ip.execute(interceptorList);
	//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	//	}

	@Test
	public void test12() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/pop.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test13() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/pop2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test14() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/pop3.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test15() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/kpop/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/kpop/import.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test16() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case16/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case16/new_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("12345678"));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	}

	@Test
	public void test17() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case17/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case17/new_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(new String(baos.toByteArray()), is(""));
	}

	//	@Test
	//	public void test18() throws Exception {
	//		PhpProject phpProject =
	//				new ProjectCompiler(
	//						new File("test_resource/interceptor/case18/"))
	//						.compile();
	//		Interpreter ip = new Interpreter(
	//				new File("test_resource/interceptor/case18/unserialize_with_classloader.php"),
	//				new Ini(new File("sample_php.ini")));
	//		ip.setPhpProject(phpProject);
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		PrintStream out = new PrintStream(baos);
	//		System.setOut(out);
	//		List<Interceptor> interceptorList = Lists.newArrayList();
	//		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
	//		interceptorList.add(new Debugger(ip));
	//		ip.execute(interceptorList);
	//		assertThat(new String(baos.toByteArray()), is("12"));
	//	}

	@Test
	public void test19() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case19/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case19/unserialize_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(new String(baos.toByteArray()), is(""));
	}

	@Test
	public void test20() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case20/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case20/new_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("1234"));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	}

	@Test
	public void test21() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case21/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case21/callstatic_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		//		assertThat(TestUtils.sort(new String(baos.toByteArray())), is("3478"));
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	}

	@Test
	public void test22() throws Exception {
		PhpProject phpProject =
				new ProjectCompiler(
						new File("test_resource/interceptor/case22/"))
						.compile();
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/case22/callstatic_with_classloader.php"),
				new Ini(new File("sample_php.ini")));
		ip.setPhpProject(phpProject);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(TestUtils.sort(new String(baos.toByteArray())), is(""));
	}

	@Test
	public void test23() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/session_decode.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test24() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/call_user_func.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test25() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/call_user_func_array.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test26() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/call_user_method.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test27() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/call_user_method_array.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test28() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/foward_static_call.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test29() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/foward_static_call_array.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test30() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/reflectionfunction.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test31() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/fopen.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	//	@Test
	//	public void test32() throws Exception {
	//		Interpreter ip = new Interpreter(
	//				new File("test_resource/interceptor/mysql_fetch_row.php"),
	//				new Ini(new File("sample_php.ini")));
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		PrintStream out = new PrintStream(baos);
	//		System.setOut(out);
	//		List<Interceptor> interceptorList = Lists.newArrayList();
	//		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
	//		interceptorList.add(new Debugger(ip));
	//		ip.execute(interceptorList);
	//		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	//	}

	@Test
	public void test33() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/file_get_contents.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test34() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/file_put_contents.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test35() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/file_put_contents2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test36() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/curl.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test37() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/if2.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test38() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/array_string.php"),
				new Ini(new File("sample_php.ini")));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
	}

	@Test
	public void test39() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/merge_sink.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), Matchers.greaterThan(0));
		for (Vulnerability vulnerability : ip.getVulnerabilityList()) {
			System.out.println(vulnerability);
			assertThat(vulnerability.getPositionList().size(), is(2));
		}
	}

	@Test
	public void test40() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/arbitrary_function_with_prefix.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(0));
	}

	@Test
	public void test41() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/arbitrary_function_with_suffix.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(0));
	}

	@Test
	public void test42() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/arbitrary_function.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test43() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/curl_with_prefix.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(0));
	}

	@Test
	public void test44() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/xss1.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test45() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/xss2.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test46() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/xss3.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test47() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/base64.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(0));
	}

	@Test
	public void test48() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/http_build_query.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(0));
	}

	@Test
	public void test49() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/files_xss.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(3));
	}

	@Test
	public void test50() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/files_lfi.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(3));
	}

	@Test
	public void test51() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/server_xss.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(6));
	}

	@Test
	public void test52() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/server_rce.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(15));
	}

	@Test
	public void test53() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/yaml_parse.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test54() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/yaml_parse_file.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test55() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/yaml_parse_url.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
	}

	@Test
	public void test56() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/php_path_manipulation.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PFM));
	}

	@Test
	public void test57() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/php_path_manipulation2.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PFM));
	}

	@Test
	public void test58() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/php_path_manipulation3.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PFM));
	}

	@Test
	public void test59() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/php_path_manipulation4.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PFM));
	}

	@Test
	public void test60() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/path_manipulation.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PM));
	}

	@Test
	public void test61() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/path_manipulation3.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PM));
	}

	@Test
	public void test62() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/path_manipulation4.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.PM));
	}

	@Test
	public void test63() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/var_dump_array.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XSS));
	}

	@Test
	public void test64() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/var_dump_array2.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(2));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XSS));
		assertThat(ip.getVulnerabilityList().get(1).getCategory(), is(VulnerabilityCategory.XSS));
	}

	@Test
	public void test65() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/var_dump_object.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XSS));
	}

	@Test
	public void test66() throws Exception {
		Interpreter ip = new Interpreter(
				new File("test_resource/interceptor/var_dump_object2.php"),
				new Ini(new File("sample_php.ini")));
		List<Interceptor> interceptorList = Lists.newArrayList();
		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
		interceptorList.add(new Debugger(ip));
		ip.execute(interceptorList);
		// TODO オブジェクトのキー側をテイントとして検知できない
		//		assertThat(ip.getVulnerabilityList().size(), is(2));
		//		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XSS));
		//		assertThat(ip.getVulnerabilityList().get(1).getCategory(), is(VulnerabilityCategory.XSS));
		assertThat(ip.getVulnerabilityList().size(), is(1));
		assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XSS));
	}
}
