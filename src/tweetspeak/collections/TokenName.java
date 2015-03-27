package tweetspeak.collections;

public enum TokenName {
	START, PROG_NAME, END, MAIN,
	PROC_CALL, PROC_RET,
	CONCAT, VAR,
	ASSIGN, COMMENT, INPUT, OUTPUT, GIBBERISH,
	IF, ELSE_IF, ELSE, 
	DO, WHILE, BREAK, CONTINUE,
	DATATYPE_INT, DATATYPE_FLOAT, DATATYPE_CHAR, DATATYPE_STRING, DATATYPE_BOOL, DATATYPE_VOID,
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