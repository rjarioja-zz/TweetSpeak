package tweetspeak.divisions;
import java.util.LinkedList;

public class Code {
	private static LinkedList<CodeLine> lines = new LinkedList<CodeLine>();
	private static String code;
	
	//getters
	public static String getCode() { return code; }
	
	public static CodeLine getLine(int index) { return lines.get(index); }
	public static LinkedList<CodeLine> getLineList() { return lines; }
	
	//setters
	public static void setCode(String code) { 
		String lines[] = code.split("\n");
		Code.code = code;
		Code.lines.clear();
		int lineNumber = 1;
		for (String l : lines) {
			addLine(new CodeLine(l, lineNumber++));
		}
	}
	public static void setLineList(LinkedList<CodeLine> lines) { Code.lines = lines; }
	
	//methods
	public static void addLine(CodeLine line) { lines.add(line); }
	public static int indexOfLine(CodeLine line) { return lines.indexOf(line); }
	
	public static String toLines() {
		String lines[] = code.split("\n");
		String toLines = "";
		if (Code.lines.isEmpty()) {
			int lineCount = 1;
			for (String s : lines) Code.addLine(new CodeLine(s, lineCount++));
		}
		
		for (CodeLine l : Code.lines) toLines += l.toString() + "\n";
		return toLines;
	}
	
	public static void clearTokens() {
		for (CodeLine l : Code.lines) l.clearTokens();
	}
}