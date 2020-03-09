package net.katagaitai.phpscan.ast;

import net.katagaitai.phpscan.TestUtils;
import net.katagaitai.phpscan.ast.nodes.AST;
import net.katagaitai.phpscan.ast.nodes.Program;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhpAstParserTest {
    @Test
    public void testNormalTag() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/normal_tag.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("パースされる。"), is(true));
        assertThat(program.toString().contains("パースされない。"), is(false));
    }

    @Test
    public void testAspTag() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/asp_tag.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, true, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("ASP形式のタグ"), is(true));
    }

    @Test
    public void testShortOpenTag() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/short_open_tag.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, true);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("<EchoStatement"), is(true));
        // OK
    }

    @Test
    public void testEchoTag() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/echo_tag.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(StringUtils.countMatches(program.toString(), "この文字列を表示"), is(2));
    }

    @Test
    public void testBranch() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/branch.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("<TrueStatement"), is(true));
        assertThat(program.toString().contains("<FalseStatement"), is(true));
    }

    @Test
    public void testNoEndTag() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/no_end_tag.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(StringUtils.countMatches(program.toString(), "<EchoStatement"), is(2));
    }

    @Test
    public void testInlineVariable() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/inline_variable.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("<Variable"), is(true));
    }

    @Test
    public void testOpe() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/ope.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().matches("(?s).*operator='\\+'.*operator='\\*'.*"), is(true));
    }

    @Test
    public void testOpe2() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/ope2.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().matches("(?s).*operator='\\+'.*operator='\\*'.*"), is(true));
    }

    @Test
    public void testVariable() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/variable.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testBacktick() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/backtick.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testMethod() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/method.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testNamespace() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/namespace.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testQuote() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/quote.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testRefernce() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/reference.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testScalar() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/scalar.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testField() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/field.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testConstant() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/constant.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testStatic() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/static.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testYield() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/yield.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testFunction() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/function.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testClass() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/class.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testParent() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/parent.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testEchoPrint() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/echo_print.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testVariable2() throws Exception {
        FileReader reader = new FileReader(TestUtils.getFile("parser/variable2.php"));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }
}
