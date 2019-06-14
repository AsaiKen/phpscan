package net.katagaitai.phpscan.compiler;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.TestInterceptor;
import net.katagaitai.phpscan.TestUtils;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.interceptor.Debugger;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.interceptor.TimeKeeper;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.TaintUtils;
import org.ini4j.Ini;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Log4j2
public class FrameworkTestCaseCompilerTest {
    @Test
    public void test() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                new File(getClass().getClassLoader().getResource("frameworkcompiler/case1").getFile()), "case1");
        compiler.compile();
    }

    @Test
    public void test2() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                new File(getClass().getClassLoader().getResource("frameworkcompiler/case2").getFile()), "case2");
        compiler.compile();
    }

    @Test
    public void test3() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                new File(getClass().getClassLoader().getResource("frameworkcompiler/case3").getFile()), "case3");
        compiler.compile();
    }

    @Test
    public void test4() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                new File(getClass().getClassLoader().getResource("frameworkcompiler/case4").getFile()), "case4");
        compiler.compile();
    }

    @Test
    public void test5() throws Exception {
        FrameworkTestCaseCompiler compiler = new FrameworkTestCaseCompiler(
                new File(getClass().getClassLoader().getResource("frameworkcompiler/case5").getFile()), "case5");
        compiler.compile();
    }

//    @Test
//    public void testFuel171() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/fuelphp-1.7.1"),
//                        "fuelphp-1.7.1");
//        compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\fuel\\\\core\\\\classes\\\\.*"));
//        compiler.compile();
//    }
//
//    @Test
//    public void testFuel171_1() throws Exception {
//        Interpreter ip = new Interpreter(
//                new File(
//                        TestUtils.APPS_DIR_PATH + "/fuelphp-1.7.1/_Fuel_Core_Request_Curl.php"),
//                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/fuelphp-1.7.1"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.OI, "\\unserialize");
//        //		ti.setKillOnFoung(false);
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void testFuel180() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/fuelphp-1.8"),
//                        "fuelphp-1.8");
//        compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\fuel\\\\core\\\\classes\\\\.*"));
//        compiler.compile();
//    }
//
//    @Test
//    public void testHorde530() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/horde530/framework"),
//                        "horde530");
//        compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\lib\\\\Horde\\\\.*"));
//        compiler.compile();
//    }
//
//    @Test
//    public void testCakephp329() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                //								"Z:/Downloads/archive/mac/famous_framework/cakephp-3-2-9/src"
//                                TestUtils.APPS_DIR_PATH + "/cakephp-3-2-9/vendor/cakephp"
//                        ),
//                        "cakephp329");
//        compiler.setIncludeRegexList(Lists.newArrayList(".*\\\\src\\\\.*"));
//        compiler.compile();
//    }
//
//    @Test
//    public void testSymfony306() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/symfony-3.0.6/src/Symfony"
//                        ),
//                        "symfony306");
//        compiler.setExcludeRegexList(Lists.newArrayList(".*\\\\Test\\\\.*", ".*\\\\Tests\\\\.*"));
//        compiler.compile();
//    }
//
//    @Test
//    public void testZend249() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/ZendFramework-2.4.9/library"
//                        ),
//                        "zend249");
//        compiler.compile();
//    }
//
//    @Test
//    public void testCodeIgniter306() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/CodeIgniter-3.0.6"
//                        ),
//                        "CodeIgniter306");
//        compiler.compile();
//    }
//
//    @Test
//    public void testLaravel5234() throws Exception {
//        FrameworkTestCaseCompiler compiler =
//                new FrameworkTestCaseCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/framework-5.2.34"
//                        ),
//                        "Laravel5234");
//        compiler.compile();
//    }
}
