import net.katagaitai.phpscan.MainTest;
import net.katagaitai.phpscan.ast.PhpAstParserTest;
import net.katagaitai.phpscan.compiler.CompilerTest;
import net.katagaitai.phpscan.compiler.ProjectCompilerTest;
import net.katagaitai.phpscan.interceptor.InterceptorTest;
import net.katagaitai.phpscan.interpreter.InterpreterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({PhpAstParserTest.class, CompilerTest.class, ProjectCompilerTest.class, InterpreterTest.class,
        InterceptorTest.class, MainTest.class})
public class AllTests {

}
