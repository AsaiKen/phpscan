package net.katagaitai.phpscan.taint;

import java.util.LinkedList;
import java.util.List;

import lombok.Value;
import net.katagaitai.phpscan.command.Command;
import net.katagaitai.phpscan.interpreter.SourcePosition;

@Value
public class Taint {
	private final List<Command> commandList;
	private final List<SourcePosition> positionList;
	private final LinkedList<EncodeTag> encodeTagStack;
	private final List<String> commentList;
}
