package net.katagaitai.phpscan.compiler;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompilerTest {
    @Test
    public void test2() throws Exception {
        Compiler compiler = new Compiler(new File(getClass().getClassLoader().getResource("parser/variable2.php").getFile()));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test3() throws Exception {
        Compiler compiler = new Compiler(new File(getClass().getClassLoader().getResource("compiler/static_variable.php").getFile()));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test4() throws Exception {
        Compiler compiler = new Compiler(new File(getClass().getClassLoader().getResource("compiler/reflection.php").getFile()));
        PhpFunction phpFunction = compiler.compile();
        assertThat(phpFunction.toString(), not(containsString("$$")));
    }

    @Test
    public void test5() throws Exception {
        Compiler compiler = new Compiler(new File(getClass().getClassLoader().getResource("interpreter/case47/callfunction_unknown2.php.inc").getFile()));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test6() throws Exception {
        Compiler compiler = new Compiler(new File(getClass().getClassLoader().getResource("compiler/arrayaccess.php").getFile()));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

}
