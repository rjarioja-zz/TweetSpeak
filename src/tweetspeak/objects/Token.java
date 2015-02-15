package tweetspeak.objects;

enum TokenName {
	ID, START, END, MAIN,
	PROC_CALL, PROC_RET,
	ASSIGN, COMMENT, INPUT, OUTPUT,
	IF, ELSE_IF, ELSE, 
	DO, WHILE, BREAK, CONTINUE;

}

public class Token {
	private String lexeme, type;
	private int lineNumber, indexNumber;
}
