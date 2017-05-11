package net.katagaitai.phpscan.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.log4j.Log4j2;
import net.katagaitai.phpscan.interpreter.Interpreter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

@Log4j2
public class FileUtils {
	public static File createTempFile(String prefix, String suffix)
			throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		File result = new File("./tmp/" + sdf.format(new Date()),
				new StringBuilder().append(Constants.PREFIX).append(prefix)
						// ランダム値
						.append(UUID.randomUUID())
						.append(suffix).toString());
		Files.createParentDirs(result);
		return result;
	}

	private static final Map<File, List<Integer>> NEWLINE_OFFSET_LIST_MAP = Maps.newHashMap();

	public static void load(File file) {
		if (file != null && file.exists() && file.isFile()) {
			// OK
		} else {
			return;
		}
		try {
			String string =
					com.google.common.io.Files.toString(file, Constants.DEFAULT_CHARSET);
			String orgString = string;
			List<Integer> newLineOffsetList = Lists.newArrayList();
			int i;
			int sum = 0;
			while ((i = string.indexOf("\n")) >= 0) {
				newLineOffsetList.add(sum + i);
				string = string.substring(i + 1);
				sum += i + 1;
			}
			newLineOffsetList.add(orgString.length());
			NEWLINE_OFFSET_LIST_MAP.put(file, newLineOffsetList);

		} catch (IOException e) {
			log.warn("", e);
		}
	}

	public static int getLineno(File file, int position) {
		if (!NEWLINE_OFFSET_LIST_MAP.containsKey(file)) {
			load(file);
		}
		List<Integer> newlineOffsetList = NEWLINE_OFFSET_LIST_MAP.get(file);
		for (int i = 0; i < newlineOffsetList.size(); i++) {
			int newlineOffset = newlineOffsetList.get(i);
			if (position <= newlineOffset) {
				return i + 1;
			}
		}
		return 0;
	}

	public static String getSourceLine(File file, long lineno) {
		try {
			List<String> lineList = Files.readLines(file, Constants.DEFAULT_CHARSET);
			if (lineno - 1 >= 0 && lineno - 1 < lineList.size()) {
				return lineList.get((int) (lineno - 1));
			}
		} catch (IOException e) {
			log.warn("", e);
		}
		return "[unknown]";
	}

	public static File createReportFile(Interpreter ip, int index) throws IOException {
		File phpFile = ip.getPhpFile();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String filename = (ip.isTimeout() ? "[Timeout]" : "")
				+ phpFile.getAbsolutePath().replace("\\", "_").replace("/", "_").replace(":", "_");
		File result = new File("./result/" + sdf.format(new Date()), filename + "-" + index + ".html");
		Files.createParentDirs(result);
		return result;
	}

	public static File createTmpReportFile(File phpFile) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmssSSS");
		String filename = phpFile.getAbsolutePath()
				.replace("\\", "_").replace("/", "_").replace(":", "_");
		File result = new File("./tmp/result/" + sdf.format(new Date()),
				filename + "_" + sdf2.format(new Date()) + ".txt");
		Files.createParentDirs(result);
		return result;
	}

	public static File createSummaryFile() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		File result = new File("./result/" + sdf.format(new Date()), "summary.txt");
		Files.createParentDirs(result);
		return result;
	}

	private static final Map<String, List<String>> FILEPATH_LIST_MAP = Maps.newHashMap();

	public static List<String> getFilepathList(String dirPath) {
		File dirFile = new File(dirPath);
		if (dirFile.exists() && dirFile.isDirectory()) {
			// OK
		} else {
			log.warn("ディレクトが存在しません。：" + dirPath);
			return Lists.newArrayList();
		}
		if (FILEPATH_LIST_MAP.get(dirFile.getAbsolutePath()) == null) {
			List<String> filepathList = Lists.newArrayList();
			String extensionsString = PropertyUtils.getString(PropertyUtils.PHP_FILE_EXTENSIONS);
			String[] extensions;
			if (extensionsString == null) {
				extensions = Constants.DEFAULT_PHP_FILE_EXTENSIONS;
			} else {
				extensions = extensionsString.split(",");
			}
			boolean recursive = true;
			Collection<File> files = org.apache.commons.io.FileUtils.listFiles(dirFile, extensions, recursive);
			for (File file : files) {
				filepathList.add(file.getAbsolutePath());
			}
			FILEPATH_LIST_MAP.put(dirFile.getAbsolutePath(), filepathList);
		}
		return FILEPATH_LIST_MAP.get(dirFile.getAbsolutePath());
	}
}
