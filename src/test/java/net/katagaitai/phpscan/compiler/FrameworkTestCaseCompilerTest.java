package net.katagaitai.phpscan.compiler;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.TestUtils;
import org.junit.Test;

@Log4j2
public class FrameworkTestCaseCompilerTest {
    @Test
    public void test() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                TestUtils.getFile("frameworkcompiler/case1"), "case1");
        compiler.compile();
    }

    @Test
    public void test2() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                TestUtils.getFile("frameworkcompiler/case2"), "case2");
        compiler.compile();
    }

    @Test
    public void test3() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                TestUtils.getFile("frameworkcompiler/case3"), "case3");
        compiler.compile();
    }

    @Test
    public void test4() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                TestUtils.getFile("frameworkcompiler/case4"), "case4");
        compiler.compile();
    }

    @Test
    public void test5() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                TestUtils.getFile("frameworkcompiler/case5"), "case5");
        compiler.compile();
    }

//	@Test
//	public void testFuel171() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"C:/xampp/htdocs/main/fuelphp-1.7.1"),
//						"fuelphp-1.7.1");
//		compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\fuel\\\\core\\\\classes\\\\.*"));
//		compiler.compile();
//	}
//
//	@Test
//	public void testFuel171_1() throws Exception {
//		Interpreter ip = new Interpreter(
//				new File("C:/Users/askn/github/phpscan/tmp/framework_testcase/fuelphp-1.7.1/_Fuel_Core_Request_Curl.php"),
//				new Ini(new File("sample_php.ini")));
//		ProjectCompiler projectCompiler =
//				new ProjectCompiler(
//						new File(
//								"C:/xampp/htdocs/main/fuelphp-1.7.1"));
//		PhpProject phpProject = projectCompiler.compile();
//		ip.setPhpProject(phpProject);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		PrintStream out = new PrintStream(baos);
//		System.setOut(out);
//		List<Interceptor> interceptorList = Lists.newArrayList();
//		interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//		interceptorList.add(new Debugger(ip));
//		interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//		TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.OI, "\\unserialize");
//		//		ti.setKillOnFoung(false);
//		interceptorList.add(ti);
//		ip.execute(interceptorList);
//		log.info(new String(baos.toByteArray()));
//		assertThat(ti.isFound(), is(true));
//	}
//
//	@Test
//	public void testFuel180() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"\\\\vmware-host\\Shared Folders\\Downloads\\archive\\mac\\famous_framework\\fuelphp-1.8"),
//						"fuelphp-1.8");
//		compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\fuel\\\\core\\\\classes\\\\.*"));
//		compiler.compile();
//	}
//
//	@Test
//	public void testHorde530() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"Z:/Downloads/archive/mac/famous_framework/horde-master/framework"),
//						"horde530");
//		compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\lib\\\\Horde\\\\.*"));
//		compiler.compile();
//	}
//
//	@Test
//	public void testCakephp329() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								//								"Z:/Downloads/archive/mac/famous_framework/cakephp-3-2-9/src"
//								"Z:/Downloads/archive/mac/famous_framework/cakephp-3-2-9/vendor/cakephp"
//						),
//						"cakephp329");
//		compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\src\\\\.*"));
//		compiler.compile();
//	}
//
//	@Test
//	public void testSymfony306() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"Z:/Downloads/archive/mac/famous_framework/symfony-3.0.6/src/Symfony"
//						),
//						"symfony306");
//		compiler.setExcludeRegexList(Lists.newArrayList(".*\\\\Test\\\\.*", ".*\\\\Tests\\\\.*"));
//		compiler.compile();
//	}
//
//	@Test
//	public void testZend249() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"Z:/Downloads/archive/mac/famous_framework/ZendFramework-2.4.9/library"
//						),
//						"zend249");
//		compiler.compile();
//	}
//
//	@Test
//	public void testCodeIgniter306() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"Z:/Downloads/archive/mac/famous_framework/CodeIgniter-3.0.6"
//						),
//						"CodeIgniter306");
//		compiler.compile();
//	}
//
//	@Test
//	public void testLaravel5234() throws Exception {
//		FrameworkTestCaseCompiler compiler =
//				new FrameworkTestCaseCompiler(
//						new File(
//								"Z:/Downloads/archive/mac/famous_framework/framework-5.2.34"
//						),
//						"Laravel5234");
//		compiler.compile();
//	}
}
