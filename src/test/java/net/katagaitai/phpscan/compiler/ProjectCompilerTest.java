package net.katagaitai.phpscan.compiler;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProjectCompilerTest {
    @Test
    public void test() throws Exception {
        ProjectCompiler compiler = new ProjectCompiler(
                new File(getClass().getClassLoader().getResource("projectcompiler/case1").getFile()));
        PhpProject phpProject = compiler.compile();
        assertThat(phpProject.getFunctionMap().size(), is(2));
        assertThat(phpProject.getClassMap().size(), is(2));
        assertThat(phpProject.getMethodMap().size(), is(2));
        assertThat(phpProject.getStaticMethodMap().size(), is(2));
    }

    @Test
    public void test2() throws Exception {
        ProjectCompiler compiler = new ProjectCompiler(
                new File(getClass().getClassLoader().getResource("projectcompiler/case2").getFile()));
        PhpProject phpProject = compiler.compile();
        assertThat(phpProject.getConstantMap().size(), is(3));
        assertThat(phpProject.getConstantMap().keySet(), is(
                Sets.newHashSet("\\A", "\\B", "\\C::D")));
    }
}
