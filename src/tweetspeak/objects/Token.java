package tweetspeak.objects;

enum TokenName {
	ID, START, END, MAIN,
	PROC_CALL, PROC_RET,
	CONCAT, VAR,
	ASSIGN, COMMENT, INPUT, OUTPUT,
	IF, ELSE_IF, ELSE, 
	DO, WHILE, BREAK, CONTINUE,
	INT_CONST, FLOAT_CONST, CHAR_CONST, STRING_CONST,
	BOOL_CONST_TRUE, BOOL_CONST_FALSE, NULL,
	INC_OP, DEC_OP,
	ADD_OP, DIF_OP,
	MUL_OP, MOD_OP, DIV_OP,
	EXP_OP, LEFT_PAREN,	RIGHT_PAREN,
	OR_OP, AND_OP, NOT_OP,
	LESS_OP, GREAT_OP,
	LESS_EQ_OP, GREAT_EQ_OP,
	EQUAL_OP, NOT_EQUAL_OP,
	ASSIGN_OP, NEST, 
	PARAM_SEP, STMT_SEP,
	DQUOTE,	SQUOTE;
}

public class Token {
	private String lexeme, name;
	private int lineNumber, indexNumber;
	
	//getters
	public String getLexeme() { return lexeme; }
	public String getName() { return name; }
	public int getIndexNumber() { return indexNumber; }
	public int getLineNumber() { return lineNumber; }
		
	//setters
	public void setLexeme(String lexeme) { this.lexeme = lexeme; }
	public void setName(String name) { this.name = name; }
	public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
	public void setIndexNumber(int indexNumber) { this.indexNumber = indexNumber; }
}
