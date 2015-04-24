package tweetspeak.functions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.sun.xml.internal.ws.api.pipe.NextAction;

import tweetspeak.collections.GrammarRules;
import tweetspeak.collections.TokenName;
import tweetspeak.collections.TokenType;
import tweetspeak.objects.Comment;
import tweetspeak.objects.Token;
import tweetspeak.objects.TokenNode;

public class Parser {
	private static int state;
	private static Token currentToken, previousToken;
    private static Stack<TokenNode> tokenStack;
    private static Stack<Integer> stateStack;
	
    static String stackTop, stackNextTop = "";
    static Token tokenTop = null;
    //Token tokenNextTop = tokenStack.get(tokenStack.size() - 2).getToken();
    
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

    //12 - 26 WITH else_if & else
    static List<String> checkReduce = Arrays.asList(
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
    		TokenName.OUTPUT.toString(),			TokenName.ELSE_IF.toString(),
    		TokenName.ELSE.toString());
    
    //state 67 at 72
    static List<String> checkReduce2 = Arrays.asList(
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
    		
    static List<String> checkReduce3 = Arrays.asList(
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
    		
    
    static List<String> checkReduce4 = Arrays.asList(
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
    
    //43 - 73 pero di lahat -- same as checkReduce but WITHOUT else_if & else 
    static List<String> checkReduce5 = Arrays.asList(	
    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
    		TokenName.DO.toString(),				TokenName.WHILE.toString(),
    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
    		TokenName.PROC_RET.toString(),			TokenName.INPUT.toString(),
    		TokenName.OUTPUT.toString());

    static List<String> checkReduce6 = Arrays.asList(
    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
    		TokenName.PROC_CALL.toString(), 		TokenName.PROC_RET.toString(),
    		TokenName.BREAK.toString(),				TokenName.CONTINUE.toString(), 
    		TokenName.DATATYPE_BOOL.toString(),		TokenName.DATATYPE_INT.toString(), 
    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
    		TokenName.DATATYPE_VOID.toString(),		TokenName.DATATYPE_STRING.toString(),
    		TokenName.IF.toString(),				TokenName.ELSE_IF.toString(),
    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
    		TokenName.INPUT.toString(),				TokenName.OUTPUT.toString(),
    		TokenName.GREAT_EQ_OP.toString(),		TokenName.LESS_EQ_OP.toString(),
			TokenName.OR_OP.toString(),				TokenName.AND_OP.toString(),
			TokenName.EQUAL_OP.toString(),			TokenName.NOT_EQUAL_OP.toString(),
			TokenName.GREAT_OP.toString(),			TokenName.LESS_OP.toString(),
			TokenName.ELSE.toString(),				TokenName.CONCAT.toString());

    static List<String> rightParenParamSep = Arrays.asList(
    		TokenName.RIGHT_PAREN.toString(), 		TokenName.PARAM_SEP.toString());
    
    static List<String> checkReduce7 = Arrays.asList(
    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
    		TokenName.PROC_CALL.toString(),			TokenName.PROC_RET.toString(),				
     		TokenName.CONTINUE.toString(),			TokenName.DATATYPE_BOOL.toString(),		
     		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
    		TokenName.INPUT.toString(),				TokenName.OUTPUT.toString(),
    		TokenName.INPUT.toString(),				TokenName.OUTPUT.toString(),
    		TokenName.BREAK.toString(),   			TokenName.ASSIGN.toString());

	static List<String> operatorss = Arrays.asList(
    		TokenName.RIGHT_PAREN.toString(), 		TokenName.LESS_EQ_OP.toString(),
			TokenName.OR_OP.toString(),				TokenName.AND_OP.toString(),
			TokenName.EQUAL_OP.toString(),			TokenName.NOT_EQUAL_OP.toString(),
			TokenName.GREAT_OP.toString(),			TokenName.LESS_OP.toString(),
			TokenName.GREAT_EQ_OP.toString());
	
	static List<String> nostmtsep = Arrays.asList(	//similar to checkReduce5 but WITHOUT stme_sep for 200th+ states
    		TokenName.VAR.toString(), 				TokenName.DEDENT.toString(),
    		TokenName.ASSIGN.toString(),			TokenName.PROC_CALL.toString(), 
    		TokenName.CONTINUE.toString(),	 		TokenName.BREAK.toString(),
    		TokenName.OUTPUT.toString(),			TokenName.DATATYPE_BOOL.toString(),
    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
    		TokenName.DO.toString(),				TokenName.WHILE.toString(),
    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
    		TokenName.PROC_RET.toString(),			TokenName.INPUT.toString());

	//constructors
    public Parser() throws IOException {
        tokenStack = new Stack<TokenNode>();
        stateStack = new Stack<Integer>();
    }
    
    //methods
	private static void getToken() {
		Token token = null;
		token = Tokenizer.getToken();
		
		previousToken = currentToken;
		//if (!(token instanceof Comment)) currentToken = token;
		if (token == null) currentToken = new Token("$", "$", TokenType.SPEC_SYMBOL.toString());
		else { 
			while (token instanceof Comment 
	    		|| token.getName().equals("COMMENT") 
	    		|| token.getName().equals("NEWLINE")
	    		|| token.getName().equals("NO_INDENT")) {
	    		token = Tokenizer.getToken();
	    		if (!(token instanceof Comment
    				|| token.getName().equals("COMMENT") 
    	    		|| token.getName().equals("NEWLINE")
    	    		|| token.getName().equals("NO_INDENT"))) break;
	    	}
			currentToken = token;
		}
		System.out.println("GET TOKEN = " + currentToken.getName());
    	
	}
	
	public static boolean parse() {
		state = 0;
		TokenNode root = new TokenNode("$");

	    tokenStack.push(root);
	    stateStack.push(state);
        
	    Tokenizer.initialize();
		
	    getToken();
	    System.out.println("START PARSE WITH: " + currentToken.toString());

		while (true) {
			if (!tokenStack.isEmpty()) {
				stackTop = tokenStack.peek().getData();
				if (tokenStack.size() >= 2) stackNextTop = tokenStack.get(tokenStack.size() - 2).getData();
				tokenTop = tokenStack.peek().getToken();
			}
			switch (state) {
				case 0:
					if (currentToken.getName().equals(TokenName.START.toString())) shift(2);
					else if (stackTop.equals("<PROGRAM>") && tokenTop == null) {
						state = 1;
						stateStack.push(state);
					} //else error();
					break;
					
				case 1:
					if (currentToken.getName() == "$") {
						System.out.println("before if else + currentToken:" + currentToken.toString());
						System.out.println(tokenStack.get(0).toString());
						// error
		                if (tokenStack.get(1).getChildren().size() == 0) {
		                    System.err.println("Parse tree is broken!\nProbably a STATEMENT reduction problem.");
		                    System.exit(0);
		                }
		                // accept
		                //truly ba return true?
		                /* YEAP, kasi boolean return neto. Binubuo nya lang yung tree*/
		                else {
		                	System.out.println("ACCEPT");
		                	return true;
		                }
		            } else
		                System.out.println("End of file");
		            break;
					
				case 2:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString())) shift(3);
					//else errorMsg("a program name.");
					break;
					
				case 3:
					if (currentToken.getName().equals(TokenName.INDENT.toString())) shift(4);
					//else errorMsg("an indent.");
					break;
					
				case 4:
					if (currentToken.getName().equals(TokenName.MAIN.toString())) shift(10);
					else if (stackTop.equals("<FUNCTIONS>") && tokenTop == null) {
						state = 5;
						stateStack.push(state);
					} else if (stackTop.equals("<MAIN_FUNCTION>") && tokenTop == null) {
						state = 8;
						stateStack.push(state);
					}////else error();
					break;
					
				case 5:
					if (currentToken.getName().equals("DEDENT")) shift(6);
					//else errorMsg("a dedent.");
					break;
					
				case 6:
					if (currentToken.getName().equals(TokenName.END.toString())) shift(7);
					//else errorMsg("End of Program.");
					break;
					
				case 7:
					System.out.println("FINAL TOKEN = " + currentToken.toString());
					if (currentToken.getName() == "$") 
						reduce(2);
		            else System.out.println("End of file");
		            break;
		            
				case 8:
					if(stackTop.equals("<SUB_FUNCTIONS>") && tokenTop == null) {
						state = 9;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
						state = 223;
						stateStack.push(state);
					} else if(stackTop.equals("<SUB_FUNCTION>") && tokenTop == null) {
						state = 221;
						stateStack.push(state);
					} else if (currentToken.getName().equals("DEDENT")) reduce(4);
					else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
					else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
					else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
					else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
					else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
					else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
					//else error();
					break;
					
				case 9:
					if(currentToken.getName().equals("DEDENT"))
						reduce(3);
					//else error(); 
					break;
					
				case 10:
					if(currentToken.getName().equals("INDENT")) shift(11);
					//else errorMsg("an indent.");
					break;
					
				case 11:
					System.out.println("State 11 stack top: " + tokenStack.get(tokenStack.size() - 1).getData());
					System.out.println("State 11 next top: " + stackNextTop);
					/*
					else if(currentToken.getName().equals(TokenName.DO.toString())) shift(159);
					else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(135);
					else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(140);
					*/
					if (stackTop.equals("<STATEMENTS>") && tokenTop == null){
						if (stackNextTop.equals("<STATEMENT>")) state = 136;
						else if (stackNextTop.equals("<MORE_STATEMENT>")) state = 138;
						else state = 134;
						//state = 134;
						stateStack.push(state);
					} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){
						state = 138;
						stateStack.push(state);
					} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
						state = 136;
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
						state = 206;
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
					} else if (currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
					else if (currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
					else if (currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
					else if (currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
					else if (currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
					else if (currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
					else if (currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))	 shift(27);
					else if (currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
					else if (currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
					else if (currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
					else if (currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
					else if (currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
					else if (currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
					else if (currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
					else if (currentToken.getName().equals(TokenName.IF.toString())) shift(165);
					else if (currentToken.getName().equals(TokenName.DO.toString())) shift(218);
					else if (currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
					else if (currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
					else if (currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
					//else 
					
					//else error();
					break;
					
				case 12:
					if(checkReduce.contains(currentToken.getName())) reduce(17);
					//else error();
					break;
					
				case 13:
					if(checkReduce.contains(currentToken.getName())) reduce(18);
					//else error();
					break;
					
				case 14:
					if(checkReduce.contains(currentToken.getName())) reduce(19);
					//else error();
					break;
					
				case 15: 
					if(checkReduce.contains(currentToken.getName())) reduce(20);
					//else error();
					break;
					
				case 16:
					if(checkReduce.contains(currentToken.getName())) reduce(22);
					//else error();
					break;
					
				case 17: 
					if(checkReduce.contains(currentToken.getName())) reduce(23);
					//else error();
					break;
					
				case 18:
					if(checkReduce.contains(currentToken.getName())) reduce(24);
					//else error();
					break;
					
				case 19:
					if(checkReduce.contains(currentToken.getName())) reduce(28);
					//else error();
					break;
					
				case 20:
					if(checkReduce.contains(currentToken.getName())) reduce(29);
					//else error();
					break;
					
				case 21:
					if(checkReduce.contains(currentToken.getName())) reduce(30);
					//else error();
					break;
					
				case 22:
					if(checkReduce.contains(currentToken.getName())) reduce(31);
					//else error();
					break;
					
				case 23:
					if(checkReduce.contains(currentToken.getName())) reduce(34);
					//else error();
					break;
					
				case 24:
					if(checkReduce.contains(currentToken.getName())) reduce(35);
					//else error();
					break;
					
				case 25:
					if(checkReduce.contains(currentToken.getName())) reduce(37);
					//else error();
					break;
					
				case 26:
					if(checkReduce.contains(currentToken.getName())) reduce(38);
					//else error();
					break;
					
				case 27:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(41);
					//else error();
					break;
					
				case 28:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(42);
					//else error();
					break;
					
				case 29:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(43);
					//else error();
					break;
					
				case 30:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(44);
					//else error();
					break;
					
				case 31:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(45);
					//else error();
					break;
					
				case 32:
					if (currentToken.getName().equals(TokenName.VAR.toString()))
						reduce(46);
					//else error();
					break;
					
				case 34:
					if(checkReduce.contains(currentToken.getName())) reduce(50);  
					//else error();
					break;
					
				case 35:
					if(checkReduce.contains(currentToken.getName())) reduce(51);  
					//else error();
					break;
					
				case 36:
					if(checkReduce.contains(currentToken.getName())) reduce(52);  
					//else error();
					break;
					
				case 37:
					if(checkReduce.contains(currentToken.getName())) reduce(53);  
					//else error();
					break;
					
				case 38:
					if(checkReduce.contains(currentToken.getName())) reduce(75);  
					//else error();
					break;
					
				case 39: 
					if(checkReduce.contains(currentToken.getName())) reduce(76);  
					//else error();
					break;
					
				case 40:
					if(checkReduce.contains(currentToken.getName())) reduce(77);  
					//else error();
					break;
					
				case 41:
					if(checkReduce.contains(currentToken.getName())) reduce(78);  
					//else error();
					break;
					
				case 42:
					if (currentToken.getName().equals(TokenName.VAR.toString())) shift(43);
					//else error();
					break;
					
				case 43:
					if(checkReduce.contains(currentToken.getName())) reduce(25);  
					else if(currentToken.getName().equals(TokenName.ASSIGN_OP.toString())) shift(44);
					//else error();
					break;
					
				case 44:
					if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(46);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(47);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(48);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(49);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(53);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(52);
					else if(currentToken.getName().equals(null)) shift(51);
					else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 45;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 50;
						stateStack.push(state);
					} //else error();
					break;
					
				case 45: 
					if(checkReduce5.contains(currentToken.getName())) reduce(26);  
					//else error();
					break;
					
				case 46:
					if(checkReduce5.contains(currentToken.getName())) reduce(112);  
					//else error();
					break;
					
				case 47:
					if(checkReduce5.contains(currentToken.getName())) reduce(113);  
					//else error();
					break;
					
				case 48:
					if(checkReduce5.contains(currentToken.getName())) reduce(114);  
					//else error();
					break;
					
				case 49: 
					System.out.println(currentToken);
					if(checkReduce5.contains(currentToken.getName())) reduce(115);  
					////else error();
					break;
					
				case 50: 
					if(checkReduce5.contains(currentToken.getName())) reduce(116);  
					//else error();
					break;
					
				case 51: 
					if(checkReduce5.contains(currentToken.getName())) reduce(117);  
					//else error();
					break;
					
				case 52: 
					if(checkReduce5.contains(currentToken.getName())) reduce(118);  
					//else error();
					break;
					
				case 53:
					if(checkReduce5.contains(currentToken.getName())) reduce(119);  
					//else error();
					break;
					
				case 54: 
					if (currentToken.getName().equals(TokenName.VAR.toString())) shift(55);
					//else error();
					break;
					
				case 55:
					if (stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(94);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(60);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 56:
					if(checkReduce5.contains(currentToken.getName())) reduce(27);  
					//else error();
					break;
	
				case 57: 
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) shift(115);
					else if(currentToken.getName().equals(TokenName.ADD_OP.toString())) shift(66);
					else if(currentToken.getName().equals(TokenName.DIF_OP.toString())) shift(85);
					else if(checkReduce5.contains(currentToken.getName())) reduce(54);
					//else error();
					break;
	
				case 58:
					if(checkReduce5.contains(currentToken.getName())) reduce(55);  
					//else error();
					break;
	
				case 59:
					if(checkReduce5.contains(currentToken.getName())) reduce(56);
					else if (currentToken.getName().equals(TokenName.OR_OP.toString())) shift(108);
					//else error();
					break;
	
				case 60:
					if(currentToken.getName().equals(TokenName.PROC_NAME.toString())) shift(61);
					//else error();
					break;
	
				case 61: 
					if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(62);
					//else error();
					break;
	
				case 62: 
					if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(76);
					else if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) shift(65);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(77);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(78);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(79);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(80);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(84);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(83);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(82);
					//else error();
					break;
	
				case 63:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) shift(64);
					//else error();
					break;
	
				case 64:
					if(checkReduce5.contains(currentToken.getName())) reduce(57);  
					//else error();
					break;
	
				case 65:
					if(checkReduce5.contains(currentToken.getName())) reduce(58);  
					//else error();
					break;
	
				case 66: 
					if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 67: 
					if(checkReduce2.contains(currentToken.getName()))
						reduce(79);
					//dag2
					else if (currentToken.getName().equals(TokenName.ELSE_IF.toString()))
						reduce(79);
					else if (currentToken.getName().equals(TokenName.ELSE.toString()))
						reduce(79);
					else if (currentToken.getName().equals(TokenName.MUL_OP.toString()))
						shift(69);
					else if (currentToken.getName().equals(TokenName.DIV_OP.toString()))
						shift(70);
					else if (currentToken.getName().equals(TokenName.MOD_OP.toString()))
						shift(71);
					//else error();
					break;
	
				case 68:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(85);
					else if (currentToken.getName().equals(TokenName.ELSE_IF.toString()))
						reduce(85);
					else if (currentToken.getName().equals(TokenName.ELSE.toString()))
						reduce(85);
					//else error();
					break;
	
				case 69:
					if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 70:
					if (stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(null)) shift(100);
					//else error();
					break;
	
				case 71:
					if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(null)) shift(100);
					//else error();
					break;
	
				case 72:
					if(checkReduce2.contains(currentToken.getName()))
						reduce(81);
					else if (currentToken.getName().equals(TokenName.ELSE_IF.toString()))
						reduce(82);
					else if (currentToken.getName().equals(TokenName.ELSE.toString()))
						reduce(82);
					else if (currentToken.getName().equals(TokenName.MUL_OP.toString()))
						shift(69);
					else if (currentToken.getName().equals(TokenName.DIV_OP.toString()))
						shift(70);
					else if (currentToken.getName().equals(TokenName.MOD_OP.toString()))
						shift(71);
					//else error();
					break;
	
				case 73:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(87);
					else if (currentToken.getName().equals(TokenName.ELSE_IF.toString()))
						reduce(87);
					else if (currentToken.getName().equals(TokenName.ELSE.toString()))
						reduce(87);
					else if (currentToken.getName().equals(TokenName.EXP_OP.toString()))
						shift(74);
					//else error();
					break;
	
				case 74:
					if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(null)) shift(100);
					//else error();
					break;
	
				case 75:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(110);
					//else error();
					break;
	
				case 76:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(111);
					//else error();
					break;
	
				case 77:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(112);
					//else error();
					break;
	
				case 78:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(113);
					//else error();
					break;
	
				case 79:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(114);
					//else error();
					break;
	
				case 80:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(115);
					//else error();
					break;
	
				case 81:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(116);
					//else error();
					break;
	
				case 82:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(117);
					//else error();
					break;
	
				case 83:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(118);
					//else error();
					break;
	
				case 84:
					if(rightParenParamSep.contains(currentToken.getName())) reduce(119);
					//else error();
					break;
	
				case 85:
					if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 86:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) reduce(60);
					else if(currentToken.getName().equals(TokenName.PARAM_SEP.toString())) shift(87);
					//else error();
					break;
					
				case 87: 
					if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(76);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(77);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(78);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(79);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(80);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(84);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(83);
					else if(currentToken.getName().equals(null)) shift(82);
					//else error();
					break;
	
				case 88: 
					if(currentToken.getName().equals(TokenName.VAR.toString())) shift(89);
					//else error();
					break;
	
				case 89:
					if(checkReduce5.contains(currentToken.getName())) reduce(47);
					//else error();
					break;
	
				case 90:
					if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 113;
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
					} else if (currentToken.getName().equals(TokenName.VAR.toString())) shift(94);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(60);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 91:
					if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(110);
					else if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(91);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(100);
					//else error();
					break;
	
				case 92:
					if (checkReduce.contains(currentToken.getName())) {
						String nextTop = stackNextTop;
						switch(nextTop) {
							case "ADD_OP": 		case "DIF_OP":
							case "MUL_OP": 		case "DIV_OP":
							case "MOD_OP": 		case "EXP_OP":
								reduce(89); break;
							case "CONCAT":
								reduce(90); break;
							case "OR_OP": 		case "AND_OP":
							case "EQUAL_OP":	case "NOT_EQUAL_OP":
							case "GREAT_OP":	case "GREAT_EQ_OP":
							case "LESS_OP":		case "LESS_EQ_OP":
								reduce(105); break;
							case "PROC_RET":
								reduce(40); break;
							default:
								switch(previousToken.getName().toString()) {
									case "INT_CONST": case "FLOAT_CONST":
										reduce(89); break;
									case "STRING_CONST":
									case "CHAR_CONST":
										reduce(90); break;
									case "BOOL_CONST_TRUE":
									case "BOOL_CONST_FALSE":
										reduce(105); break;
								}
						}
					}
					else if(currentToken.getName().equals(TokenName.CONCAT.toString())) shift(116);
					else if(currentToken.getName().equals(TokenName.ADD_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.DIF_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.MUL_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.DIV_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.MOD_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.EXP_OP.toString())) reduce(89);
					else if(currentToken.getName().equals(TokenName.OR_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.AND_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.EQUAL_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.GREAT_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.LESS_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())) reduce(105);
					else if(currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) reduce(105);
					//else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) reduce(105);
					////else error();
					break;
	
				case 93: 
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())
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
						reduce(110);
					//else error();
					break;
	
				case 94:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(111);
					//else error();
					break;
	
				case 95: 
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
					break;
	
				case 96:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
					//else error();
					break;
					
				case 97:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(114);
					//else error();
					break;
					
				case 98:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(115);
					//else error();
					break;
					
				case 99:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(116);
					//else error();
					break;
					
				case 100:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(117);
					//else error();
					break;
					
				case 101:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(118);
					//else error();
					break;
				
				case 102:
					if(checkReduce4.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.ELSE_IF.toString()) 
						|| currentToken.getName().equals(TokenName.ELSE.toString())	
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
						reduce(119);
					//else error();
					break;
	
					//state 103 to state 106 conflictz
				case 103:
					if(checkReduce.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())) {
							if (stackTop.equals("<REL_EXPR2>")) {
								if (stackNextTop.equals("OR_O")) reduce(92);
								else reduce(93);
							}
					} else if(currentToken.getName().equals(TokenName.AND_OP.toString())) {
							if (stackTop.equals("<REL_EXPR2>")) reduce(93);
							else shift(118);
					} else if(currentToken.getName().equals(TokenName.EQUAL_OP.toString())
				 		|| currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())
				 		|| currentToken.getName().equals(TokenName.GREAT_OP.toString())
				 		|| currentToken.getName().equals(TokenName.LESS_OP.toString())
				 		|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
				 		|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString()))
				 			reduce(93);
					break;
	
				case 104:
					if(checkReduce.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.OR_OP.toString())
						|| currentToken.getName().equals(TokenName.AND_OP.toString())) {
						if (stackTop.equals("<REL_EXPR3>")) {
							if (stackNextTop.equals("AND_OP")) reduce(94);
							else reduce(95);
						}
					} else if(currentToken.getName().equals(TokenName.EQUAL_OP.toString())) shift(114);
					else if(currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) shift(119);
					else if(currentToken.getName().equals(TokenName.GREAT_OP.toString())
				 		|| currentToken.getName().equals(TokenName.LESS_OP.toString())
				 		|| currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())
				 		|| currentToken.getName().equals(TokenName.LESS_EQ_OP.toString()))
				 		reduce(94);
				 	//else error();
					break;
	
				case 105:
					if (checkReduce6.contains(currentToken.getName())) {
						if (stackTop.equals("<REL_EXPR4>")) {
							if (stackNextTop.equals("EQUAL_OP")) reduce(96);
							else if (stackNextTop.equals("NOT_EQUAL_OP")) reduce(97);
							else reduce(98);
						}
					} //else error();
					break;
	
				case 106:
				 	if(checkReduce6.contains(currentToken.getName())) {
				 		if (stackTop.equals("<REL_EXPR5>")) {
							if (stackNextTop.equals("GREAT_OP")) reduce(99);
							else if (stackNextTop.equals("LESS_OP>")) reduce(100);
							else if (stackNextTop.equals("GREAT_EQ_OP>")) reduce(101);
							else if (stackNextTop.equals("LESS_EQ_OP>")) reduce(102);
							else reduce(103);
						}
				 	}
				 	//else error();
				 	break;
				
				case 107:
					if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
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
					} 
					else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
					else if(currentToken.getName().equals(TokenName.NULL.toString())) shift(131);
					//else error();
					break;
	
				case 108:
					if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
					else if(currentToken.getName().equals(null)) shift(131);
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
					} //else error();
					break;
	
				case 109:
					if(checkReduce4.contains(currentToken.getName())) reduce(110);
					//else error();
					break;
	
				case 110:
					if(checkReduce4.contains(currentToken.getName())) reduce(111);
					//else error();
					break;
	
				case 111:
					if(checkReduce3.contains(currentToken.getName()))
						reduce(82);
					//else error();
					break;
	
				case 112:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) 
						reduce(59);
					//else error();
					break;
	
				case 113:
					if(checkReduce5.contains(currentToken.getName()))
						reduce(48);
					//else error();
					break;
	
				case 114:
					if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
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
					} else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
					else if(currentToken.getName().equals(null)) shift(131);
					//else error();
					break;
	
