package net.katagaitai.phpscan;

import com.google.common.collect.Lists;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;


public class TestUtils {

    public static String sort(String str) {
        List<String> list = Lists.newArrayList(str.split(""));
        Collections.sort(list);
        return String.join("", list);
    }

    public static File getFile(String str) throws URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(str);
        return new File(url.toURI());
    }
}
