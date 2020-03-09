package net.katagaitai.phpscan.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import lombok.extern.log4j.Log4j2;

import com.google.common.collect.Maps;
import com.google.common.io.Files;

@Log4j2
public class PropertyUtils {
	public static final String ENTRY_POINT_PATH = "ENTRY_POINT_PATH";
	public static final String PROJECT_PATH = "PROJECT_PATH";
	public static final String PHP_INI_PATH = "PHP_INI_PATH";
	public static final String IGNORE_TXT_PATH = "IGNORE_TXT_PATH";
	public static final String IGNORE_REGEXPS = "IGNORE_REGEXPS";
	public static final String SQL_ESCAPE_FUNCTIONS = "SQL_ESCAPE_FUNCTIONS";
	public static final String ENTRY_POINT_PARENT_PATH = "ENTRY_POINT_PARENT_PATH";
	public static final String HTML_ESCAPE_FUNCTIONS = "HTML_ESCAPE_FUNCTIONS";
	public static final String DISABLED_VULNERABILITY_CATEGORIES = "DISABLED_VULNERABILITY_CATEGORIES";
	public static final String PHP_FILE_EXTENSIONS = "PHP_FILE_EXTENSIONS";
	public static final String USED_FRAMEWORKS = "USED_FRAMEWORKS";

	private static final Map<String, String> PROPERTY_MAP = Maps.newHashMap();
	static {
		File file = getResourceAsFile("/setting.properties");
		Properties properties = new Properties();
		try {
			String string = Files.toString(file, Constants.DEFAULT_CHARSET);
			properties.load(new StringReader(string.replace("\\", "\\\\")));
		} catch (IOException e) {
			log.error("", e);
		}
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			log.debug(key + "[=]" + value);
			PROPERTY_MAP.put(key, value);
		}
	}

	public static String getString(String key) {
		return PROPERTY_MAP.get(key);
	}

	public static long getLong(String key) {
		return Long.parseLong(PROPERTY_MAP.get(key));
	}

	public static File getResourceAsFile(String path) {
		String path2 = path;
		if (path.startsWith("/")) {
			path2 = path.substring(1);
		}
		try {
			File file = null;
			URL url = Thread.currentThread().getContextClassLoader().getResource(path);
			if (url != null) {
				file = new File(url.toURI());
			} else {
				file = new File(path2);
			}
			if (!file.exists()) {
				log.error("ファイルが見つかりません。：" + path);
			}
			return file;
		} catch (URISyntaxException e) {
			log.warn("", e);
			return null;
		}
	}

}
