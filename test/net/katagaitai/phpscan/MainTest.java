package net.katagaitai.phpscan;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpProject;
import net.katagaitai.phpscan.compiler.ProjectCompiler;
import net.katagaitai.phpscan.interceptor.Debugger;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.interceptor.TimeKeeper;
import net.katagaitai.phpscan.interceptor.cms.WordPressInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.TaintUtils;

import org.ini4j.Ini;
import org.junit.Test;

import com.google.common.collect.Lists;

@Log4j2
public class MainTest {
    @Test
    public void test() throws Exception {
    }

//    private static final String mainDirPath = "C:/xampp_old2/htdocs/main";
//
//    @Test
//    public void test() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562/index.php"),
//                        new Ini(new File("C:/Users/askn/github/phpscan/sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/Joomla_3.4.5-Stable-Full_Package_CVE-2015-8562"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test2() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/piwik-2.14.0/piwik/misc/others/iframeWidget_localhost.php"),
//                        new Ini(new File("C:/Users/askn/github/phpscan/sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/piwik-2.14.0/piwik"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test3() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/anchor-cms-0.9/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/anchor-cms-0.9"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test4() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/securemoz-security-audit.1.0.5/securemoz-security-audit/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/securemoz-security-audit.1.0.5/securemoz-security-audit"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test5() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/Slim-2.5.0/Slim-2.5.0/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/Slim-2.5.0"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test6() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/eGroupware-1.8.005.20131007/egroupware/addressbook/csv_import.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/eGroupware-1.8.005.20131007/egroupware"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test7() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/tuleap-7.6-4/tuleap-7.6-4/src/www/project/register.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/tuleap-7.6-4/tuleap-7.6-4"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test8() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/testlink-1.9.12/lib/execute/execSetResults.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/testlink-1.9.12"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test9() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/X2CRM-4.1.7/x2engine/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/X2CRM-4.1.7"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test10() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/moodle-2.3.11/moodle/repository/repository_ajax.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/moodle-2.3.11/moodle"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test11() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/typo3_src-6.1.8/typo3_src-6.1.8/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/typo3_src-6.1.8/typo3_src-6.1.8"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test12() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/dotclear-dotclear-185f7650c1d8/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/dotclear-dotclear-185f7650c1d8"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test13() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/dotclear-dotclear-185f7650c1d8/index3.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/dotclear-dotclear-185f7650c1d8"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test14() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/horde-horde-5.0.0/turba/addressbooks/delete.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/horde-horde-5.0.0"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test15() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/symfony-2.2.0/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/symfony-2.2.0"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test16() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/glpi-0.83.8/glpi/front/ticket.form.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/glpi-0.83.8/glpi"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test17() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/typo3_src-6.1.6/typo3_src-6.1.6/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/typo3_src-6.1.6"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test18() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/moodle-2.5.1/moodle/badges/external.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/moodle-2.5.1"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test19() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/joomla-cms-3.0.2/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/joomla-cms-3.0.2"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test20() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/CubeCart-5.2.0/controllers/controller.index.inc.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/CubeCart-5.2.0"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test21() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/tiki-8.3/tiki-print_multi_pages.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/tiki-8.3"));
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
//    public void test22() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/fuelphp-1.7.1/fuel/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/fuelphp-1.7.1"));
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
//    public void test23() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/nibbleblog-v4.0.4/nibbleblog-v404/admin/ajax/uploader.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/nibbleblog-v4.0.4"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\file_put_contents");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test24() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/pivotx_2.3.10/pivotx/fileupload.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/pivotx_2.3.10"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.BP, "任意のファイルのオープン：\\fopen");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test25() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/simple-ads-manager.2.5.94/simple-ads-manager/sam-ajax-admin.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/simple-ads-manager.2.5.94"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test26() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/adminsystems-4.0.0/upload/connectors/php/filemanager.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/adminsystems-4.0.0"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test27() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath
//                                        +
//                                        "/wp-easycart.3.0.8/wp-easycart/inc/amfphp/administration/banneruploaderscript.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/wp-easycart.3.0.8"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test28() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/wp-symposium-14.11/server/php/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/wp-symposium-14.11"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test29() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/cforms2.14.7/cforms2/lib_nonajax.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/cforms2.14.7"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\rename");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test30() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/magmi_0.7.17a/magmi/web/magmi.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/magmi_0.7.17a"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.LFI, null);
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test31() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/slideshow-gallery.1.4.6/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/slideshow-gallery.1.4.6"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test32() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/phpwiki-1.5.0/lib/imagecache.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/phpwiki-1.5.0"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.RCE, "\\exec");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test33() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/plogger-1.0RC1/plog-admin/plog-upload.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/plogger-1.0RC1"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\move_uploaded_file");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test34() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/xrms-2006-04-26-v1.99.1/xrms/plugins/useradmin/fingeruser.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/xrms-2006-04-26-v1.99.1"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.RCE, "\\system");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test35() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/WordPress-3.9.1/index2.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/WordPress-3.9.1"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test36() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/refbase-0.9.5/install.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/refbase-0.9.5"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.RCE, "\\exec");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test37() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/mt-phpincgi.php"),
//                        new Ini(new File("sample_php.ini")));
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.LFI, null);
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test38() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/morfy-cms-1.0.5/install.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/morfy-cms-1.0.5"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\file_put_contents");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test39() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/mantisbt-release-1.2.17/my_view_inc.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/mantisbt-release-1.2.17"));
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
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test40() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/x7chat2_0_5_1/x7chat2/index.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/x7chat2_0_5_1"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.RCE, "\\preg_replace");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    @Test
//    public void test41() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/sphider-1.3.6/admin/admin.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                mainDirPath + "/sphider-1.3.6"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new Debugger(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.PFM, "\\fwrite");
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    // https://blog.sucuri.net/2017/06/sql-injection-vulnerability-wp-statistics.html
//    @Test
//    public void test42() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                mainDirPath + "/wordpress-4.7.0/wp-content/plugins/wp-statistics/wp-statistics.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(mainDirPath + "/wordpress-4.7.0/"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new WordPressInterceptor(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.SQLI, "\\add_shortcode");
//        ti.setKillOnFoung(false);
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
//
//    // https://legalhackers.com/advisories/PHPMailer-Exploit-Remote-Code-Exec-CVE-2016-10045-Vuln-Patch-Bypass.html
//    @Test
//    public void test43() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(mainDirPath + "/CVE-2016-10033/poc.php"),
//                        new Ini(new File("sample_php.ini")));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(mainDirPath + "/CVE-2016-10033/"));
//        PhpProject phpProject = projectCompiler.compile();
//        ip.setPhpProject(phpProject);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(baos);
//        System.setOut(out);
//        List<Interceptor> interceptorList = Lists.newArrayList();
//        interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
//        interceptorList.add(new TimeKeeper(ip, 3 * 60 * 1000));
//        TestInterceptor ti = new TestInterceptor(ip, VulnerabilityCategory.RCE, "\\mail");
//        ti.setKillOnFoung(false);
//        interceptorList.add(ti);
//        ip.execute(interceptorList);
//        log.info(new String(baos.toByteArray()));
//        assertThat(ti.isFound(), is(true));
//    }
}
