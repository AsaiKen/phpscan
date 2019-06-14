package net.katagaitai.phpscan.interpreter;

import lombok.Value;
import net.katagaitai.phpscan.util.FileUtils;

import java.io.File;

@Value
public class SourcePosition {
    private File file;
    private int start;
    private int length;

    public long getLineno() {
        return FileUtils.getLineno(file, start);
    }

    public String getSourceLine() {
        return FileUtils.getSourceLine(file, getLineno());
    }

    @Override
    public String toString() {
        return String.format("%s:%d", file.getAbsolutePath(), getLineno());
    }

}
