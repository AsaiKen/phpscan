package net.katagaitai.phpscan.command;

import java.io.File;

import lombok.Value;
import net.katagaitai.phpscan.interpreter.Interpreter;
import net.katagaitai.phpscan.util.FileUtils;

@Value
public class CommentSource implements Command {
	private File file;
	private int start;
	private int length;

	@Override
	public String toString() {
		return String.format("// %s#%d",
				file.toPath().getFileName(), FileUtils.getLineno(file, start));
	}

	@Override
	public void accept(Interpreter vm) {
		vm.accept(this);
	}

}
