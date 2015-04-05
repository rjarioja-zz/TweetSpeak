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
	
	public static boolean parser() {
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
	    
	    //12 - 26
	    List<String> checkReduce = Arrays.asList(
	    		TokenName.PROG_NAME.toString(), 		TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString());
	    
	    //state 67 at 72
	    List<String> checkReduce2 = Arrays.asList(
	    		TokenName.PROG_NAME.toString(), 		TokenName.DEDENT.toString(),
	    		TokenName.STMT_SEP.toString(), 			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 			TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),		TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 		TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), 	TokenName.IF.toString(),
	    		TokenName.DO.toString(), 				TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 			TokenName.DEC_OP.toString(),
	    		TokenName.PROC_RET.toString(),			TokenName.ADD_OP.toString(),
	    		TokenName.DIF_OP.toString());
	    
	    List<String> checkReduce3 = Arrays.asList(
	    		TokenName.PROG_NAME.toString(),			TokenName.DEDENT.toString(), 		
	    		TokenName.STMT_SEP.toString(),			TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 		TokenName.PROC_RET.toString(),
	    		TokenName.DO.toString(),				TokenName.CONTINUE.toString(), 			
	    		TokenName.BREAK.toString(),				TokenName.DATATYPE_BOOL.toString(),		
	    		TokenName.DATATYPE_CHAR.toString(),   	TokenName.WHILE.toString(),
	    		TokenName.DATATYPE_FLOAT.toString(),	TokenName.DATATYPE_INT.toString(), 	
	    		TokenName.DATATYPE_STRING.toString(),	TokenName.DATATYPE_VOID.toString(), 
	    		TokenName.IF.toString());		
	    
	    getToken();
	    System.out.println("START PARSE WITH: " + currentToken.toString());
		while (true) {
			String stackTop = tokenStack.peek().getData();
            Token tokenTop = tokenStack.peek().getToken();
            
			switch (state) {
				case 0:
					if (stackTop.equals("<PROGRAM>") && tokenTop == null) {
						state = 1;
						stateStack.push(state);
					} else if (currentToken.getName().equals(TokenName.START.toString())) 
						shift(2);
					else error();
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
					else if(currentToken.getName().equals("DEDENT"))
						shift(6);
					else errorMsg("an indent.");
					break;
					
				case 11:
					if (currentToken.getName().equals(TokenName.ASSIGN.toString()))
						shift(54);
					else if(currentToken.getName().equals(TokenName.BREAK.toString()))
						shift(25);
					else if(currentToken.getName().equals(TokenName.CONTINUE.toString()))
						shift(26);
					else if(stackTop.equals("<DECLARATION>") && tokenTop == null){
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
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 33;
						stateStack.push(state);
					} else if(stackTop.equals("<ELSEIF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 35;
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
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(43);
					else error();
					break;
				case 28:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(44);
					else error();
					break;
				case 29:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(45);
					else error();
					break;
				case 30:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(46);
					else error();
					break;
				case 31:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(47);
					else error();
					break;
				case 32:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						reduce(48);
					else error();
					break;
				case 33:
					if(checkReduce.contains(currentToken.getName())) reduce(51); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 34:
					if(checkReduce.contains(currentToken.getName())) reduce(52); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 35:
					if(checkReduce.contains(currentToken.getName())) reduce(53); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 36:
					if(checkReduce.contains(currentToken.getName())) reduce(54); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 37:
					if(checkReduce.contains(currentToken.getName())) reduce(5); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 38:
					if(checkReduce.contains(currentToken.getName())) reduce(77); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 39: 
					if(checkReduce.contains(currentToken.getName())) reduce(78); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 40:
					if(checkReduce.contains(currentToken.getName())) reduce(79); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 41:
					if(checkReduce.contains(currentToken.getName())) reduce(80); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 42:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						shift(43);
					else error();
					break;
				case 43:
					if(checkReduce.contains(currentToken.getName())) reduce(27); //WITH CALL_PARAM DI PA NALAGYAN
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
					if(checkReduce.contains(currentToken.getName())) reduce(28); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 46:
					if(checkReduce.contains(currentToken.getName())) reduce(114); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 47:
					if(checkReduce.contains(currentToken.getName())) reduce(115); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 48:
					if(checkReduce.contains(currentToken.getName())) reduce(116); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 49: 
					if(checkReduce.contains(currentToken.getName())) reduce(117); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 50: 
					if(checkReduce.contains(currentToken.getName())) reduce(119); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 51: 
					if(checkReduce.contains(currentToken.getName())) reduce(118); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 52: 
					if(checkReduce.contains(currentToken.getName())) reduce(120); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 53:
					if(checkReduce.contains(currentToken.getName())) reduce(121); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 54: 
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString()))
						shift(55);
					else error();
					break;
				case 55:
					if(currentToken.getName().equals(TokenName.PROC_CALL.toString()))
						shift(60);
					else if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 56;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
						state = 57;
						stateStack.push(state);
					} else if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 58;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
						state = 59;
						stateStack.push(state);
					} else error();
					break;
				case 56:
					if(checkReduce.contains(currentToken.getName())) reduce(29); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 57: 
					if(currentToken.getName().equals(TokenName.ADD_OP.toString()))
						shift(66);
					else if(currentToken.getName().equals(TokenName.DIF_OP.toString()))
						shift(85);
					else error();
					break;
				case 58:
					if(checkReduce.contains(currentToken.getName())) reduce(57); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 59:
					if(checkReduce.contains(currentToken.getName())) reduce(58); //WITH CALL_PARAM DI PA NALAGYAN
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
					if(stackTop.equals("<CALL_PARAMS>") && tokenTop == null){
						state = 63;
						stateStack.push(state);
					} else error();
					break;
				case 63:
					if(currentToken.getName().equals(TokenName.RIGHT_PAREN.toString())) 
						shift(64);
					else error();
					break;
				case 64:
					if(checkReduce.contains(currentToken.getName())) reduce(59); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 65:
					if(checkReduce.contains(currentToken.getName())) reduce(60); //WITH CALL_PARAM DI PA NALAGYAN
					else error();
					break;
				case 66: 
					if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 67;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR3>") && tokenTop == null){
						state = 68;
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
					if(checkReduce2.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.MUL_OP.toString())
						|| currentToken.getName().equals(TokenName.DIV_OP.toString())
						|| currentToken.getName().equals(TokenName.MOD_OP.toString()))
						reduce(87);
					else error();
					break;
				case 69: 
					break;
				case 70:
					break;
				case 71:
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
					if(checkReduce3.contains(currentToken.getName())
						|| currentToken.getName().equals(TokenName.MUL_OP.toString())
						|| currentToken.getName().equals(TokenName.DIV_OP.toString())
						|| currentToken.getName().equals(TokenName.MOD_OP.toString()))
						reduce(89);
					else if (currentToken.getName().equals(TokenName.EXP_OP.toString()))
						shift(74);
					else error();
					break;
				case 74:
					break;
				case 75:
					break;
				case 76:
					break;
				case 77:
					break;
				case 78:
					break;
				case 79:
					break;
				case 80:
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
		System.out.println("Shift to state " + nextState + ": " + currentToken.toString());
        TokenNode node = new TokenNode();
        node.setData(currentToken.getName());
        node.setToken(currentToken);
        tokenStack.push(node);
        
        state = nextState;
        stateStack.push(state);

        getToken();
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