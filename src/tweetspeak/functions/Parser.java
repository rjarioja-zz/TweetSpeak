package tweetspeak.functions;

import java.io.IOException;
import java.util.*;
import tweetspeak.collections.GrammarRules;
import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.objects.*;

public class Parser {
	private static int state;
	private static Token currentToken;
    private static Stack<TokenNode> tokenStack;
    private static Stack<Integer> stateStack;
	
	//constructors
    public Parser() throws IOException {
        tokenStack = new Stack<TokenNode>();
        stateStack = new Stack<Integer>();
    }
    
    //methods
	private static void getToken() {
		Token token = null;
		token = Tokenizer.getToken();
		
		if (!(token instanceof Comment)) currentToken = token;
	    else {
	    	while (token instanceof Comment || token.getName().equals("COMMENT")) {
	    		token = Tokenizer.getToken();
	    		if (!(token instanceof Comment)) break;
	    	}
	    	currentToken = token;
	    }
		if (token == null) currentToken = new Token("$", "$", TokenType.SPEC_SYMBOL.toString());
	}
	
	public static boolean parse() {
		state = 0;
		TokenNode root = new TokenNode("$");
        
	    //root.setData("$");
	    tokenStack.push(root);
	    stateStack.push(state);
        
	    Tokenizer.initialize();
		
		// PARSING TABLE IN SWITCH-CASE FORM
		// prepare for pagkahaba habang code :(
		// please ganto nalang
		// sorry = not sorry
	    
	    // kewl to kimpot :P
	    
	    /* TO: KIM, para saan tong mga list na to? -r */

	    // - 
	    // kasi sa case 12 to 26, pareparehas lang sila ayan laman. so para di sobrang haba
	    // ng code hahahah gets
	    // same goes with 67, 72, etc
	    // pag redundant lang lol
	    //-
	    
	    /* AHHHHHHHHHHH OKIE :D */

	    //12 - 26
	    List<String> checkReduce = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString());
	    
