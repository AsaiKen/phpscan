package net.katagaitai.phpscan.compiler;

import net.katagaitai.phpscan.TestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class CompilerTest {
    @Test
    public void test2() throws Exception {
        Compiler compiler = new Compiler(TestUtils.getFile("parser/variable2.php"));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test3() throws Exception {
        Compiler compiler = new Compiler(TestUtils.getFile("compiler/static_variable.php"));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test4() throws Exception {
        Compiler compiler = new Compiler(TestUtils.getFile("compiler/reflection.php"));
        PhpFunction phpFunction = compiler.compile();
        assertThat(phpFunction.toString(), not(containsString("$$")));
    }

    @Test
    public void test5() throws Exception {
        Compiler compiler = new Compiler(TestUtils.getFile("interpreter/case47/callfunction_unknown2.php.inc"));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

    @Test
    public void test6() throws Exception {
        Compiler compiler = new Compiler(TestUtils.getFile("compiler/arrayaccess.php"));
        PhpFunction phpFunction = compiler.compile();
        System.out.println(phpFunction.toString());
        // 例外なしならOK
        assert true;
    }

}
