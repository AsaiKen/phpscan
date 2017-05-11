package net.katagaitai.phpscan.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.Vulnerability;
import net.katagaitai.phpscan.VulnerabilityCategory;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.interpreter.SourcePosition;
import net.katagaitai.phpscan.taint.Taint;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

@Log4j2
public class ScanUtils {

	public static void report(Interpreter ip, List<Vulnerability> vulnerabilityList) {
		// 重複を排除する
		List<List<String>> signatureList = ip.getVulnerabilitySignatureList();
		for (Iterator<Vulnerability> iterator = vulnerabilityList.iterator(); iterator.hasNext();) {
			Vulnerability vulnerability = iterator.next();
			List<String> signature = Lists.newArrayList();
			String name = vulnerability.getCategory().getName();
			signature.add(name);
			String commentString = String.join("\n", vulnerability.getCommentList());
			signature.add(commentString);
			String pathString = ScanUtils.getPathTraceWithSource(vulnerability.getPositionList());
			signature.add(pathString);
			if (signatureList.contains(signature)) {
				iterator.remove();
			} else {
				signatureList.add(signature);
			}
		}
		// レポートを出力する
		int vulnSizePerPage = Constants.VULN_SIZE_PER_PAGE;
		int size = vulnerabilityList.size();
		for (int pageNum = 0; pageNum < Math.ceil(((double) size) / vulnSizePerPage); pageNum += 1) {
			List<Vulnerability> subList =
					vulnerabilityList.subList(
							pageNum * vulnSizePerPage,
							Math.min((pageNum + 1) * vulnSizePerPage, size));
			List<String> overviewStringList = Lists.newArrayList("Overview", "===", "\n");
			List<String> detailStringList = Lists.newArrayList("Detail", "===", "\n");
			for (int i = 0; i < subList.size(); i++) {
				Vulnerability vulnerability = subList.get(i);
				List<String> stringList = Lists.newArrayList();
				String name = vulnerability.getCategory().getName();
				stringList.add(
						String.format("<a id='%d'>#%d %s</a>", i + 1, i + 1, name));
				stringList.add("--------------");
				stringList.add("### comment");
				stringList.add("```");
				String commentString = String.join("\n", vulnerability.getCommentList());
				stringList.add(escape(commentString));
				stringList.add("```");
				stringList.add("### path");
				stringList.add("```");
				String pathString = ScanUtils.getPathTraceWithSource(vulnerability.getPositionList());
				stringList.add(escape(pathString));
				stringList.add("```");
				if (vulnerability.getCallStack().size() > 0) {
					stringList.add("### callstack");
					stringList.add("```");
					String callstackString = ScanUtils.getStackTraceWithSource(vulnerability.getCallStack());
					stringList.add(escape(callstackString));
					stringList.add("```");
				}
				stringList.add("___");
				detailStringList.addAll(stringList);
				overviewStringList.add(
						String.format("### [#%d %s](#%d)", i + 1, vulnerability.getCategory().getName(), i + 1));
			}
			overviewStringList.add("___\n");
			if (subList.size() > 0) {
				String overviewString = String.join("\n", overviewStringList);
				String detailString = String.join("\n", detailStringList);
				try {
					String headString =
							Files.toString(new File("resource/report_template.head"), Constants.DEFAULT_CHARSET);
					String tailString =
							Files.toString(new File("resource/report_template.tail"), Constants.DEFAULT_CHARSET);
					File file = FileUtils.createReportFile(ip, pageNum);
					java.nio.file.Files.deleteIfExists(file.toPath());
					Files.append(headString, file, Constants.DEFAULT_CHARSET);
					Files.append(overviewString, file, Constants.DEFAULT_CHARSET);
					Files.append(detailString, file, Constants.DEFAULT_CHARSET);
					Files.append(tailString, file, Constants.DEFAULT_CHARSET);
				} catch (IOException e) {
					log.error("レポートの作成に失敗しました。", e);
				}
			}
		}
	}

	private static boolean isDisabledCategory(VulnerabilityCategory category) {
		String disabledCategoriesString = PropertyUtils.getString(PropertyUtils.DISABLED_VULNERABILITY_CATEGORIES);
		List<String> disabledCategoryList = Lists.newArrayList(disabledCategoriesString.split(","));
		return disabledCategoryList.contains(category.toString());
	}

	private static String escape(String string) {
		String result = string.replace("```", "\\`\\`\\`")
				.replace("*/", "\\*\\/").replace("</script>", "<\\/script>");
		return result;
	}

	public static void reportTmp(Interpreter ip, Vulnerability vulnerability) {
		List<String> detailStringList = Lists.newArrayList();
		detailStringList.add(
				String.format("%s", vulnerability.getCategory().getName()));
		detailStringList.add("--------------");
		detailStringList.add("### comment");
		detailStringList.add("```");
		detailStringList.add(String.join("\n", vulnerability.getCommentList()));
		detailStringList.add("```");
		detailStringList.add("### path");
		detailStringList.add("```");
		detailStringList.add(ScanUtils.getPathTraceWithSource(vulnerability.getPositionList()));
		detailStringList.add("```");
		if (vulnerability.getCallStack().size() > 0) {
			detailStringList.add("### callstack");
			detailStringList.add("```");
			detailStringList.add(ScanUtils.getStackTraceWithSource(vulnerability.getCallStack()));
			detailStringList.add("```");
		}
		detailStringList.add("### command");
		detailStringList.add("```");
		for (Command command : vulnerability.getCommandList()) {
			detailStringList.add(command.toString());
		}
		detailStringList.add("```");
		detailStringList.add("___");
		try {
			File tmpFile = FileUtils.createTmpReportFile(ip.getPhpFile());
			Files.append(String.join("\n", detailStringList) + "\n\n", tmpFile, Constants.DEFAULT_CHARSET);
		} catch (IOException e) {
			log.error("tmpレポートの作成に失敗しました。", e);
		}
	}

