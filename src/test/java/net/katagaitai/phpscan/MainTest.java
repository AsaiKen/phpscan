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
}
