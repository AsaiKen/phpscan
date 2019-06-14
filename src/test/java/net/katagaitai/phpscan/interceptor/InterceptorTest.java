package net.katagaitai.phpscan.interceptor;

import com.google.common.collect.Lists;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j2
public class InterceptorTest {
    @Test
    public void test2() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/eval.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/eval2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/eval3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/reflection.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/if.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/reflection2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/get_any_field.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
    //				new File(getClass().getClassLoader().getResource("interceptor/get_any_field2.php").getFile()),
    //				new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/get_any_value.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
    //				new File(getClass().getClassLoader().getResource("interceptor/get_any_value2.php").getFile()),
    //				new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/pop.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/pop2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/pop3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/kpop/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/kpop/import.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case16/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case16/new_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case17/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case17/new_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
    //						new File(getClass().getClassLoader().getResource("interceptor/case18/").getFile()))
    //						.compile();
    //		Interpreter ip = new Interpreter(
    //				new File(getClass().getClassLoader().getResource("interceptor/case18/unserialize_with_classloader.php").getFile()),
    //				new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case19/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case19/unserialize_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case20/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case20/new_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case21/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case21/callstatic_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                        new File(getClass().getClassLoader().getResource("interceptor/case22/").getFile()))
                        .compile();
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/case22/callstatic_with_classloader.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/session_decode.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/call_user_func.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/call_user_func_array.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/call_user_method.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/call_user_method_array.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/foward_static_call.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/foward_static_call_array.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/reflectionfunction.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/fopen.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
    //				new File(getClass().getClassLoader().getResource("interceptor/mysql_fetch_row.php").getFile()),
    //				new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/file_get_contents.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/file_put_contents.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/file_put_contents2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/curl.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/if2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/array_string.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/merge_sink.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/arbitrary_function_with_prefix.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test41() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/arbitrary_function_with_suffix.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test42() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/arbitrary_function.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test43() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/curl_with_prefix.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test44() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/xss1.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test45() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/xss2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test46() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/xss3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test47() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/base64.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test48() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/http_build_query.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test49() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/files_xss.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(3));
    }

    @Test
    public void test50() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/files_lfi.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(3));
    }

    @Test
    public void test51() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/server_xss.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(6));
    }

    @Test
    public void test52() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/server_rce.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(15));
    }

    @Test
    public void test53() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/yaml_parse.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test54() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/yaml_parse_file.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test55() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/yaml_parse_url.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
    }

    @Test
    public void test56() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/php_path_manipulation.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/php_path_manipulation2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/php_path_manipulation3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/php_path_manipulation4.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/path_manipulation.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/path_manipulation3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/path_manipulation4.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/var_dump_array.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/var_dump_array2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/var_dump_object.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
                new File(getClass().getClassLoader().getResource("interceptor/var_dump_object2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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

    @Test
    public void test67() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/xxe1.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XXE));
    }

    @Test
    public void test68() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/xxe2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.XXE));
    }

    @Test
    public void test69() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.RCE));
    }

    @Test
    public void test70() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test71() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/reflection3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(2));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.RCE));
        assertThat(ip.getVulnerabilityList().get(1).getCategory(), is(VulnerabilityCategory.RCE));
    }

    @Test
    public void test72() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.RCE));
    }

    @Test
    public void test73() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options2.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test74() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options3.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(1));
        assertThat(ip.getVulnerabilityList().get(0).getCategory(), is(VulnerabilityCategory.RCE));
    }

    @Test
    public void test75() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options4.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

    @Test
    public void test76() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options5.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(4));
    }

    @Test
    public void test77() throws Exception {
        Interpreter ip = new Interpreter(
                new File(getClass().getClassLoader().getResource("interceptor/assert_options6.php").getFile()),
                new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
        List<Interceptor> interceptorList = Lists.newArrayList();
        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
        interceptorList.add(new Debugger(ip));
        ip.execute(interceptorList);
        assertThat(ip.getVulnerabilityList().size(), is(0));
    }

}
