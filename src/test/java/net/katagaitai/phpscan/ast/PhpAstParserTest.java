package net.katagaitai.phpscan.ast;

import net.katagaitai.phpscan.ast.nodes.AST;
import net.katagaitai.phpscan.ast.nodes.Program;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhpAstParserTest {
    @Test
    public void testNormalTag() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/normal_tag.php").getFile()));
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
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/asp_tag.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, true, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("ASP形式のタグ"), is(true));
    }

    @Test
    public void testShortOpenTag() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/short_open_tag.php").getFile()));
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
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/echo_tag.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(StringUtils.countMatches(program.toString(), "この文字列を表示"), is(2));
    }

    @Test
    public void testBranch() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/branch.php").getFile()));
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
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/no_end_tag.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(StringUtils.countMatches(program.toString(), "<EchoStatement"), is(2));
    }

    @Test
    public void testInlineVariable() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/inline_variable.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().contains("<Variable"), is(true));
    }

    @Test
    public void testOpe() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/ope.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().matches("(?s).*operator='\\+'.*operator='\\*'.*"), is(true));
    }

    @Test
    public void testOpe2() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/ope2.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
        assertThat(program.toString().matches("(?s).*operator='\\+'.*operator='\\*'.*"), is(true));
    }

    @Test
    public void testVariable() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/variable.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testBacktick() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/backtick.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testMethod() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/method.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testNamespace() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/namespace.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testQuote() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/quote.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testRefernce() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/reference.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testScalar() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/scalar.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testField() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/field.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testConstant() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/constant.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testStatic() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/static.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testYield() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/yield.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testFunction() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/function.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testClass() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/class.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testParent() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/parent.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testEchoPrint() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/echo_print.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }

    @Test
    public void testVariable2() throws Exception {
        FileReader reader = new FileReader(new File(getClass().getClassLoader().getResource("parser/variable2.php").getFile()));
        AST ast = new AST(reader, PHPVersion.PHP5_6, false, false);
        PhpAstParser parser = ast.getParser();
        parser.parse();
        Program program = parser.getProgram();
        System.out.println(program);
    }
}