				case 115:
					if(checkReduce4.contains(currentToken.getName()))
						reduce(88);
					//else error();
					break;
	
				case 116:
					if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
					else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(125);
					else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
					else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
					else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
					else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
					else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
					else if(currentToken.getName().equals(null)) shift(131);
					else if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 117;
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
					} //else error();
						break;
	
				case 117:
					if(checkReduce5.contains(currentToken.getName())) 
						reduce(91);
					//else error();
					break;
					
				case 118: case 119: case 120: case 121:
				case 122: case 123: case 124: case 125:
				case 126: case 127: case 128: case 129:
				case 130: case 131: case 132: case 133:
				case 134: case 135: case 136: case 137:
				case 138: case 139: case 140: case 141:
				case 142: case 143: case 144: case 145:
				case 146: case 147: case 148: case 149:
				case 150: case 151: case 152: case 153:
				case 154: case 155: case 156: case 157:
				case 158: case 159: case 160: case 161:
				case 162: case 163: case 164: case 165:
				case 166: case 167: case 168: case 169:
				case 170: case 171: case 172: case 173:
				case 174: case 175: case 176: case 177:
				case 178: case 179: case 180: case 181:
				case 182: case 183: case 184: case 185:
				case 186: case 187: case 188: case 189:
				case 190: case 191: case 192: case 193:
				case 194: case 195: case 196: case 197:
				case 198: case 199: case 200: case 201:
				case 202: case 203: case 204: case 205:
				case 206: case 207: case 208: case 209:
				case 210: case 211: case 212: case 213:
				case 214: case 215: case 216: case 217:
				case 218: case 219: case 220: case 221:
				case 222: case 223: case 224: case 225:
				case 226: case 227: case 228: case 229:
				case 230: case 231: case 232: case 233:
				case 234:
					
					parse2();
					break;
					
			} // end of switch
		}
	}
	static void parse2() {
		if (!tokenStack.isEmpty()) {
			stackTop = tokenStack.peek().getData();
			if (tokenStack.size() >= 2) stackNextTop = tokenStack.get(tokenStack.size() - 2).getData();
			tokenTop = tokenStack.peek().getToken();
		}
		
		switch (state) {
			case 118:
				if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);//else error();
				break;
	
			case 119:
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				//else error();
				break;
	
			case 120:
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				//else error();
				break;
	
			case 121:
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				//else error();
				break;
	
			case 122:
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				//else error();
				break;
	
			case 123:
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
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(125);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(126);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(127);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(128);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(129);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(133);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(132);
				else if(currentToken.getName().equals(null)) shift(131);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				//else error();
				break;
	
			case 124:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(110);
				//else error();
				break;
	
			case 125:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(111);
				//else error();
				break;
	
			case 126:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(112);
				//else error();
				break;
	
			case 127:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(113);
				//else error();
				break;
	
			case 128:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(114);
				//else error();
				break;
	
			case 129:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(115);
				//else error();
				break;
	
			case 130:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(116);
				//else error();
				break;
	
			case 131:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(117);
				//else error();
				break;
	
			case 132:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(118);
				//else error();
				break;
	
			case 133:
				if(checkReduce6.contains(currentToken.getName()))
					reduce(119);
				//else error();
				break;
	
			case 134:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) shift(135);
				//else error();
				break;
	
			case 135:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
					|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
						reduce(5);
				//else error();
				break;
	
			case 136:
				if(stackTop.equals("<STATEMENTS>") && tokenTop == null) {
					state = 137;
					stateStack.push(state);
				} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null) {
					state = 138;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<BRANCHING>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 18;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
					state = 20;
					stateStack.push(state);
				} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null) {
					state = 21;
					stateStack.push(state);
				} else if(stackTop.equals("<LOOPING>") && tokenTop == null) {
					state = 22;
					stateStack.push(state);
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 42;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null) {
					state = 34;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null) {
					state = 35;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_STMT>") && tokenTop == null) {
					state = 206;
					stateStack.push(state);
				} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null) {
					state = 36;
					stateStack.push(state);
				} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null) {
					state = 37;
					stateStack.push(state);
				} else if(stackTop.equals("<INC_STMT>") && tokenTop == null) {
					state = 23;
					stateStack.push(state);
				} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null) {
					state = 24;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_INC>") && tokenTop == null) {
					state = 38;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_INC>") && tokenTop == null) {
					state = 39;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null) {
					state = 40;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_DEC>") && tokenTop == null) {
					state = 41;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.DEDENT.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else reduce(14);
				} else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) shift(147);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(54);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(149);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())){
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(219);
				} else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(218);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(216);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(25);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(26);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(31);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(30);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(29);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(28);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(27);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(32);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(88);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(90);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(165);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(140);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if(stackNextTop.equals(TokenName.STMT_SEP.toString()))
						reduce(16);
					else shift(142);
				} else if(stackTop.equals("<STATENENT>") && tokenTop == null) {
					state = 136;
					stateStack.push(state);
				}
				//else error();
				break;
	
			case 137:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(11);
				//else error();	
				break;
	
			case 138:
				if(stackTop.equals("<STATEMENTS>") && tokenTop == null) {
					state = 139;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 136;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(13);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null) {
					state = 138;
					stateStack.push(state);
				} //else error();
				break;
	
			case 139:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(12);
				//else error();
				break;
	
			case 140:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(141);
				//else error();
				break;
	
			case 141:
				if(checkReduce7.contains(currentToken.getName())) reduce(106);
				//else error();
				break;
	
			case 142:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(143);
				//else error();
				break;
	
			case 143:
				if(checkReduce7.contains(currentToken.getName())) reduce(108);
				//else error();
				break;
	
			case 144:
				if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(145);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(146);
				//else error();
				break;
	
			case 145:
				if(checkReduce7.contains(currentToken.getName())) reduce(107);
				//else error();
				break;
	
			case 146:
				if(checkReduce7.contains(currentToken.getName())) reduce(109);
				//else error();
				break;
	
			case 147:
				if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null) {
					state = 148;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 136;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				//else error();
				break;
	
			case 148:
				if (checkReduce7.contains(currentToken.getName())) reduce(15);
				//else error();
				break;
	
			case 149:
				if(currentToken.getName().equals(TokenName.PROC_NAME.toString())) shift(150);
				//else error();
				break;
	
			case 150:
				if (currentToken.getName().equals(TokenName.LEFT_PAREN.toString()))
					shift(151);
				//else error();
				break;
	
			case 151:
				if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
					state = 195;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 153;
					stateStack.push(state);
				} else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 155;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 161;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(156);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(157);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(158);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(159);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(160);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(163);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(164);
				else if(currentToken.getName().equals(null)) shift(162);
				//else error();
				break;
	
			case 152: 
				if (checkReduce7.contains(currentToken.getName()))
					reduce(36);
				//else error();
				break;
	
			case 153:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) reduce(60);
				else if(currentToken.getName().equals(TokenName.PARAM_SEP.toString())) shift(154);
				//else error();
				break;
	
			case 154:
				if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
					state = 199;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 153;
					stateStack.push(state);
				} else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 155;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 161;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(156);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(157);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(158);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(159);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(160);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(163);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(164);
				else if(currentToken.getName().equals(null)) shift(162);
				//else error();
				break;
	
			case 155:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(110);
				//else error();
				break;
	
			case 156:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(111);
				//else error();
				break;
	
			case 157:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(112);
				//else error();
				break;
	
			case 158:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(113);
				//else error();
				break;
	
			case 159:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(114);
				//else error();
				break;
	
			case 160:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(115);
				//else error();
				break;
	
			case 161:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(116);
				//else error();
				break;
	
			case 162:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(117);
				//else error();
				break;
	
			case 163:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(118);
				//else error();
				break;
	
			case 164:
				if(rightParenParamSep.contains(currentToken.getName())) reduce(119);
				//else error();
				break;
	
			case 165:
				if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(166);
				//else error();
				break;
	
			case 166:
				if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
					state = 167;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
					state = 168;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				//else error();
				break;
	
			//state 167 to state 171 conflicts
			 case 167:
				 if (currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) {
					 if(stackNextTop.equals(TokenName.NOT_OP.toString()))
						reduce(104);
					else shift(192);
				 } else if (currentToken.getName().equals(TokenName.OR_OP.toString())) {
					 if(stackNextTop.equals(TokenName.NOT_OP.toString()))
						reduce(104);
					else shift(184);
				 } else if (currentToken.getName().equals(TokenName.AND_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.EQUAL_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.GREAT_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.LESS_OP.toString())) reduce(104);
				 else if (currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) reduce(104);
				 //else error();
				 break;
	
			 case 168:
				 if (currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) {
					if (stackNextTop.equals("OR_OP")) reduce(92);
					else reduce(93);
				 } else if (currentToken.getName().equals(TokenName.OR_OP.toString())) {
					 if (stackNextTop.equals("OR_OP")) reduce(92);
					else reduce(93);
				 } else if (currentToken.getName().equals(TokenName.AND_OP.toString())) shift(185);
				 //else error();
				 break;
	
			 case 169:
				 if (currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) {
					 if (stackNextTop.equals("AND_OP")) reduce(94);
					 else reduce(95);
				 } else if (currentToken.getName().equals(TokenName.OR_OP.toString())) {
					 if (stackNextTop.equals("AND_OP")) reduce(94);
					 else reduce(95);
				 } else if (currentToken.getName().equals(TokenName.AND_OP.toString())) {
					 if (stackNextTop.equals("AND_OP")) reduce(94);
					 else reduce(95);
				 } else if (currentToken.getName().equals(TokenName.EQUAL_OP.toString())) shift(186);
				 else if (currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) shift(187);
				 break;
				 
			 case 170:
				 if (currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) {
					switch(stackNextTop) {
						case "EQUAL_OP": reduce(96); break;
						case "NOT_EQUAL_OP": reduce(97); break;
						default: reduce(98); break;
					}
				 } else if (currentToken.getName().equals(TokenName.OR_OP.toString())) {
					switch(stackNextTop) {
						case "EQUAL_OP": reduce(96); break;
						case "NOT_EQUAL_OP": reduce(97); break;
						default: reduce(98); break;
					}
				 } else if (currentToken.getName().equals(TokenName.AND_OP.toString())) {
					switch(stackNextTop) {
						case "EQUAL_OP": reduce(96); break;
						case "NOT_EQUAL_OP": reduce(97); break;
						default: reduce(98); break;
					}
				 } else if (currentToken.getName().equals(TokenName.EQUAL_OP.toString())) {
					switch(stackNextTop) {
						case "EQUAL_OP": reduce(96); break;
						case "NOT_EQUAL_OP": reduce(97); break;
						default: reduce(98); break;
					}
				 } else if (currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) {
					switch(stackNextTop) {
						case "EQUAL_OP": reduce(96); break;
						case "NOT_EQUAL_OP": reduce(97); break;
						default: reduce(98); break;
					}
				 } else if (currentToken.getName().equals(TokenName.GREAT_OP.toString())) shift(188);
				 else if (currentToken.getName().equals(TokenName.LESS_OP.toString())) shift(189);
				 else if (currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())) shift(190);
				 else if (currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) shift(191);
				 
				 break;
	
			 case 171:
				 if (currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.OR_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.AND_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.EQUAL_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.NOT_EQUAL_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.GREAT_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.LESS_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.GREAT_EQ_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } else if (currentToken.getName().equals(TokenName.LESS_EQ_OP.toString())) {
					switch(stackNextTop) {
						case "GREAT_OP": reduce(99); break;
						case "GREAT_EQ_OP": reduce(101); break;
						case "LESS_OP": reduce(100); break;
						case "LESS_EQ_OP": reduce(102); break;
						default: reduce(103); break;
					}
				 } 
				 break;
	
			case 172:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
					state = 167;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
					state = 168;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 173:
				if(operatorss.contains(currentToken.getName())) reduce(105);
				//else error();
				break;
	
			case 174:
				if(operatorss.contains(currentToken.getName())) reduce(110);
				//else error();
				break;
	
			case 175:
				if(operatorss.contains(currentToken.getName())) reduce(111);
				//else error();
				break;
	
			case 176:
				if(operatorss.contains(currentToken.getName())) reduce(112);
				//else error();
				break;
	
			case 177:
				if(operatorss.contains(currentToken.getName())) reduce(113);
				//else error();
				break;
	
			case 178:
				if(operatorss.contains(currentToken.getName())) reduce(114);
				//else error();
				break;
	
			case 179:
				if(operatorss.contains(currentToken.getName())) reduce(115);
				//else error();
				break;
	
			case 180:
				if(operatorss.contains(currentToken.getName())) reduce(116);
				//else error();
				break;
	
			case 181:
				if(operatorss.contains(currentToken.getName())) reduce(117);
				//else error();
				break;
	
			case 182:
				if(operatorss.contains(currentToken.getName())) reduce(118);
				//else error();
				break;
	
			case 183:
				if(operatorss.contains(currentToken.getName())) reduce(119);
				//else error();
				break;
	
			case 184:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
					state = 168;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 185:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 186:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 187:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 188:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 189:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 190:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
			case 191:
				if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(null)) shift(181);
				else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null){
					state = 173;
					stateStack.push(state);
				}else if(stackTop.equals("<CONST>") && tokenTop == null){
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
					state = 180;
					stateStack.push(state);
				} //else error();
				break;
	
				//state 192 - 194 conflict
			case 192:
				if (stackTop.equals("<STATEMENT>") && tokenTop == null){
					state = 193;
					stateStack.push(state);
				//} /*else if(stackTop.equals("<STATEMENTS>") && tokenTop == null){
					//if (stackNextTop.equals("<STATEMENT>"))	reduce(11);*/
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.DEDENT.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
				} else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
				} else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(54);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(149);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(219);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(25);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(26);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(27);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(28);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(29);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(30);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(31);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(32);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(88);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(90);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(165);
				} else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(218);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(216);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(140);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if (tokenStack.get(tokenStack.size() - 6).getData().equals("DO")) reduce(74);
					else if (tokenStack.get(tokenStack.size() - 8).getData().equals("DO")) reduce(73);
					else shift(142);
				} else if(currentToken.getName().equals(TokenName.INDENT.toString())) shift(203);
				
				break;
	
			case 193:
				if (stackTop.equals("<STATEMENTS>") && tokenTop == null){ 
					state = 197;
					stateStack.push(state);
				} else if (stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){ 
					state = 194;
					stateStack.push(state);
				} else if (stackTop.equals("<DECLARATION>") && tokenTop == null){ 
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(14);
				else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(198);
				} else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(54);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(149);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(219);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(25);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(26);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(27);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(28);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(29);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(30);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(31);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(88);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(90);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(165);
				} else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) reduce(16);
				else if(currentToken.getName().equals(TokenName.ELSE.toString())) reduce(16);
				else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(218);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(216);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(140);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if (stackNextTop.equals(TokenName.STMT_SEP.toString())) reduce(16);
					else shift(142);
				} else if (stackTop.equals("<STATEMENT>") && tokenTop == null){ 
					state = 193;
					stateStack.push(state);
				}  
				break;
	
			case 194:
				if (stackTop.equals("<STATEMENTS>") && tokenTop == null){ 
					state = 200;
					stateStack.push(state);
				} else if (stackTop.equals("<STATEMENT>") && tokenTop == null){ 
					state = 193;
					stateStack.push(state);
				} else if (stackTop.equals("<DECLARATION>") && tokenTop == null){ 
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(13);
				else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) reduce(13);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(54);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(149);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(219);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(25);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(26);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(27);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(28);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(29);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(30);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(31);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(144);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(88);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(90);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(165);
				} else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) reduce(13);
				else if(currentToken.getName().equals(TokenName.ELSE.toString())) reduce(13);
				else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(218);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(216);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(140);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if (stackTop.equals("<MORE_STATEMENT>")) reduce(13);
					else shift(142);
				} else if (stackTop.equals("<MORE_STATEMENT>") && tokenTop == null){ 
					state = 194;
					stateStack.push(state);
				}  
				break;
				
			case 195:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) shift(152);
				//else error();
				break;
	
			case 196://conflict
				System.out.println("pasok 196");
				if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DEDENT.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if (stackNextTop.equals(TokenName.ELSE.toString())) reduce(70);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.ELSE.toString())) {
					if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) {
					if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.IF.toString())) reduce(66);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.ELSE_IF.toString())) reduce(68);
					else if(tokenStack.get(tokenStack.size() - 5).getData().equals(TokenName.WHILE.toString())) reduce(72);
				} //else error();
				break;
	
			case 197:
				if(checkReduce.contains(currentToken.getName())) reduce(11);
				//else error();
				break;
	
			case 198:
				if(stackTop.equals("<MORE_STATEMENTS>") && tokenTop == null){
					state = 201;
					stateStack.push(state);
				} else if(stackTop.equals("<STATEMENT>") && tokenTop == null){
					state = 193;
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
					state = 206;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				//else error();
				break;
	
			case 199:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) reduce(60);
				//else error();
				break;
				
			case 200:
				if(checkReduce.contains(currentToken.getName())) reduce(12);
				//else error();
				break;
					
			case 201:
				if(checkReduce.contains(currentToken.getName())) reduce(15);
				//else error();
				break;
					
			case 203:
				if(stackTop.equals("<STATEMENTS>") && tokenTop == null) {
					if (stackNextTop.equals("<STATEMENT>")) state = 136;
					else state = 204;
					stateStack.push(state);
				} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null) {
					state = 194;
					stateStack.push(state);
				} else if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 193;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<BRANCHING>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 18;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
					state = 20;
					stateStack.push(state);
				} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null) {
					state = 21;
					stateStack.push(state);
				} else if(stackTop.equals("<LOOPING>") && tokenTop == null) {
					state = 22;
					stateStack.push(state);
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 42;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null) {
					state = 34;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null) {
					state = 35;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_STMT>") && tokenTop == null) {
					state = 206;
					stateStack.push(state);
				} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null) {
					state = 36;
					stateStack.push(state);
				} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null) {
					state = 37;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_INC>") && tokenTop == null) {
					state = 38;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_INC>") && tokenTop == null) {
					state = 39;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null) {
					state = 40;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_DEC>") && tokenTop == null) {
					state = 41;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				break;
				
			case 204:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) shift(205);
				break;
				
			case 205:
				if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) {
					//System.out.println("pasok else if, check " + tokenStack.get(tokenStack.size() - 4).getData());
					if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString()))
						reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString()))
						reduce(67);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DEDENT.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.STMT_SEP.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.BREAK.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.INPUT.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.IF.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DO.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.WHILE.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.INC_OP.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) {
					if(tokenStack.get(tokenStack.size() - 4).getData().equals(TokenName.ELSE.toString())) reduce(69);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString())) reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString())) reduce(67);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.WHILE.toString())) reduce(71);
				} else if(currentToken.getName().equals(TokenName.ELSE.toString())) {
					if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.IF.toString()))
						reduce(65);
					else if(tokenStack.get(tokenStack.size() - 7).getData().equals(TokenName.ELSE_IF.toString()))
						reduce(67);
				}   //else error();
				break;
				
			case 206:
				if(stackTop.equals("<ELSEIF_STMTS>") && tokenTop == null) {
					state = 207;
					stateStack.push(state);
				} else if(stackTop.equals("<ELSEIF_STMT>") && tokenTop == null) {
					state = 211;
					stateStack.push(state);
				} else if(stackTop.equals("<ELSE_STMT>") && tokenTop == null) {
					state = 208;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) shift(212);
				else if(currentToken.getName().equals(TokenName.ELSE.toString())) shift(214);
				else if(checkReduce.contains(currentToken.getName())) reduce(49);
				//else error();
				break;
				
			case 207:
				if(nostmtsep.contains(currentToken.getName())) reduce(61);
				//else error();
				break;
				
			case 208:
				if(nostmtsep.contains(currentToken.getName())){
					if (stackNextTop.equals("<IF_STMT>")) reduce(62);
					else reduce(64);
				}
				//else error();
				break;
				
			case 211:
				if(stackTop.equals("<ELSEIF_STMTS>") && tokenTop == null) {
					state = 215;
					stateStack.push(state);
				} else if(stackTop.equals("<ELSE_STMT>") && tokenTop == null) {
					state = 208;
					stateStack.push(state);
				} else if(nostmtsep.contains(currentToken.getName())) reduce(64);
				else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())) shift(212);
				else if(currentToken.getName().equals(TokenName.ELSE.toString())) shift(214);
				else if(stackTop.equals("<ELSEIF_STMT>") && tokenTop == null) {
					state = 211;
					stateStack.push(state);
				} //else error();
				break;
				
			case 212:
				if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(213);
				//else error();
				break;
				
			case 213:
				if(stackTop.equals("<REL_EXPR>") && tokenTop == null) {
					state = 167;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null) {
					state = 168;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null) {
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null) {
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null) {
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null) {
					state = 173;
					stateStack.push(state);
				} else if(stackTop.equals("<CONST>") && tokenTop == null) {
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null) {
					state = 180;
					stateStack.push(state);
				} 
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(null)) shift(181);
				//else error();
				break;
				
			case 214:
				if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 196;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<BRANCHING>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 18;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
					state = 20;
					stateStack.push(state);
				} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null) {
					state = 21;
					stateStack.push(state);
				} else if(stackTop.equals("<LOOPING>") && tokenTop == null) {
					state = 22;
					stateStack.push(state);
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 42;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null) {
					state = 34;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null) {
					state = 35;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_STMT>") && tokenTop == null) {
					state = 206;
					stateStack.push(state);
				} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null) {
					state = 36;
					stateStack.push(state);
				} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null) {
					state = 37;
					stateStack.push(state);
				} else if(stackTop.equals("<INC_STMT>") && tokenTop == null) {
					state = 23;
					stateStack.push(state);
				} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null) {
					state = 24;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_INC>") && tokenTop == null) {
					state = 38;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_INC>") && tokenTop == null) {
					state = 39;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null) {
					state = 40;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_DEC>") && tokenTop == null) {
					state = 41;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.INDENT.toString())) shift(203);
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				break;
				
			case 215:
				if(nostmtsep.contains(currentToken.getName())) reduce(63);
				//else error();
				break;
				
			case 216:
				System.out.println("pasok 216");
				if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) shift(217);
				break;
				
			case 217:
				System.out.println("pasok 217");
				if(stackTop.equals("<REL_EXPR>") && tokenTop == null) {
					state = 167;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null) {
					state = 168;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null) {
					state = 169;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null) {
					state = 170;
					stateStack.push(state);
				} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null) {
					state = 171;
					stateStack.push(state);
				} else if(stackTop.equals("<VALUE>") && tokenTop == null) {
					state = 173;
					stateStack.push(state);
				} else if(stackTop.equals("<CONST>") && tokenTop == null) {
					state = 174;
					stateStack.push(state);
				} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null) {
					state = 180;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(175);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(172);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(176);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(177);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(178);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(179);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(182);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(183);
				else if(currentToken.getName().equals(null)) shift(181);
				//else error();
				break;
				
			case 218:
				//System.out.println("pasok 218");
				if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 196;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<BRANCHING>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 18;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
					state = 20;
					stateStack.push(state);
				} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null) {
					state = 21;
					stateStack.push(state);
				} else if(stackTop.equals("<LOOPING>") && tokenTop == null) {
					state = 22;
					stateStack.push(state);
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 42;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null) {
					state = 34;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null) {
					state = 35;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_STMT>") && tokenTop == null) {
					state = 206;
					stateStack.push(state);
				} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null) {
					state = 36;
					stateStack.push(state);
				} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null) {
					state = 37;
					stateStack.push(state);
				} else if(stackTop.equals("<INC_STMT>") && tokenTop == null) {
					state = 23;
					stateStack.push(state);
				} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null) {
					state = 24;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_INC>") && tokenTop == null) {
					state = 38;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_INC>") && tokenTop == null) {
					state = 39;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null) {
					state = 40;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_DEC>") && tokenTop == null) {
					state = 41;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.INDENT.toString())) shift(203);
				else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString())) shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString())) shift(219);
				else if(currentToken.getName().equals(TokenName.BREAK.toString())) shift(25);
				else if(currentToken.getName().equals(TokenName.CONTINUE.toString())) shift(26);
				else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) shift(27);
				else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())) shift(28);
				else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())) shift(29);
				else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())) shift(30);
				else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())) shift(31);
				else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())) shift(32);
				else if(currentToken.getName().equals(TokenName.INPUT.toString())) shift(88);
				else if(currentToken.getName().equals(TokenName.OUTPUT.toString())) shift(90);
				else if(currentToken.getName().equals(TokenName.IF.toString())) shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) shift(142);
				////else error();
				break;
				
			case 219:
				if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null) {
					state = 220;
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
				} else if(currentToken.getName().equals(TokenName.VAR.toString())) shift(94);
				else if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) shift(91);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())) shift(60);
				else if(currentToken.getName().equals(TokenName.NOT_OP.toString())) shift(107);
				else if(currentToken.getName().equals(TokenName.INT_CONST.toString())) shift(95);
				else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())) shift(96);
				else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())) shift(97);
				else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())) shift(98);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())) shift(101);
				else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())) shift(102);
				else if(currentToken.getName().equals(null)) shift(100);					
				//else error();
				break;
	
			case 220:
				if(checkReduce5.contains(currentToken.getName())) reduce(39);
				//else error();
				break;
				
			case 221:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(7);
				else if(stackTop.equals("<SUB_FUNCTIONS>") && tokenTop == null){
					state = 222;
					stateStack.push(state);
				}else if(stackTop.equals("<SUB_FUNCTION>") && tokenTop == null){
					state = 221;
					stateStack.push(state);
				}else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
					state = 223;
					stateStack.push(state);
				}else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString()))
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
				//else error();
				break;
				
			case 222:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) reduce(6);
				//else error();
				break;
				
			case 223:
				if(currentToken.getName().equals(TokenName.PROC_NAME.toString()))
					shift(224);
				//else error();
				break;
				
			case 224:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString()))
					shift(225);
				//else error();
				break;
				
			case 225:
				if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) 
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
				else if(stackTop.equals("<PARAMS>") && tokenTop == null) {
					state = 226;
					stateStack.push(state);					
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 231;
					stateStack.push(state);					
				} //else error();
				break;
				
			case 226:
				if(currentToken.getName().equals(TokenName.LEFT_PAREN.toString())) 
					shift(227);
				//else error();
				break;
				
			case 227:
				if(currentToken.getName().equals(TokenName.INDENT.toString())) 
					shift(228);
				//else error();
				break;
				
			case 228:
				if(stackTop.equals("<STATEMENTS>") && tokenTop == null) {
					state = 229;
					stateStack.push(state);
				} else if(stackTop.equals("<MORE_STATEMENT>") && tokenTop == null) {
					state = 138;
					stateStack.push(state);
				} else if(stackTop.equals("<STATEMENT>") && tokenTop == null) {
					state = 136;
					stateStack.push(state);
				} else if(stackTop.equals("<DECLARATION>") && tokenTop == null) {
					state = 12;
					stateStack.push(state);
				} else if(stackTop.equals("<ASSIGNMENT>") && tokenTop == null) {
					state = 13;
					stateStack.push(state);
				} else if(stackTop.equals("<IO>") && tokenTop == null) {
					state = 14;
					stateStack.push(state);
				} else if(stackTop.equals("<CONTROL_FLOW>") && tokenTop == null) {
					state = 15;
					stateStack.push(state);
				} else if(stackTop.equals("<EXPR_STATEMENTS>") && tokenTop == null) {
					state = 16;
					stateStack.push(state);
				} else if(stackTop.equals("<BRANCHING>") && tokenTop == null) {
					state = 17;
					stateStack.push(state);
				} else if(stackTop.equals("<RETURN>") && tokenTop == null) {
					state = 18;
					stateStack.push(state);
				} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null) {
					state = 19;
					stateStack.push(state);
				} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null) {
					state = 20;
					stateStack.push(state);
				} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null) {
					state = 21;
					stateStack.push(state);
				} else if(stackTop.equals("<LOOPING>") && tokenTop == null) {
					state = 22;
					stateStack.push(state);
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 42;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null) {
					state = 34;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null) {
					state = 35;
					stateStack.push(state);
				} else if(stackTop.equals("<IF_STMT>") && tokenTop == null) {
					state = 206;
					stateStack.push(state);
				} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null) {
					state = 36;
					stateStack.push(state);
				} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null) {
					state = 37;
					stateStack.push(state);
				} else if(stackTop.equals("<INC_STMT>") && tokenTop == null) {
					state = 23;
					stateStack.push(state);
				} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null) {
					state = 24;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_INC>") && tokenTop == null) {
					state = 38;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_INC>") && tokenTop == null) {
					state = 39;
					stateStack.push(state);
				} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null) {
					state = 40;
					stateStack.push(state);
				} else if(stackTop.equals("<POST_DEC>") && tokenTop == null) {
					state = 41;
					stateStack.push(state);
				} else if(currentToken.getName().equals(TokenName.VAR.toString()))
					shift(144);
				else if(currentToken.getName().equals(TokenName.ASSIGN.toString()))
					shift(54);
				else if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
					shift(149);
				else if(currentToken.getName().equals(TokenName.PROC_RET.toString()))
					shift(219);
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
				else if(currentToken.getName().equals(TokenName.IF.toString())) 
					shift(165);
				else if(currentToken.getName().equals(TokenName.DO.toString())) 
					shift(218);
				else if(currentToken.getName().equals(TokenName.WHILE.toString())) 
					shift(216);
				else if(currentToken.getName().equals(TokenName.INC_OP.toString())) 
					shift(140);
				else if(currentToken.getName().equals(TokenName.DEC_OP.toString())) 
					shift(142);
				//else error();
				break;
				
			case 229:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())) 
					shift(142);
				//else error();
				break;
				
			case 230:
				if(currentToken.getName().equals(TokenName.DEDENT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
						|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString()))
							reduce(8);
					//else error();
				break;
				
			case 231:
				if(currentToken.getName().equals(TokenName.VAR.toString())) 
					shift(232);
				//else error();
				break;
				
			case 232:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) reduce(10);
				else if(currentToken.getName().equals(TokenName.PARAM_SEP.toString())) 
					shift(233);
				//else error();
				break;
				
			case 233:
				if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())) 
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
				else if(stackTop.equals("<PARAMS>") && tokenTop == null) {
					state = 234;
					stateStack.push(state);					
				} else if(stackTop.equals("<DATATYPE>") && tokenTop == null) {
					state = 231;
					stateStack.push(state);					
				} //else error();
				break;
				
			case 234:
				if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) reduce(9);
				//else error();
				break;
			}
	}
	
	private static void reduce(int rule) {
		System.out.println("Currently on state " + stateStack.peek().toString() + ". Reduce from: " + previousToken.toString());
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
        
        System.out.print("top of stack = " + tokenStack.peek().getData());
        System.out.print(", next top of stack = " + tokenStack.get(tokenStack.size() - 2).getData());
        System.out.println(", state stack after" + stateStack.toString());
        System.out.println("current state " + stateStack.peek().toString() 
        		+ ", current token " + currentToken.getName()
        		+ ", line: " + currentToken.getLineNumber()
        		+ ", top of stack " + tokenStack.peek().getData());
    }
	
	private static void shift(int nextState) {
        TokenNode node = new TokenNode();
        node.setData(currentToken.getName());
        node.setToken(currentToken);
        tokenStack.push(node);
        
        state = nextState;
        stateStack.push(state);
        
        getToken();
        //System.out.println(currentToken);
        System.out.println("Shift to state " + nextState + ": " + currentToken.toString());
    }
	
	public static TokenNode getRoot() {
        return tokenStack.get(1);
    }
	
	private static void error() {
		// TODO: ERROR MESSAGE or sumthin
		System.out.println("Parsing failed. Some error occured at " + state);
	}

	private static void errorMsg (String errormsg) {
		System.out.println("Error at line " + currentToken.getLineNumber() + ". Expecting: " + errormsg);
	}
}
