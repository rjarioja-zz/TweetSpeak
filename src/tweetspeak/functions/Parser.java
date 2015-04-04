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
		TokenNode root = new TokenNode();
        
	    root.setData("$");
	    tokenStack.push(root);
	    stateStack.push(state);
        
	    Tokenizer.initialize();
		
		// PARSING TABLE IN SWITCH-CASE FORM
		// prepare for pagkahaba habang code :(
		// please ganto nalang
		// sorry = not sorry
	    
	    // kewl to kimpot :P
	    
	    List<String> checkReduce1 = Arrays.asList(
	    		TokenName.PROG_NAME.toString(), 	TokenName.DEDENT.toString(),
	    		TokenName.ASSIGN.toString(), 		TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 	TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 		TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(),	TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 	TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString(), 		TokenName.IF.toString(),
	    		TokenName.DO.toString(), 			TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 		TokenName.DEC_OP.toString());

	    // states 28-33
	    List<String> checkReduce2 = Arrays.asList(
	    		TokenName.PROG_NAME.toString(), 	TokenName.ASSIGN.toString(),
	    		TokenName.PROC_CALL.toString(), 	TokenName.BREAK.toString(),
	    		TokenName.CONTINUE.toString(), 		TokenName.DATATYPE_BOOL.toString(),
	    		TokenName.DATATYPE_CHAR.toString(), TokenName.DATATYPE_FLOAT.toString(),
	    		TokenName.DATATYPE_INT.toString(), 	TokenName.DATATYPE_STRING.toString(),
	    		TokenName.DATATYPE_VOID.toString(), TokenName.INPUT.toString(),
	    		TokenName.OUTPUT.toString(), 		TokenName.IF.toString(),
	    		TokenName.DO.toString(), 			TokenName.WHILE.toString(),
	    		TokenName.INC_OP.toString(), 		TokenName.DEC_OP.toString());
	    
	    List<String> checkReduce3 = Arrays.asList(
	    		TokenName.DEDENT.toString(),			TokenName.INDENT.toString(), 		
	    		TokenName.STMT_SEP.toString(),			TokenName.ASSIGN.toString(), 		
	    		TokenName.ASSIGN.toString(),			TokenName.PROC_CALL.toString(), 	
	    		TokenName.BREAK.toString(),				TokenName.CONTINUE.toString(), 		
	    		TokenName.DATATYPE_BOOL.toString(),		TokenName.DATATYPE_CHAR.toString(), 
	    		TokenName.DATATYPE_FLOAT.toString(),	TokenName.DATATYPE_INT.toString(), 	
	    		TokenName.DATATYPE_STRING.toString(),	TokenName.DATATYPE_VOID.toString(), 
	    		TokenName.INPUT.toString(),				TokenName.OUTPUT.toString(), 		
	    		TokenName.IF.toString(),				TokenName.DO.toString(), 			
	    		TokenName.WHILE.toString(),				TokenName.CONCAT.toString(), 		
	    		TokenName.INC_OP.toString(),			TokenName.DEC_OP.toString());
	    
	    getToken();
	    
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
					else 
						error();
					break;
					
				case 1:
					if (currentToken.getName() == "$") {
						// error
                        if (tokenStack.get(1).getChildren().size() == 0) {
                            System.err.println("Parse tree is broken!\nProbably a STATEMENT reduction problem.");
                            System.exit(0);
                        }
                        // accept
                        return true;
                    } else
                        System.out.println("End of file");
                    break;
					
				case 2:
					if (currentToken.getName().equals(TokenName.PROG_NAME.toString())) 
						shift(3);
					else 
						// PROG_NAME required 
						errorMsg("a program name.");
					break;
					
				case 3:
					if (currentToken.getName().equals(TokenName.INDENT.toString())) 
						shift(4);
					else 
						errorMsg("an indent.");
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
					} else 
						error();
					break;
					
				case 5:
					if (currentToken.getName().equals("DEDENT"))
						shift(6);
					else 
						errorMsg("a dedent.");
					break;
					
				case 6:
					if (currentToken.getName().equals(TokenName.END.toString()))
						shift(7);
					else
						errorMsg("End of Program.");
					break;
					
				case 7:
					if (currentToken.getName() == "$") 
						reduce(2);
                    else
                        System.out.println("End of file");
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
					if(currentToken.getName().equals("DEDENT")){
						reduce(3);
					} else {
						error();
					} break;
				case 10:
					if(currentToken.getName().equals("INDENT")){
						shift(11);
					} else {
						errorMsg("an indent.");
					} break;
				case 11:
					if(currentToken.getName().equals(TokenName.PROC_CALL.toString())){
						shift(129);
					} else if (currentToken.getName().equals(TokenName.PROC_RET.toString())){
						shift(140);
					} else if(currentToken.getName().equals(TokenName.BREAK.toString())){
						shift(26);
					} else if(currentToken.getName().equals(TokenName.CONTINUE.toString())){
						shift(27);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_INT.toString())){
						shift(28);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())){
						shift(29);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())){
						shift(30);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())){
						shift(31);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())){
						shift(32);
					} else if(currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())){
						shift(33);
					} else if (currentToken.getName().equals(TokenName.INPUT.toString())){
						shift(142);
					} else if(currentToken.getName().equals(TokenName.OUTPUT.toString())){
						shift(144);
					} else if(currentToken.getName().equals(TokenName.IF.toString())){
						shift(146);
					} else if(currentToken.getName().equals(TokenName.ELSE_IF.toString())){
						shift(155);
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
						state = 17;
						stateStack.push(state);
					} else if(stackTop.equals("<BRANCHING>") && tokenTop == null){
						state = 18;
						stateStack.push(state);
					} else if(stackTop.equals("<RETURN>") && tokenTop == null){
						state = 19;
						stateStack.push(state);
					} else if(stackTop.equals("<INPUT_STMT>") && tokenTop == null){
						state = 20;
						stateStack.push(state);
					} else if(stackTop.equals("<OUTPUT_STMT>") && tokenTop == null){
						state = 21;
						stateStack.push(state);
					} else if(stackTop.equals("<CONDITIONAL>") && tokenTop == null){
						state = 22;
						stateStack.push(state);
					} else if(stackTop.equals("<LOOPING>") && tokenTop == null){
						state = 23;
						stateStack.push(state);
					} else if(stackTop.equals("<INC_STMT>") && tokenTop == null){
						state = 24;
						stateStack.push(state);
					} else if(stackTop.equals("<DEC_STMT>") && tokenTop == null){
						state = 25;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_STMT>") && tokenTop == null){
						state = 34;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSEIF_STMT>") && tokenTop == null){
						state = 35;
						stateStack.push(state);
					} else if(stackTop.equals("<IF_ELSE_STMT>") && tokenTop == null){
						state = 36;
						stateStack.push(state);
					} else if(stackTop.equals("<WHILE_STMT>") && tokenTop == null){
						state = 37;
						stateStack.push(state);
					} else if(stackTop.equals("<DO_WHILE>") && tokenTop == null){
						state = 38;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_INC>") && tokenTop == null){
						state = 39;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_INC>") && tokenTop == null){
						state = 40;
						stateStack.push(state);
					} else if(stackTop.equals("<PRE_DEC>") && tokenTop == null){
						state = 41;
						stateStack.push(state);
					} else if(stackTop.equals("<POST_DEC>") && tokenTop == null){
						state = 42;
						stateStack.push(state);
					} else if(stackTop.equals("<DATATYPE>") && tokenTop == null){
						state = 43;
						stateStack.push(state);
					} else {
						error();
					} break;
				case 12:
					if(checkReduce1.contains(currentToken.getName())){
						reduce(19);
					} else {
						error();
					} break;
				case 13:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(20);
					} else {
				error();
					} break;
				case 14:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(21);
					} else {
						error();
					} break;
				case 15: 
					if(checkReduce1.contains(currentToken.getName())){
							reduce(22);
					} else {
						error();
					} break;
				case 16:
				/*
				 * WALA?
				 */
				case 17: 
					if(checkReduce1.contains(currentToken.getName())){
							reduce(24);
					} else {
						error();
					} break;
				case 18:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(25);
					} else {
						error();
					} break;
				case 19:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(26);
					} else {
						error();
					} break;
				case 20:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(30);
					} else {
						error();
					} break;
				case 21:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(31);
					} else {
						error();
					} break;
				case 22:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(32);
					} else {
						error();
					} break;
				case 23:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(33);
					} else {
						error();
					} break;
				case 24:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(36);
					} else {
						error();
					} break;
				case 25:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(37);
					} else {
						error();
					} break;
				case 26:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(39);
					} else {
						error();
					} break;
				case 27:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(40);
					} else {
						error();
					} break;
				case 28:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(43);
					} else {
						error();
					} break;
				case 29:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(44);
					} else {
						error();
					} break;
				case 30:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(45);
					} else {
						error();
					} break;
				case 31:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(46);
					} else {
						error();
					} break;
				case 32:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(47);
					} else {
						error();
					} break;
				case 33:
					if(checkReduce2.contains(currentToken.getName())){
							reduce(48);
					} else {
						error();
					} break;
				case 34:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(51);
					} else {
						error();
					} break;
				case 35:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(52);
					} else {
						error();
					} break;
				case 36:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(53);
					} else {
						error();
					} break;
				case 37:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(54);
					} else {
						error();
					} break;
				case 38:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(55);
					} else {
						error();
					} break;
				case 39:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(77);
					} else {
						error();
					} break;
				case 40:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(78);
					} else {
						error();
					} break;
				case 41:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(79);
					} else {
						error();
					} break;
				case 42:
					if(checkReduce1.contains(currentToken.getName())){
							reduce(80);
					} else {
						error();
					} break;
				case 43:
					if(currentToken.getName().equals(TokenName.PROG_NAME.toString())){
						shift(44);
					} else{
						errorMsg("Program Name");
					} break;
				case 44:
					if(currentToken.getName().equals("DEDENT")
							|| currentToken.getName().equals(TokenName.ASSIGN.toString())
							|| currentToken.getName().equals(TokenName.PROC_CALL.toString())
							|| currentToken.getName().equals(TokenName.BREAK.toString())
							|| currentToken.getName().equals(TokenName.CONTINUE.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())
							|| currentToken.getName().equals(TokenName.INPUT.toString())
							|| currentToken.getName().equals(TokenName.OUTPUT.toString())
							|| currentToken.getName().equals(TokenName.IF.toString())
							|| currentToken.getName().equals(TokenName.DO.toString())
							|| currentToken.getName().equals(TokenName.WHILE.toString())
					        || currentToken.getName().equals(TokenName.INC_OP.toString())
					        || currentToken.getName().equals(TokenName.DEC_OP.toString())){
						reduce(27);
					} else if(currentToken.getName().equals(TokenName.ASSIGN_OP.toString())){
						shift(45);
					} else {
						error();
					} break;
				case 45: 
					if(currentToken.getName().equals(TokenName.INT_CONST.toString())){
						shift(47);
					} else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())){
						shift(48);
					} else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())){
						shift(49);
					} else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())){
						shift(50);
					} else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())){
						shift(54);
					} else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())){
						shift(53);
					} else if(currentToken.getName().equals(null)){
						shift(51);
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 46;
						stateStack.push(state);
					} else if(stackTop.equals("<BOOL_CONST>") && tokenTop == null){
						state = 52;
						stateStack.push(state);
					} else {
						error();
					} break;
				case 46:
					if(currentToken.getName().equals("DEDENT")
							|| currentToken.getName().equals(TokenName.ASSIGN.toString())
							|| currentToken.getName().equals(TokenName.PROC_CALL.toString())
							|| currentToken.getName().equals(TokenName.BREAK.toString())
							|| currentToken.getName().equals(TokenName.CONTINUE.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())
							|| currentToken.getName().equals(TokenName.INPUT.toString())
							|| currentToken.getName().equals(TokenName.OUTPUT.toString())
							|| currentToken.getName().equals(TokenName.IF.toString())
							|| currentToken.getName().equals(TokenName.DO.toString())
							|| currentToken.getName().equals(TokenName.WHILE.toString())
					        || currentToken.getName().equals(TokenName.INC_OP.toString())
					        || currentToken.getName().equals(TokenName.DEC_OP.toString())){
						reduce(28);
					} else {
						error();
					} break;
				case 47:
					if(checkReduce3.contains(currentToken.getName())){
						reduce(114);
					} else {
						error();
					} break;
				case 48:
					if(checkReduce3.contains(currentToken.getName())){
						reduce(115);
					} else {
						error();
					} break;
				case 49: 
					if(checkReduce3.contains(currentToken.getName())){
						reduce(116);
					} else {
						error();
					} break;
				case 50: 
					if(checkReduce3.contains(currentToken.getName())){
						reduce(117);
					} else {
						error();
					} break;
				case 51: 
					if(checkReduce3.contains(currentToken.getName())){
						reduce(119);
					} else {
						error();
					} break;
				case 52: 
					if(checkReduce3.contains(currentToken.getName())){
						reduce(118);
					} else {
						error();
					} break;
				case 53:
					if(checkReduce3.contains(currentToken.getName())){
						reduce(120);
					} else {
						error();
					} break;
				case 54: 
					if(checkReduce3.contains(currentToken.getName())){
						reduce(121);
					} else {
						error();
					} break;
				case 55:
					if(currentToken.getName().equals(TokenName.PROG_NAME.toString())){
						shift(56);
					} else{
						errorMsg("Program Name");
					} break;
				case 56:
					if(currentToken.getName().equals(TokenName.PROG_NAME.toString())){
						shift(90);
					} else if(currentToken.getName().equals(TokenName.PROC_CALL.toString())){
						shift(61);
					} else if(currentToken.getName().equals(TokenName.NOT_OP.toString())){
						shift(124);
					} else if(currentToken.getName().equals(TokenName.INT_CONST.toString())){
						shift(91);
					} else if(currentToken.getName().equals(TokenName.FLOAT_CONST.toString())){
						shift(92);
					} else if(currentToken.getName().equals(TokenName.CHAR_CONST.toString())){
						shift(93);
					} else if(currentToken.getName().equals(TokenName.STRING_CONST.toString())){
						shift(94);
					} else if(currentToken.getName().equals(TokenName.BOOL_CONST_FALSE.toString())){
						shift(98);
					} else if(currentToken.getName().equals(TokenName.BOOL_CONST_TRUE.toString())){
						shift(97);
					} else if(currentToken.getName().equals(null)){
						shift(96);
					} else if(stackTop.equals("<EXPRESSIONS>") && tokenTop == null){
						state = 57;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR>") && tokenTop == null){
						state = 58;
						stateStack.push(state);
					} else if(stackTop.equals("<MATH_EXPR2>") && tokenTop == null){
						state = 76;
						stateStack.push(state);
					} else if(stackTop.equals("<STRING_EXPR>") && tokenTop == null){
						state = 59;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR>") && tokenTop == null){
						state = 60;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR2>") && tokenTop == null){
						state = 102;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR3>") && tokenTop == null){
						state = 104;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR4>") && tokenTop == null){
						state = 110;
						stateStack.push(state);
					} else if(stackTop.equals("<REL_EXPR5>") && tokenTop == null){
						state = 123;
						stateStack.push(state);
					} else if(stackTop.equals("<VALUE>") && tokenTop == null){
						//state 86 OR 126 NOT SHO
					} else if(stackTop.equals("<CONST>") && tokenTop == null){
						state = 89;
						stateStack.push(state);
					} else {
						error();
					} break;
				case 57: 
					//wala pa
					break;
				case 58:
					if(currentToken.getName().equals("DEDENT")
							|| currentToken.getName().equals(TokenName.ASSIGN.toString())
							|| currentToken.getName().equals(TokenName.PROC_CALL.toString())
							|| currentToken.getName().equals(TokenName.BREAK.toString())
							|| currentToken.getName().equals(TokenName.CONTINUE.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_BOOL.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_CHAR.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_FLOAT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_INT.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_STRING.toString())
							|| currentToken.getName().equals(TokenName.DATATYPE_VOID.toString())
							|| currentToken.getName().equals(TokenName.INPUT.toString())
							|| currentToken.getName().equals(TokenName.OUTPUT.toString())
							|| currentToken.getName().equals(TokenName.IF.toString())
							|| currentToken.getName().equals(TokenName.DO.toString())
							|| currentToken.getName().equals(TokenName.WHILE.toString())
					        || currentToken.getName().equals(TokenName.INC_OP.toString())
					        || currentToken.getName().equals(TokenName.DEC_OP.toString())){
						reduce(56);
					} else if(currentToken.getName().equals(TokenName.ADD_OP.toString())){
						shift(70);
					} else if(currentToken.getName().equals(TokenName.DIF_OP.toString())){
						shift(71);
					} else {
						error();
					} break;
				case 59:
					//wala pa
					break;
				case 60:
					if(currentToken.getName().equals(TokenName.OR_OP.toString())){
						shift(100);
					} else{
						error();
					} break;
				case 61: 
					if(currentToken.getName().equals(TokenName.PROG_NAME.toString())){
						shift(62);
					} else{
						errorMsg("Program Name");
					} break;	
			} // end of switch
		}
	}
	
	private static void reduce(int rule) {
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

        state = stateStack.peek();
        tokenStack.push(node);
    }
	
	private static void shift(int nextState) {
        TokenNode node = new TokenNode();
        node.setData(currentToken.getName());
        node.setToken(currentToken);
        tokenStack.push(node);
        
        state = nextState;
        stateStack.push(state);

        getToken();
    }
	
	private static void error() {
		// TODO: ERROR MESSAGE or sumthin
	}

	private static void errorMsg (String errormsg) {
		System.out.println("Error at line " + currentToken.getLineNumber() + ". Expecting: " + errormsg);
	}
}