	public static String getStackTraceWithSource(List<SourcePosition> callStack) {
		List<String> list = Lists.newArrayList();
		for (int i = 0; i < callStack.size(); i++) {
			SourcePosition position = callStack.get(i);
			list.add(String.format("#%d: %s\n\t%s", callStack.size() - i, position.toString(), position.getSourceLine()));
		}
		return String.join("\n", list);
	}

	public static String getPathTraceWithSource(List<SourcePosition> positionList) {
		List<SourcePosition> filteredPositionList = Lists.newArrayList();
		for (SourcePosition position : positionList) {
			if (filteredPositionList.size() == 0) {
				filteredPositionList.add(position);
			} else if (filteredPositionList.get(filteredPositionList.size() - 1).equals(position)) {
				// skip
			} else if (position.getFile().getAbsolutePath().contains(Constants.PREFIX)) {
				// sikip
			} else {
				filteredPositionList.add(position);
			}
		}
		List<String> list = Lists.newArrayList();
		for (int i = 0; i < filteredPositionList.size(); i++) {
			SourcePosition position = filteredPositionList.get(i);
			list.add(String.format("#%d: %s\n\t%s", i + 1, position.toString(), position.getSourceLine()));
		}
		return String.join("\n", list);
	}

	public static List<Vulnerability> getVulnerabilityList(Interpreter ip, VulnerabilityCategory category,
			Set<Taint> taintSet, String comment) {
		List<Vulnerability> result = Lists.newArrayList();
		for (Taint taint : taintSet) {
			Taint newTaint = TaintUtils.createTaint(ip, taint, comment);
			Vulnerability vulnerability = new Vulnerability(category, ip, newTaint);
			result.add(vulnerability);
		}
		return result;
	}

	public static List<Vulnerability> getVulnerabilityList(Interpreter ip, VulnerabilityCategory category,
			Set<Taint> taintSet) {
		return getVulnerabilityList(ip, category, taintSet, null);
	}

	public static void addVulnerability(Interpreter ip, List<Vulnerability> vulnerabilityList) {
		for (Vulnerability vulnerability : vulnerabilityList) {
			if (isDisabledCategory(vulnerability.getCategory())) {
				continue;
			}
			//			ScanUtils.reportTmp(ip, vulnerability);
			ip.getVulnerabilityList().add(vulnerability);
		}
	}

	private static List<String> ignoreLineList = null;
	private static List<String> ignoreRegexpList = null;

	public static boolean isIgnorePath(String scanTargetPath) throws IOException {
		if (scanTargetPath == null) {
			return true;
		}
		File scanTargetFile = new File(scanTargetPath);
		if (scanTargetFile.exists() && scanTargetFile.isFile()) {
			// OK
		} else {
			// ファイルでないなら無視する
			return true;
		}

		// regexpをロード
		if (ignoreLineList == null) {
			String ignoreRegexpsString = PropertyUtils.getString(PropertyUtils.IGNORE_REGEXPS);
			if (ignoreRegexpsString == null || ignoreRegexpsString.isEmpty()) {
				// skip
			} else {
				String[] ignoreRegexps = ignoreRegexpsString.split(",");
				ignoreRegexpList = Lists.newArrayList(ignoreRegexps);
			}
		}

		// ignore.txtをロード
		if (ignoreLineList == null) {
			String ignoreListPath = PropertyUtils.getString(PropertyUtils.IGNORE_TXT_PATH);
			if (ignoreListPath == null || ignoreListPath.isEmpty()) {
				// skip
			} else {
				File ignoreListFile = new File(ignoreListPath);
				if (ignoreListFile.exists() && ignoreListFile.isFile()) {
					// OK
				} else {
					throw new RuntimeException("除外ファイルが見つかりません。：" + ignoreListPath);
				}
				ignoreLineList = Files.readLines(ignoreListFile, Constants.DEFAULT_CHARSET);
			}
		}

		if (ignoreRegexpList != null) {
			for (String regexp : ignoreRegexpList) {
				String prefix = "^";
				if (regexp.startsWith("^")) {
					prefix = "";
				}
				String suffix = "$";
				if (regexp.endsWith("$")) {
					suffix = "";
				}
				if (scanTargetPath.matches(prefix + regexp + suffix)) {
					return true;
				}
			}
		}

		if (ignoreLineList != null) {
			for (String line : ignoreLineList) {
				File ignoreFile = new File(line);
				if (ignoreFile.isFile() && scanTargetFile.equals(ignoreFile)) {
					return true;
				} else if (ignoreFile.isDirectory()
						&& scanTargetFile.getAbsolutePath().startsWith(ignoreFile.getAbsolutePath())) {
					return true;
				}
			}
		}

		return false;
	}

	public static void writeSummary(String string) throws IOException {
		File summaryFile = FileUtils.createSummaryFile();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(new Date());
		Files.append(String.format("%s %s\n", dateString, string),
				summaryFile, Constants.DEFAULT_CHARSET);
	}
}
