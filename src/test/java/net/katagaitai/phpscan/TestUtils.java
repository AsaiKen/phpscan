package net.katagaitai.phpscan;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;


public class TestUtils {
    public static final String APPS_DIR_PATH = "/opt/lampp/htdocs/phpscan_test";

    public static String sort(String str) {
        List<String> list = Lists.newArrayList(str.split(""));
        Collections.sort(list);
        return String.join("", list);
    }

}