	    //state 67 at 72
	    List<String> checkReduce2 = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.ADD_OP.toString(),
	    		TokenName.DIF_OP.toString(),			TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString());
	    
	    List<String> checkReduce3 = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.ADD_OP.toString(),
	    		TokenName.DIF_OP.toString(),			TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString(),			TokenName.MUL_OP.toString(),
	    		TokenName.DIV_OP.toString(),			TokenName.MOD_OP.toString());

	    List<String> checkReduce4 = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.ADD_OP.toString(),
	    		TokenName.DIF_OP.toString(),			TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString(),			TokenName.MUL_OP.toString(),
	    		TokenName.DIV_OP.toString(),			TokenName.MOD_OP.toString(),
	    		TokenName.EXP_OP.toString());

	    List<String> checkReduce5 = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.INPUT.toString());

	    List<String> checkReduce6 = Arrays.asList(
	    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString(),			TokenName.LESS_EQ_OP.toString(),
				TokenName.OR_OP.toString(),				TokenName.AND_OP.toString(),
				TokenName.EQUAL_OP.toString(),			TokenName.NOT_EQUAL_OP.toString(),
				TokenName.GREAT_OP.toString(),			TokenName.LESS_OP.toString(),
				TokenName.GREAT_EQ_OP.toString());

	    List<String> rightParenParamSep = Arrays.asList(
	    		TokenName.RIGHT_PAREN.toString(), 		TokenName.PARAM_SEP.toString());
	    
	    getToken();
	    System.out.println("START PARSE WITH: " + currentToken.toString());

		while (true) {
			String stackTop = tokenStack.peek().getData();
            Token tokenTop = tokenStack.peek().getToken();
            
			switch (state) {
				case 0:
					if (currentToken.getName().equals(TokenName.START.toString())) 
						shift(2);
					else if (stackTop.equals("<PROGRAM>") && tokenTop == null) {
						state = 1;
						stateStack.push(state);
					} else error();
					break;
					
				case 1:
					if (currentToken.getName() == "$") {
						System.out.println("before if else + currentToken:" + currentToken.toString());
						
						// error
                        if (tokenStack.get(1).getChildren().size() == 0) {
                            System.err.println("Parse tree is broken!\nProbably a STATEMENT reduction problem.");
                            System.exit(0);
                        }
                        // accept
                        //truly ba return true?
                        else {
                        	System.out.println("ACCEPT");
                        	return true;
                        }
                    } else
                        System.out.println("End of file");
                    break;
					
				case 2:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString())) 
						shift(3);
					else errorMsg("a program name.");
					break;
					
				case 3:
					if (currentToken.getName().equals(TokenName.INDENT.toString())) 
						shift(4);
					else errorMsg("an indent.");
					break;
					
				case 4:
					if (currentToken.getName().equals(TokenName.MAIN.toString())) 
						shift(10);
					else if (stackTop.equals("<FUNCTIONS>") && tokenTop == null) {
						state = 5;
						stateStack.push(state);
					} else if (stackTop.equals("<MAIN_FUNCTION>") && tokenTop == null) {
						state = 8;
						stateStack.push(state);
					} else error();
					break;
					
				case 5:
					if (currentToken.getName().equals("DEDENT"))
						shift(6);
					else errorMsg("a dedent.");
					break;
					
				case 6:
					if (currentToken.getName().equals(TokenName.END.toString()))
						shift(7);
					else errorMsg("End of Program.");
					break;
					
				case 7:
					System.out.println("FINAL TOKEN = " + currentToken.toString());
					if (currentToken.getName() == "$") 
						reduce(2);
                    else System.out.println("End of file");
                    break;
                    
				case 8:
					if (currentToken.getName().equals("DEDENT"))
						reduce(4);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						shift(32);
					else if(stackTop.equals("<SUB_FUNCTIONS>") && tokenTop == null) {
						state = 9;
						stateStack.push(state);
					} else
						error();
					break;
					
				case 9:
					if(currentToken.getName().equals("DEDENT"))
						reduce(3);
					else error(); 
					break;
					
				case 10:
					if(currentToken.getName().equals("INDENT"))
						shift(11);
					else errorMsg("an indent.");
					break;
					
				case 11:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						shift(137);
					else if (currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if (currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(142);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						shift(32);
					else if(currentToken.getName().equals(TokenName.INPUT.toString()))
						shift(88);
					else if(currentToken.getName().equals(TokenName.OUTPUT.toString()))
						shift(90);
					else if(currentToken.getName().equals(TokenName.DO.toString()))
						shift(159);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString()))
						shift(140);
					else if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
						state = 134;
						stateStack.push(state);
					} else if(stackTop.equals("<MORE_STATEMENTS>") && tokenTop == null){
						state = 149;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 147;
						stateStack.push(state);
					} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
						state = 12;
						stateStack.push(state);
					} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
						state = 13;
						stateStack.push(state);
					} else if(stackTop.equals("<IO>") && tokenTop == null){
						state = 14;
						stateStack.push(state);
					} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
						state = 15;
						stateStack.push(state);
					} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null){
						state = 16;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<INC_STMT>") && tokenTop == null){
						state = 23;
						stateStack.push(state);
					} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null){
						state = 24;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 151;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else error();
					break;
					
				case 12:
					if(checkReduce.contains(currentToken.getName())) reduce(19);
					else error();
					break;
					
				case 13:
					if(checkReduce.contains(currentToken.getName())) reduce(20);
					else error();
					break;
					
				case 14:
					if(checkReduce.contains(currentToken.getName())) reduce(21);
					else error();
					break;
					
				case 15: 
					if(checkReduce.contains(currentToken.getName())) reduce(22);
					else error();
					break;
					
				case 16:
					if(checkReduce.contains(currentToken.getName())) reduce(24);
					else error();
					break;
					
				case 17: 
					if(checkReduce.contains(currentToken.getName())) reduce(25);
					else error();
					break;
					
				case 18:
					if(checkReduce.contains(currentToken.getName())) reduce(26);
					else error();
					break;
					
				case 19:
					if(checkReduce.contains(currentToken.getName())) reduce(30);
					else error();
					break;
					
				case 20:
					if(checkReduce.contains(currentToken.getName())) reduce(31);
					else error();
					break;
					
				case 21:
					if(checkReduce.contains(currentToken.getName())) reduce(32);
					else error();
					break;
					
				case 22:
					if(checkReduce.contains(currentToken.getName())) reduce(33);
					else error();
					break;
					
				case 23:
					if(checkReduce.contains(currentToken.getName())) reduce(36);
					else error();
					break;
					
				case 24:
					if(checkReduce.contains(currentToken.getName())) reduce(37);
					else error();
					break;
					
				case 25:
					if(checkReduce.contains(currentToken.getName())) reduce(39);
					else error();
					break;
					
				case 26:
					if(checkReduce.contains(currentToken.getName())) reduce(40);
					else error();
					break;
					
				case 27:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(43);
					else error();
					break;
					
				case 28:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(44);
					else error();
					break;
					
				case 29:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(45);
					else error();
					break;
					
				case 30:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(46);
					else error();
					break;
					
				case 31:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(47);
					else error();
					break;
					
				case 32:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(48);
					else error();
					break;

				case 34:
					if(checkReduce.contains(currentToken.getName())) reduce(52);  
					else error();
					break;
					
				case 35:
					if(checkReduce.contains(currentToken.getName())) reduce(53);  
					else error();
					break;
					
				case 36:
					if(checkReduce.contains(currentToken.getName())) reduce(54);  
					else error();
					break;
					
				case 37:
					if(checkReduce.contains(currentToken.getName())) reduce(5);  
					else error();
					break;
					
				case 38:
					if(checkReduce.contains(currentToken.getName())) reduce(77);  
					else error();
					break;
					
				case 39: 
					if(checkReduce.contains(currentToken.getName())) reduce(78);  
					else error();
					break;
					
				case 40:
					if(checkReduce.contains(currentToken.getName())) reduce(79);  
					else error();
					break;
					
				case 41:
					if(checkReduce.contains(currentToken.getName())) reduce(80);  
					else error();
					break;
					
				case 42:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						shift(43);
					else error();
					break;
					
				case 43:
					if(checkReduce.contains(currentToken.getName())) reduce(27);  
					else if(currentToken.getName().equals(TokenName.ASSIGN_OP.toString()))
						shift(44);
					else error();
					break;
					
				case 44:
					if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(46);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(47);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(48);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(49);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(53);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(52);
					else if(currentToken.getName().equals(null))
						shift(51);
					else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 45;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 50;
						stateStack.push(state);
					} else {
						error();
					} break;
					
				case 45: 
					if(checkReduce.contains(currentToken.getName())) reduce(28);  
					else error();
					break;
					
				case 46:
					if(checkReduce.contains(currentToken.getName())) reduce(114);  
					else error();
					break;
					
				case 47:
					if(checkReduce.contains(currentToken.getName())) reduce(115);  
					else error();
					break;
					
				case 48:
					if(checkReduce.contains(currentToken.getName())) reduce(116);  
					else error();
					break;
					
				case 49: 
					if(checkReduce.contains(currentToken.getName())) reduce(117);  
					else error();
					break;
					
				case 50: 
					if(checkReduce.contains(currentToken.getName())) reduce(119);  
					else error();
					break;
					
				case 51: 
					if(checkReduce.contains(currentToken.getName())) reduce(118);  
					else error();
					break;
					
				case 52: 
					if(checkReduce.contains(currentToken.getName())) reduce(120);  
					else error();
					break;
					
				case 53:
					if(checkReduce.contains(currentToken.getName())) reduce(121);  
					else error();
					break;
					
				case 54: 
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						shift(55);
					else error();
					break;
					
				case 55:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(94);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(60);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 56;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
						state = 57;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 72;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 58;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
						state = 59;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
						state = 103;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 93;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 56:
					if(checkReduce.contains(currentToken.getName())) reduce(29);  
					else error();
					break;

				case 57: 
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString()))
						shift(115);
					else if(currentToken.getName().equals(TokenName.ADD_OP.toString()))
						shift(66);
					else if(currentToken.getName().equals(TokenName.DIF_OP.toString()))
						shift(85);
					else error();
					break;

				case 58:
					if(checkReduce.contains(currentToken.getName())) reduce(57);  
					else error();
					break;

				case 59:
					if(checkReduce.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())) 
							reduce(58);  
					else error();
					break;

				case 60:
					if(currentToken.getName().equals(TokenName.PROC_NAME.toString()))
						shift(61);
					else error();
					break;

				case 61: 
					if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) 
						shift(62);
					else error();
					break;

				case 62: 
					if(currentToken.getName().equals(TokenName.VAR.toString())) 
						shift(76);
					else if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) 
						shift(65);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(77);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(78);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(79);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(80);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(84);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(83);
					else if(currentToken.getName().equals(null))
						shift(82);
					else if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
						state = 63;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 86;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 75;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 81;
						stateStack.push(state);
					} else error();
					break;

				case 63:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) 
						shift(64);
					else error();
					break;

				case 64:
					if(checkReduce.contains(currentToken.getName())) reduce(59);  
					else error();
					break;

				case 65:
					if(checkReduce.contains(currentToken.getName())) reduce(60);  
					else error();
					break;

				case 66: 
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 67;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 67: 
					if(checkReduce2.contains(currentToken.getName()))
						reduce(81);
					else if (currentToken.getName().equals(TokenName.MUL_OP.toString()))
						shift(69);
					else if (currentToken.getName().equals(TokenName.DIV_OP.toString()))
						shift(70);
					else if (currentToken.getName().equals(TokenName.MOD_OP.toString()))
						shift(71);
					else error();
					break;

				case 68:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(87);
					else error();
					break;

				case 69:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 111;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 70:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 111;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 71:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 111;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 72:
					if(checkReduce2.contains(currentToken.getName()))
						reduce(83);
					else if (currentToken.getName().equals(TokenName.MUL_OP.toString()))
						shift(69);
					else if (currentToken.getName().equals(TokenName.DIV_OP.toString()))
						shift(70);
					else if (currentToken.getName().equals(TokenName.MOD_OP.toString()))
						shift(71);
					else error();
					break;

				case 73:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(89);
					else if (currentToken.getName().equals(TokenName.EXP_OP.toString()))
						shift(74);
					else error();
					break;

				case 74:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 111;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 75:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(112);
					else error();
					break;

				case 76:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(113);
					else error();
					break;

				case 77:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(114);
					else error();
					break;

				case 78:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(115);
					else error();
					break;

				case 79:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(116);
					else error();
					break;

				case 80:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(117);
					else error();
					break;

				case 81:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(118);
					else error();
					break;

				case 82:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(119);
					else error();
					break;

				case 83:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(120);
					else error();
					break;

				case 84:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(121);
					else error();
					break;

				case 85:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 67;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 86:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString()))
						reduce(62);
					else if(currentToken.getName().equals(TokenName.PARAM_SEP.toString()))
						shift(87);
					else error();
					break;
					
				case 87: 
					if(currentToken.getName().equals(TokenName.VAR.toString())) 
						shift(76);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(77);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(78);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(79);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(80);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(84);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(83);
					else if(currentToken.getName().equals(null))
						shift(82);
					else if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 113;
						stateStack.push(state);
					} else if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
						state = 112;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 86;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 75;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 81;
						stateStack.push(state);
					} else error();
					break;

				case 88: 
					if(currentToken.getName().equals(TokenName.VAR.toString())) 
						shift(89);
					else error();
					break;

				case 89:
					if(checkReduce.contains(currentToken.getName())) reduce(49);
					else error();
					break;

				case 90:
					if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(60);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 56;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
						state = 57;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 72;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 58;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
						state = 59;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
						state = 103;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 91:
					if(currentToken.getName().equals(TokenName.VAR.toString())) 
						shift(76);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(101);
					else if(currentToken.getName().equals(null))
						shift(100);
					else if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
						state = 57;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 72;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR4>") && tokenTop == null){
						state = 73;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 109;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 99;
						stateStack.push(state);
					} else error();
					break;

				case 92:
					if(checkReduce4.contains(currentToken.getName())) reduce(91);
					else if(currentToken.getName().equals(TokenName.CONCAT.toString())) shift(116);
					else error();
					break;

				case 93: 
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.CONCAT.toString())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())
						|| currentToken.getName().equals(TokenName.AND_OP.toString())
						|| currentToken.getName().equals(TokenName.EQUAL_OP.toString())
						|| currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())
						|| currentToken.getName().equals(TokenName.GREAT_OP.toString())
						|| currentToken.getName().equals(TokenName.LESS_OP.toString())
						|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
						|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())
						|| currentToken.getName().equals(TokenName.NOT_OP.toString())) 
						reduce(112);
					else error();
					break;

				case 94:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.CONCAT.toString())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())
						|| currentToken.getName().equals(TokenName.AND_OP.toString())
						|| currentToken.getName().equals(TokenName.EQUAL_OP.toString())
						|| currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())
						|| currentToken.getName().equals(TokenName.GREAT_OP.toString())
						|| currentToken.getName().equals(TokenName.LESS_OP.toString())
						|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
						|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())
						|| currentToken.getName().equals(TokenName.NOT_OP.toString()))
						reduce(113);
					else error();
					break;

				case 95: 
					if(checkReduce4.contains(currentToken.getName())) reduce(114);
					else error();
					break;

				case 96:
					if(checkReduce4.contains(currentToken.getName())) reduce(115);
					else error();
					break;
					
				case 97:
					if(checkReduce4.contains(currentToken.getName())) reduce(116);
					else error();
					break;
					
				case 98:
					if(checkReduce4.contains(currentToken.getName())) reduce(117);
					else error();
					break;
					
				case 99:
					if(checkReduce4.contains(currentToken.getName())) reduce(118);
					else error();
					break;
					
				case 100:
					if(checkReduce4.contains(currentToken.getName())) reduce(119);
					else error();
					break;
					
				case 101:
					if(checkReduce4.contains(currentToken.getName())) reduce(120);
					else error();
					break;
				
				case 102:
					if(checkReduce4.contains(currentToken.getName())) reduce(121);
					else error();
					break;

				case 103:
					if(checkReduce.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())
						|| currentToken.getName().equals(TokenName.AND_OP.toString())) 
							reduce(95);
					else error();
					break;

				// case 104:
				// 	if(checkReduce.contains(currentToken.getName())
				// 		|| currentToken.getName().equals(TokenName.OR_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.AND_OP.toString())) 
				// 			reduce(97);
				// 	else error();
				// 	break;
					//double reduce

				// case 105:
				// 	if(checkReduce.contains(currentToken.getName())
				// 		|| currentToken.getName().equals(TokenName.OR_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.AND_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.EQUAL_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.GREAT_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.LESS_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) 
				// 			reduce(100);
				// 	else error();
				// 	break;
					//double reduce

				// case 106:
				// 	if(checkReduce.contains(currentToken.getName())
				// 		|| currentToken.getName().equals(TokenName.OR_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.AND_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.EQUAL_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.GREAT_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.LESS_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
				// 		|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) 
				// 			reduce(105);
				// 	else error();
				// 	break;

					//triple reduce
				
				case 107:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
						state = 59;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
						state = 103;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 108:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
						state = 103;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 93;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 109:
					if(checkReduce4.contains(currentToken.getName())) reduce(112);
					else error();
					break;

				case 110:
					if(checkReduce4.contains(currentToken.getName())) reduce(113);
					else error();
					break;

				case 111:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(84);
					else error();
					break;

				case 112:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) 
						reduce(61);
					else error();
					break;

				case 113:
					if(checkReduce5.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.DO.toString())
						|| currentToken.getName().equals(TokenName.WHILE.toString()))
						reduce(50);
					else error();
					break;

				case 114:
					if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else error();
					break;

				case 115:
					if(checkReduce5.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OUTPUT.toString())
						|| currentToken.getName().equals(TokenName.ADD_OP.toString())
						|| currentToken.getName().equals(TokenName.DIF_OP.toString())
						|| currentToken.getName().equals(TokenName.MUL_OP.toString())
						|| currentToken.getName().equals(TokenName.DIV_OP.toString())
						|| currentToken.getName().equals(TokenName.MOD_OP.toString())
						|| currentToken.getName().equals(TokenName.EXP_OP.toString()))
							reduce(90);
					else error();
					break;

				case 116:
					if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 117;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else error();
					break;

				case 117:
					if(checkReduce5.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OUTPUT.toString())) 
							reduce(93);
					else error();
					break;

				case 118:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 119:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 105;
						stateStack.push(state);
					} if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 120:if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 121:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 122:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 123:
				 	if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString()))
						shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString()))
						shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString()))
						shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString()))
						shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString()))
						shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString()))
						shift(132);
					else if(currentToken.getName().equals(null))
						shift(131);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString()))
						shift(107);
					if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 106;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						state = 92;
						stateStack.push(state);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 124;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 130;
						stateStack.push(state);
					} else error();
					break;

				case 124:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(112);
					else error();
					break;

				case 125:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(113);
					else error();
					break;

				case 126:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(114);
					else error();
					break;

				case 127:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(115);
					else error();
					break;

				case 128:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(116);
					else error();
					break;

				case 129:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(117);
					else error();
					break;

				case 130:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(118);
					else error();
					break;

				case 131:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(119);
					else error();
					break;

				case 132:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(120);
					else error();
					break;

				case 133:
					if(checkReduce6.contains(currentToken.getName()))
						reduce(121);
					else error();
					break;

				case 134:
					if(currentToken.getName().equals(TokenName.DEDENT.toString()))
						shift(158);
					else error();
					break;

				case 135:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(136);
					else error();
					break;

				case 136:
					if(checkReduce.contains(currentToken.getName()))
						reduce(108);
					else error();
					break;

				case 137:
					if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(138);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString())){
						shift(139);
					} else error();
					break;

				case 138:
					if(checkReduce.contains(currentToken.getName()))
						reduce(109);
					else error();
					break;

				case 139:
					if(checkReduce.contains(currentToken.getName()))
						reduce(111);
					else error();
					break;

				case 140:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(141);
					else error();
					break;

				case 141:
					if(checkReduce.contains(currentToken.getName()))
						reduce(110);
					else error();
					break;

				case 142:
					if(currentToken.getName().equals(TokenName.PROC_NAME.toString()))
						shift(143);
					else error();
					break;

				case 143:
					if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
						shift(144);
					else error();
					break;

				case 144:
					if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
						state = 145;
						stateStack.push(state);
					} else error();
					break;

				case 145:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString()))
						shift(146);
					else error();
					break;

				case 146:
					if(checkReduce.contains(currentToken.getName()))
						reduce(38);
					else error();
					break;

				case 147:
					if(currentToken.getName().equals(TokenName.DEDENT.toString()))
						reduce(20);
					else if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(137);
					else if(currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(142);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						shift(32);
					else if(currentToken.getName().equals(TokenName.INPUT.toString()))
						shift(88);
					else if(currentToken.getName().equals(TokenName.OUTPUT.toString()))
						shift(90);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString()))
						shift(140);
					if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
						state = 148;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 147;
						stateStack.push(state);
					} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
						state = 12;
						stateStack.push(state);
					} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
						state = 13;
						stateStack.push(state);
					} else if(stackTop.equals("<IO>") && tokenTop == null){
						state = 14;
						stateStack.push(state);
					} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
						state = 15;
						stateStack.push(state);
					} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null){
						state = 16;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 151;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<INC_STMT>") && tokenTop == null){
						state = 23;
						stateStack.push(state);
					} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null){
						state = 24;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else error();
					break;

				case 148:
					if (currentToken.getName().equals(TokenName.DEDENT.toString()))
						reduce(11);
					else error();
					break;

				case 149:
					if(currentToken.getName().equals(TokenName.DEDENT.toString()))
						reduce(13);
					else if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(137);
					else if(currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(142);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.INPUT.toString()))
						shift(88);
					else if(currentToken.getName().equals(TokenName.OUTPUT.toString()))
						shift(90);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString()))
						shift(140);
					if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
						state = 150;
						stateStack.push(state);
					} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){
						state = 149;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 147;
						stateStack.push(state);
					} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
						state = 12;
						stateStack.push(state);
					} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
						state = 13;
						stateStack.push(state);
					} else if(stackTop.equals("<IO>") && tokenTop == null){
						state = 14;
						stateStack.push(state);
					} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
						state = 15;
						stateStack.push(state);
					} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null){
						state = 16;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 151;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else error();
					break;

				case 150:
					if (currentToken.getName().equals(TokenName.DEDENT.toString()))
						reduce(12);
					else error();
					break;

				case 151:
					if(currentToken.getName().equals(TokenName.ELSE.toString()))
						shift(156);
					else if(stackTop.equals("<ELSEIF_STMTS>") && tokenTop == null){
						state = 153;
						stateStack.push(state);
					} else if(stackTop.equals("<ELSEIF_STMT>") && tokenTop == null){
						state = 154;
						stateStack.push(state);
					} else if(stackTop.equals("<ELSE_STMT>") && tokenTop == null){
						state = 152;
						stateStack.push(state);
					} else error();
					break;

				//case 152: RYAN PAKIGAWA REDUCE NA MAY OR OR PAG GINAWA MO TO PUPUTI KA HE HE
					//break;

				case 153:
					if(checkReduce.contains(currentToken.getName()))
						reduce(63);
					else error();
					break;

				case 154:
					if(currentToken.getName().equals(TokenName.ELSE.toString()))
						shift(156);
					else if(stackTop.equals("<ELSEIF_STMTS>") && tokenTop == null){
						state = 155;
						stateStack.push(state);
					} else if(stackTop.equals("<ELSE_STMT>") && tokenTop == null){
						state = 152;
						stateStack.push(state);
					} else error();
					break;

				case 155:
					if(checkReduce.contains(currentToken.getName()))
						reduce(65);
					else error();
					break;

				case 156:
					if(currentToken.getName().equals(TokenName.DEDENT.toString()))
						reduce(13);
					else if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(137);
					else if(currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(142);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						shift(32);
					else if(currentToken.getName().equals(TokenName.INPUT.toString()))
						shift(88);
					else if(currentToken.getName().equals(TokenName.OUTPUT.toString()))
						shift(90);
					else if(currentToken.getName().equals(TokenName.ELSE.toString()))
						shift(157);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString()))
						shift(140);
					if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
						state = 150;
						stateStack.push(state);
					} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){
						state = 149;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 147;
						stateStack.push(state);
					} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
						state = 12;
						stateStack.push(state);
					} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
						state = 13;
						stateStack.push(state);
					} else if(stackTop.equals("<IO>") && tokenTop == null){
						state = 14;
						stateStack.push(state);
					} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
						state = 15;
						stateStack.push(state);
					} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null){
						state = 16;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 151;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<INC_STMT>") && tokenTop == null){
						state = 23;
						stateStack.push(state);
					} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null){
						state = 24;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else error();
					break;

				case 157:
					if(checkReduce.contains(currentToken.getName()))
						reduce(72);
					else error();
					break;

				case 158:
					if (currentToken.getName().equals(TokenName.DEDENT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						reduce(5);
					else error();
					break;

				case 159:
					if(currentToken.getName().equals(TokenName.DO.toString()))
						shift(160);
					else error();
					break;

				case 160:
					if(currentToken.getName().equals(TokenName.VAR.toString()))
						shift(137);
					else if(currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(142);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
						shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString()))
						shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString()))
						shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString()))
						shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString()))
						shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						shift(32);
					else if(currentToken.getName().equals(TokenName.INPUT.toString()))
						shift(88);
					else if(currentToken.getName().equals(TokenName.OUTPUT.toString()))
						shift(90);
					else if(currentToken.getName().equals(TokenName.DO.toString()))
						shift(159);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString()))
						shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString()))
						shift(140);
					if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
						state = 150;
						stateStack.push(state);
					} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){
						state = 149;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 147;
						stateStack.push(state);
					} else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
						state = 12;
						stateStack.push(state);
					} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null){
						state = 13;
						stateStack.push(state);
					} else if(stackTop.equals("<IO>") && tokenTop == null){
						state = 14;
						stateStack.push(state);
					} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null){
						state = 15;
						stateStack.push(state);
					} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null){
						state = 16;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 151;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<INC_STMT>") && tokenTop == null){
						state = 23;
						stateStack.push(state);
					} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null){
						state = 24;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else error();
					break;
			} // end of switch
		}
	}
	
	private static void reduce(int rule) {
		System.out.println("Reduce: " + currentToken.toString());
        int ruleLength = GrammarRules.getRule(rule).size() - 1;

        TokenNode node = new TokenNode();
        String variable = GrammarRules.getRule(rule).get(0);
        node.setData(variable);

        Stack<TokenNode> poppedNodes = new Stack<TokenNode>();

        for (int i = 0; i < ruleLength; i++) {
            if (!tokenStack.empty()) {
                poppedNodes.push(tokenStack.pop());
                stateStack.pop();
            } else {
                // TODO: Output Error
            }
        }

        int poppedNodesSize = poppedNodes.size();
        for (int i = 0; i < poppedNodesSize; i++) {
            TokenNode poppedNode = poppedNodes.pop();
            node.addChild(poppedNode);
            poppedNode.setParent(node);
        }

        System.out.println("Reduced to: " + node.toString());
        
        state = stateStack.peek();
        tokenStack.push(node);
        
        System.out.println("top of stack = " + tokenStack.peek().toString());
        System.out.println("stack top " + tokenStack.peek().getData());
    }
	
	private static void shift(int nextState) {
        TokenNode node = new TokenNode();
        node.setData(currentToken.getName());
        node.setToken(currentToken);
        tokenStack.push(node);
        
        state = nextState;
        stateStack.push(state);

        getToken();
        System.out.println("Shift to state " + nextState + ": " + currentToken.toString());
    }
	
	public static TokenNode getRoot() {
        return tokenStack.get(1);
    }
	
	private static void error() {
		// TODO: ERROR MESSAGE or sumthin
	}

	private static void errorMsg (String errormsg) {
		System.out.println("Error at line " + currentToken.getLineNumber() + ". Expecting: " + errormsg);
	}
}
