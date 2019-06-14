package net.katagaitai.phpscan;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.compiler.PhpProject;
import net.katagaitai.phpscan.compiler.ProjectCompiler;
import net.katagaitai.phpscan.interceptor.Interceptor;
import net.katagaitai.phpscan.interceptor.TimeKeeper;
import net.katagaitai.phpscan.interceptor.cms.WordPressInterceptor;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.FileUtils;
import net.katagaitai.phpscan.util.PropertyUtils;
import net.katagaitai.phpscan.util.ScanUtils;
import net.katagaitai.phpscan.util.TaintUtils;
import org.ini4j.Ini;

import java.io.File;
import java.util.List;

@Log4j2
public class Main {
    public static void main(String[] args) {
        String entryPointPath = PropertyUtils.getString(PropertyUtils.ENTRY_POINT_PATH);
        String entryPointParentPath = PropertyUtils.getString(PropertyUtils.ENTRY_POINT_PARENT_PATH);

        if (entryPointParentPath != null && !entryPointParentPath.isEmpty()) {
            File entryPointParentFile = new File(entryPointParentPath);
            if (entryPointParentFile.exists() && entryPointParentFile.isDirectory()) {
                // OK
            } else {
                throw new RuntimeException("検査開始パスがディレクトリではありません。：" + entryPointParentPath);
            }
            for (File pluginDirFile : entryPointParentFile.listFiles()) {
                if (pluginDirFile.isDirectory()) {
                    // OK
                } else {
                    continue;
                }
                scan(pluginDirFile.getAbsolutePath());
            }
        } else if (entryPointPath != null && !entryPointPath.isEmpty()) {
            File entryPointFile = new File(entryPointPath);
            if (entryPointFile.exists() && entryPointFile.isDirectory()) {
                // OK
            } else {
                throw new RuntimeException("検査開始パスがディレクトリではありません。：" + entryPointParentPath);
            }
            scan(entryPointFile.getAbsolutePath());
        } else {
            throw new RuntimeException("検査開始パスがありません。");
        }
    }

    private static void scan(String entryPointPath) {
        String phpIniPath = PropertyUtils.getString(PropertyUtils.PHP_INI_PATH);
        String projectPath = PropertyUtils.getString(PropertyUtils.PROJECT_PATH);
        PhpProject phpProject = new PhpProject();
        if (projectPath == null || projectPath.isEmpty()) {
            projectPath = entryPointPath;
        }
        ProjectCompiler projectCompiler = new ProjectCompiler(new File(projectPath));
        if (entryPointPath.startsWith(projectPath)) {
            // skip
        } else {
            projectCompiler.setEntryPointDirectoryFile(new File(entryPointPath));
        }
        try {
            phpProject = projectCompiler.compile();
        } catch (Exception e) {
            log.error("", e);
        }
        List<List<String>> vulnerabilitySignatureList = Lists.newArrayList();
        List<String> filepathList = FileUtils.getFilepathList(entryPointPath);
        if (filepathList.size() == 0) {
            log.error("検査対象のファイルがありません。：" + entryPointPath);
        }
        for (String filepath : filepathList) {
            log.info("検査開始：" + filepath);
            try {
                if (ScanUtils.isIgnorePath(filepath)) {
                    log.info("除外：" + filepath);
                    continue;
                }
                Interpreter ip = new Interpreter(
                        new File(filepath),
                        new Ini(new File(phpIniPath))
                );
                ip.setPhpProject(phpProject);
                ip.setVulnerabilitySignatureList(vulnerabilitySignatureList);
                ip.setUseEcho(false);
                List<Interceptor> interceptorList = Lists.newArrayList();
                interceptorList.addAll(TaintUtils.getTaintInterceptorList(ip));
                //				interceptorList.add(new Debugger(ip));
                interceptorList.add(new TimeKeeper(ip));
                // CMSやフレームワークの個別対応を有効化する
                String usedFrameworks = PropertyUtils.getString(PropertyUtils.USED_FRAMEWORKS);
                if (usedFrameworks != null) {
                    for (String usedFramework : usedFrameworks.split(",")) {
                        if (usedFramework.toLowerCase().equals("wordpress")) {
                            interceptorList.add(new WordPressInterceptor(ip));
                        }
                    }
                }
                ScanUtils.writeSummary("開始：" + filepath);
                ip.execute(interceptorList);
                ScanUtils.writeSummary("終了：" + ip.getVulnerabilityList().size());
            } catch (Exception e) {
                log.error("検査失敗：" + filepath, e);
            }
            log.info("検査終了：" + filepath);
        }
    }
}
