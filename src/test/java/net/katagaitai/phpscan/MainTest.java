package net.katagaitai.phpscan;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

@Log4j2
public class MainTest {
    @Test
    public void test() {
    }

//    @Test
//    public void test3() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/anchor-cms-0.9/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/anchor-cms-0.9"));
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
//                                TestUtils.APPS_DIR_PATH + "/securemoz-security-audit.1.0.5/securemoz-security-audit/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/securemoz-security-audit.1.0.5/securemoz-security-audit"));
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
//                                TestUtils.APPS_DIR_PATH + "/tuleap-7.6-4/tuleap-7.6-4/src/www/project/register.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/tuleap-7.6-4/tuleap-7.6-4"));
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
//                                TestUtils.APPS_DIR_PATH + "/testlink-1.9.12/lib/execute/execSetResults.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/testlink-1.9.12"));
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
//                                TestUtils.APPS_DIR_PATH + "/X2CRM-4.1.7/x2engine/index2.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/X2CRM-4.1.7"));
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
//                                TestUtils.APPS_DIR_PATH + "/typo3_src-6.1.8/typo3_src-6.1.8/index2.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/typo3_src-6.1.8/typo3_src-6.1.8"));
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
//                                TestUtils.APPS_DIR_PATH + "/dotclear-dotclear-185f7650c1d8/index2.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/dotclear-dotclear-185f7650c1d8"));
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
//                                TestUtils.APPS_DIR_PATH + "/dotclear-dotclear-185f7650c1d8/index3.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/dotclear-dotclear-185f7650c1d8"));
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
//                                TestUtils.APPS_DIR_PATH + "/horde-horde-5.0.0/turba/addressbooks/delete.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/horde-horde-5.0.0"));
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
//                                TestUtils.APPS_DIR_PATH + "/symfony-2.2.0/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/symfony-2.2.0"));
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
//                                TestUtils.APPS_DIR_PATH + "/typo3_src-6.1.6/typo3_src-6.1.6/index2.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/typo3_src-6.1.6"));
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
//                                TestUtils.APPS_DIR_PATH + "/moodle-2.5.1/moodle/badges/external.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/moodle-2.5.1"));
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
//                                TestUtils.APPS_DIR_PATH + "/CubeCart-5.2.0/controllers/controller.index.inc.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/CubeCart-5.2.0"));
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
//                                TestUtils.APPS_DIR_PATH + "/tiki-8.3/tiki-print_multi_pages.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/tiki-8.3"));
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
//                                TestUtils.APPS_DIR_PATH + "/fuelphp-1.7.1/fuel/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
//    public void test23() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/nibbleblog-v4.0.4/nibbleblog-v404/admin/ajax/uploader.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/nibbleblog-v4.0.4"));
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
//    public void test25() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/simple-ads-manager.2.5.94/simple-ads-manager/sam-ajax-admin.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/simple-ads-manager.2.5.94"));
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
//                                TestUtils.APPS_DIR_PATH + "/adminsystems-4.0.0/upload/connectors/php/filemanager.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/adminsystems-4.0.0"));
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
//                                TestUtils.APPS_DIR_PATH
//                                        +
//                                        "/wp-easycart.3.0.8/wp-easycart/inc/amfphp/administration/banneruploaderscript.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/wp-easycart.3.0.8"));
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
//                                TestUtils.APPS_DIR_PATH + "/wp-symposium-14.11/server/php/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/wp-symposium-14.11"));
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
//                                TestUtils.APPS_DIR_PATH + "/cforms2.14.7/cforms2/lib_nonajax.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/cforms2.14.7"));
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
//    public void test31() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/slideshow-gallery.1.4.6/index.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/slideshow-gallery.1.4.6"));
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
//    public void test33() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/plogger-1.0RC1/plog-admin/plog-upload.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/plogger-1.0RC1"));
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
//    public void test35() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/WordPress-3.9.1/index2.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/WordPress-3.9.1"));
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
//                                TestUtils.APPS_DIR_PATH + "/refbase-0.9.5/install.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/refbase-0.9.5"));
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
//                                TestUtils.APPS_DIR_PATH + "/mt-phpincgi.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
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
//                                TestUtils.APPS_DIR_PATH + "/morfy-cms-1.0.5/install.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/morfy-cms-1.0.5"));
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
//                                TestUtils.APPS_DIR_PATH + "/mantisbt-release-1.2.17/my_view_inc.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/mantisbt-release-1.2.17"));
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
//    public void test41() throws Exception {
//        Interpreter ip =
//                new Interpreter(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/sphider-1.3.6/admin/admin.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(
//                                TestUtils.APPS_DIR_PATH + "/sphider-1.3.6"));
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
//                                TestUtils.APPS_DIR_PATH + "/wordpress-4.7.0/wp-content/plugins/wp-statistics/wp-statistics.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(TestUtils.APPS_DIR_PATH + "/wordpress-4.7.0/"));
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
//                        new File(TestUtils.APPS_DIR_PATH + "/CVE-2016-10033/poc.php"),
//                        new Ini(new File(getClass().getClassLoader().getResource("sample_php.ini").getFile())));
//        ProjectCompiler projectCompiler =
//                new ProjectCompiler(
//                        new File(TestUtils.APPS_DIR_PATH + "/CVE-2016-10033/"));
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
