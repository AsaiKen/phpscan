package net.katagaitai.phpscan.compiler;

import net.katagaitai.phpscan.TestUtils;
import org.junit.Test;

public class ProjectCompilerTest {
    @Test
    public void test() throws Exception {
        ProjectCompiler compiler = new ProjectCompiler(
                TestUtils.getFile("projectcompiler/case1"));
        PhpProject phpProject = compiler.compile();
        //		assertThat(phpProject.getFunctionMap().size(), is(2));
        //		assertThat(phpProject.getClassMap().size(), is(2));
        //		assertThat(phpProject.getMethodMap().size(), is(2));
        //		assertThat(phpProject.getStaticMethodMap().size(), is(2));
    }

    @Test
    public void test2() throws Exception {
        ProjectCompiler compiler = new ProjectCompiler(
                TestUtils.getFile("projectcompiler/case2"));
        PhpProject phpProject = compiler.compile();
        //		assertThat(phpProject.getConstantMap().size(), is(3));
        //		assertThat(phpProject.getConstantMap().keySet(), is(
        //				Sets.newHashSet("\\A", "\\B", "\\C::D")));
    }
}
